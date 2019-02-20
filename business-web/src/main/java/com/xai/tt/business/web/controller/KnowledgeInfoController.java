package com.xai.tt.business.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.support.OssET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.business.biz.common.util.JsonUtils;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.entity.KnowledgeCatalog;
import com.xai.tt.dc.client.entity.KnowledgeCatalogInfo;
import com.xai.tt.dc.client.inter.IKnowledgeBaseService;
import com.xai.tt.dc.client.query.KnowledgeCatalogQuery;
import com.xai.tt.dc.client.request.SaveKnowledgeCatalogInfo;
import com.xai.tt.dc.client.response.KnowledgeCatalogInfoRes;

/**
 * 
 */
@Controller
@RequestMapping("knowledgeInfo")
public class KnowledgeInfoController   extends BaseController {

    @Resource
    private IKnowledgeBaseService iKnowledgeBaseService;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.endpoint}")
    private String ossUrl;

    private static final String HTTP = "http://";

    @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("knowledgeInfo/list");
        mav.addObject("catalogList", iKnowledgeBaseService.queryPage(new KnowledgeCatalogQuery()).getData().getList());
        LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	UserType userType = user.getUserType();
    	if(user.hasRole("ROLE_ADMIN")){
    		userType = UserType.Group;
    	}
    	mav.addObject("userType", userType);
        return mav;
    }

    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?> queryPage(KnowledgeCatalogQuery knowledgeCatalogQuery, PageParam pageParam) {
        try {
            knowledgeCatalogQuery.setPageNo(pageParam.getPageNum());
            knowledgeCatalogQuery.setPageSize(pageParam.getPageSize());
            Result<PageInfo<KnowledgeCatalogInfo>> result = iKnowledgeBaseService.queryByCatalog(knowledgeCatalogQuery);
            if (result == null || result.getCode() != 0 ) {
                Result.createFailResult("未查询到结果");
            }
            if (CollectionUtils.isNotEmpty(result.getData().getList()) && StringUtils.isNotEmpty(knowledgeCatalogQuery.getKeyWorlds())) {
                List<KnowledgeCatalogInfo> infos = result.getData().getList();
                infos.forEach(item-> item.setContent(item.getContent().replaceAll(knowledgeCatalogQuery.getKeyWorlds(), "<font color='red'>" + knowledgeCatalogQuery.getKeyWorlds() + "</font>")));
                PageData pageData = new PageData(result.getData().getTotal(), infos);
                return Result.createSuccessResult(pageData);
            }
            PageData pageData = new PageData(result.getData().getTotal(), result.getData().getList());
            return Result.createSuccessResult(pageData);
        } catch (Exception e) {
            logger.error("知识库目录查询询失败,request query:{}", JsonUtils.toJson(knowledgeCatalogQuery), e);
            return Result.createFailResult("查询发生错误");
        }

    }

    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询车辆信息，id={}", id);
        Result<KnowledgeCatalogInfoRes> result = iKnowledgeBaseService.findKnowledgeCatalogInfoById(id);
        return result;
    }

    @RequestMapping("/detail/get")
    public ModelAndView getDetail(Integer id,String key) {
        logger.info("查询车辆信息，id={},key:{}", id,key);
        ModelAndView mv = new ModelAndView("knowledgeInfo/detail");
        Result<KnowledgeCatalogInfoRes> result = iKnowledgeBaseService.findKnowledgeCatalogInfoById(id);
        KnowledgeCatalogInfoRes info = result.getData();
        if (StringUtils.isNotEmpty(key)) {
            info.setContent(info.getContent().replaceAll(key, "<font color='red'>" + key + "</font>"));
        }
        if (CollectionUtils.isNotEmpty(result.getData().getFileInfos())) {
            result.getData().getFileInfos().forEach(item-> item.setFileUrl(HTTP + bucket + Constants.POINT + ossUrl + Constants.SLASH+item.getFileUrl()));
        }
        mv.addObject("item", info);
        return mv;
    }

    @LogAspect(type = LogAspect.LogType.Update_KnowledgeCatalogInfo, objectNames = SaveKnowledgeCatalogInfo.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(SaveKnowledgeCatalogInfo knowledgeCatalog, String fileUrl) {
        try {
            Result<KnowledgeCatalog> temp = iKnowledgeBaseService.findKnowledgeCatalogById(knowledgeCatalog.getCatelogId());
            if (temp.success()) {
                knowledgeCatalog.setCatelogName(temp.getData().getCatalogName());
                if (StringUtils.isNotEmpty(fileUrl)) {
                    knowledgeCatalog.setFileNames(fileUrl);
                }
                Result result = iKnowledgeBaseService.saveKnowledgeCatalogInfo(knowledgeCatalog);
                return result;
            } else {
                logger.info("未找到知识库目录对应的记录 req:{}",JsonUtils.toJson(knowledgeCatalog));
                return Result.createFailResult("未找到知识库目录对应的记录");
            }

        } catch (Exception e) {
            logger.error("保存知识库目录 admin接口返回失败，req:{}", JsonUtils.toJson(knowledgeCatalog),e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Del_KnowledgeCatalogInfo, argNames = {"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {
        try {
            Result result = iKnowledgeBaseService.delInfoById(id);
            return result;
        } catch (Exception e) {
            logger.error("删除知识库内容admin接口返回失败，req:{}", id, e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Upload_KnowledgeCatalogInfo)
    @RequestMapping("/ossUpload")
    @ResponseBody
    public Result<?> ossUpload(MultipartFile[] file) throws IOException {
        logger.info("ossUpload:{}",file.length);
        if(file == null || file.length == 0) {
            return Result.createFailResult("请选择文件！");
        }

        OSSClient ossClient = OssET.createOSSClient();
        StringBuilder str = new StringBuilder();
        //文件完整路径，不能以/开发
        Arrays.asList(file).stream().forEach(item->{
            String newFileName = "test/" + UUID.randomUUID().toString();
            int lastSeparator = item.getOriginalFilename().lastIndexOf(".");
            if(lastSeparator >= 0) {
                newFileName += item.getOriginalFilename().substring(lastSeparator);
            }
            try {
                ossClient.putObject(bucket, newFileName, item.getInputStream());
                str.append(newFileName+Constants.LINELINE+item.getOriginalFilename()).append(Constants.COMMA);
            } catch (Exception e) {
                logger.error("上传失败,文件名:{}", item.getOriginalFilename());
            }
        });
        String result = str.toString();

        return Result.createSuccessResult(result.substring(0,result.length()-1));
    }
}
