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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.xai.tt.dc.client.inter.IAncmNewsBaseService;
import com.xai.tt.dc.client.inter.IKnowledgeBaseService;
import com.xai.tt.dc.client.model.T14AncmNews;
import com.xai.tt.dc.client.query.KeyWordsQuery;
import com.xai.tt.dc.client.query.KnowledgeCatalogQuery;
import com.xai.tt.dc.client.request.SaveAncmNewInfo;
import com.xai.tt.dc.client.request.SaveKnowledgeCatalogInfo;
import com.xai.tt.dc.client.response.AncmNewsInfoRes;
import com.xai.tt.dc.client.response.KnowledgeCatalogInfoRes;

/**
 * 
 */
@Controller
@RequestMapping("ancmNews")
public class AncmNewsController   extends BaseController {

    @Resource
    private IAncmNewsBaseService iAncmNewsBaseService;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.endpoint}")
    private String ossUrl;

    private static final String HTTP = "http://";

    @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("ancmNews/list");
 //       mav.addObject("catalogList", iAncmNewsBaseService.queryPage(new KnowledgeCatalogQuery()).getData().getList());
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
    public Result<?> queryPage(KeyWordsQuery keyWordsQuery, PageParam pageParam) {
    	logger.info("queryPage");
        try {
        	keyWordsQuery.setPageNo(pageParam.getPageNum());
        	keyWordsQuery.setPageSize(pageParam.getPageSize());
            Result<PageInfo<T14AncmNews>> result = iAncmNewsBaseService.queryPage(keyWordsQuery);
            if (result == null || result.getCode() != 0 ) {
                Result.createFailResult("未查询到结果");
            }
            if(null != result.getData() && null != result.getData().getList()) {
	            if (CollectionUtils.isNotEmpty(result.getData().getList()) && StringUtils.isNotBlank(keyWordsQuery.getKeyWorlds())) {
	                List<T14AncmNews> infos = result.getData().getList();
	                infos.forEach(item-> item.setContent(item.getContent().replaceAll(keyWordsQuery.getKeyWorlds(), "<font color='red'>" + keyWordsQuery.getKeyWorlds() + "</font>")));
	                PageData pageData = new PageData(result.getData().getTotal(), infos);
	                return Result.createSuccessResult(pageData);
	            }
            }
            PageData pageData = new PageData(result.getData().getTotal(), result.getData().getList());
            return Result.createSuccessResult(pageData);
        } catch (Exception e) {
            logger.error("公告新闻分页查询询失败,request query:{}", JsonUtils.toJson(keyWordsQuery), e);
            return Result.createFailResult("查询发生错误");
        }

    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询公告新闻信息，id={}", id);
        Result<AncmNewsInfoRes> result = iAncmNewsBaseService.findAncmNewsById(id);
        return result;
    }

    @RequestMapping(value = "/ancmNewsList", method=RequestMethod.GET)
    public ModelAndView ancmNewsList() {
        logger.info("ancmNewsList");
        ModelAndView mv = new ModelAndView("ancmNews/ancmNewsList");
        return mv;
    }
    
    @RequestMapping(value = { "list_by_ar" })
    public ModelAndView list_by_ar(@RequestParam(name="arId") String arId) {
 //   public ModelAndView list_by_ar() {
    	logger.info("list_by_ar 请求参数arId=" + arId);
    	ModelAndView mav = new ModelAndView("orderManagement/list_by_ar");
    	mav.addObject("arId", arId);
    	return mav;
    }
    
    @RequestMapping(value = { "newsContent" })
    public ModelAndView newsContent(@RequestParam(name="id") String id) {
        logger.info("newsContent, id=" + id);
        ModelAndView mv = new ModelAndView("ancmNews/ancmNewsContent");
        mv.addObject("id", id);
        return mv;
    }
    
    @RequestMapping("/detail/get")
    public ModelAndView getDetail(Integer id,String key) {
        logger.info("查询公告新闻信息，id={},key:{}", id,key);
        ModelAndView mv = new ModelAndView("ancmNews/detail");
        Result<AncmNewsInfoRes> result = iAncmNewsBaseService.findAncmNewsById(id);
        AncmNewsInfoRes info = result.getData();
        if (StringUtils.isNotEmpty(key)) {
            info.setContent(info.getContent().replaceAll(key, "<font color='red'>" + key + "</font>"));
        }
        if(null != result && null != result.getData() && null != result.getData().getFileInfos()) {
	        if(CollectionUtils.isNotEmpty(result.getData().getFileInfos())) {
	            result.getData().getFileInfos().forEach(item-> item.setFileUrl(HTTP + bucket + Constants.POINT + ossUrl + Constants.SLASH+item.getFileUrl()));
	        }
        }
        mv.addObject("item", info);
        return mv;
    }

    @LogAspect(type = LogAspect.LogType.Update_KnowledgeCatalogInfo, objectNames = SaveKnowledgeCatalogInfo.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(SaveAncmNewInfo saveAncmNewInfo, String fileUrl) {
        try {
                if (StringUtils.isNotEmpty(fileUrl)) {
                	saveAncmNewInfo.setFileNames(fileUrl);
                }
                
               	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
               	saveAncmNewInfo.setUsername(user.getUsername());
               	
                Result result = iAncmNewsBaseService.saveAncmNewInfo(saveAncmNewInfo);
                return result;
        } catch (Exception e) {
            logger.error("保存知识库目录 admin接口返回失败，req:{}", JsonUtils.toJson(saveAncmNewInfo),e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Del_KnowledgeCatalogInfo, argNames = {"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {
        try {
            Result result = iAncmNewsBaseService.delInfoById(id);
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
