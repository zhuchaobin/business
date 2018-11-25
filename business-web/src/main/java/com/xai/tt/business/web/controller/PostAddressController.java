package com.xai.tt.business.web.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.support.OssET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.inter.PostAddressDcService;
import com.xai.tt.dc.client.query.SubmitArQuery;
import com.xai.tt.dc.client.service.TB0001DcService;
import com.xai.tt.dc.client.vo.T1ARInfVo;
import com.xai.tt.dc.client.vo.inVo.TB0001InVo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/*
 * 
 * @ClassName:  postAddressController   
 * @Description:长约信息信息管理
 * @author: zhuchaobin
 * @date:   2018年7月10日 下午7:00:23  
 *
 */
@Controller
@RequestMapping("postAddress")
public class PostAddressController extends BaseController {
	
	@Autowired
	private PostAddressDcService postAddressDcService;
	@Autowired
	private TB0001DcService tB0001DcService2;
	
    @RequestMapping(value = { "add" })
    @ResponseBody
    public ModelAndView add(TB0001InVo postAddressDcQuery, PageParam pageParam) {
        ModelAndView mav = new ModelAndView("postAddress/add");
        return mav;
    }
    
    @RequestMapping(value = { "save" })
    @ResponseBody
    public Result<?>  save(TB0001InVo inVo, PageParam pageParam) {
    	logger.info("保存长约信息请求报文：{}", JSON.toJSONString(inVo));
//    	logger.info("长约附件信息长度：{}", inVo.getList().size());
       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	inVo.setUserType(user.getUserType().ordinal());
       	inVo.setUsername(user.getUsername());
       	inVo.setCompanyId(user.getCompanyId());
       	inVo.setNickname(user.getNickname());
       	inVo.setChineseName(user.getChineseName());
    	Result<Boolean> result = tB0001DcService2.save(inVo);
    	logger.info("保存长约信息返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    
    
    @RequestMapping(value = { "submitAr" })
    @ResponseBody
    public Result<?>  submitAr(SubmitArQuery query) {
    	logger.info("提交长约请求报文：{}", JSON.toJSONString(query));
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	query.setUsername(user.getUsername());
    	query.setCompanyId(user.getCompanyId());
    	Result<Boolean> result = tB0001DcService2.submitAr(query);
    	logger.info("提交长约返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    
    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(TB0001InVo tB0001InVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	tB0001InVo.setUserType(user.getUserType().ordinal());
    	tB0001InVo.setUsername(user.getUsername());
    	tB0001InVo.setCompanyId(user.getCompanyId());
    	tB0001InVo.setNickname(user.getNickname());
    	tB0001InVo.setChineseName(user.getChineseName());
    	logger.info("长约信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(tB0001InVo),JSON.toJSONString(pageParam));
        Result<PageData<T1ARInfVo>> result = tB0001DcService2.queryPage(tB0001InVo, pageParam);
        logger.info("长约信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "getDetail" })
    @ResponseBody
    public Result<?>  getDetail(String id) {
    	logger.info("查询长约详情，请求参数id=：{}", id);
    	Result<T1ARInfVo> rlt = tB0001DcService2.queryArDetail(id);
    	logger.info("查询长约详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());
    }
    
    @RequestMapping(value = { "list" })
    @ResponseBody
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("postAddress/list");
/*        mav.addObject("catalogList", iKnowledgeBaseService.queryPage(new KnowledgeCatalogQuery()).getData().getList());
        LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	UserType userType = user.getUserType();
    	if(user.hasRole("ROLE_ADMIN")){
    		userType = UserType.Group;
    	}
    	mav.addObject("userType", userType);*/
    	mav.addObject("userType", "Group");
        return mav;
        
/*    	logger.info("长约信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(postAddressDcQuery),JSON.toJSONString(pageParam));
        Result<PageData<T1ARInfVo>> result = tB0001DcService2.queryPage(postAddressDcQuery, pageParam);

        return Result.createSuccessResult(result.getData());*/
    }
    
    @RequestMapping("/ossUpload")
    @ResponseBody
    public Result<?> ossUpload(MultipartFile[] file) throws IOException {
        logger.info("ossUpload:{}",file.length);
        if(file == null || file.length == 0) {
            return Result.createFailResult("请选择文件！");
        }

//        OSSClient ossClient = OssET.createOSSClient();
        StringBuilder str = new StringBuilder();
        //文件完整路径，不能以/开发
        Arrays.asList(file).stream().forEach(item->{
            String newFileName = "d:\\test\\" + UUID.randomUUID().toString();
            int lastSeparator = item.getOriginalFilename().lastIndexOf(".");
            if(lastSeparator >= 0) {
                newFileName += item.getOriginalFilename().substring(lastSeparator);
            }
/*            try {
                ossClient.putObject(bucket, newFileName, item.getInputStream());
                str.append(newFileName+Constants.LINELINE+item.getOriginalFilename()).append(Constants.COMMA);
            } catch (Exception e) {
                logger.error("上传失败,文件名:{}", item.getOriginalFilename());
            }*/
            str.append(newFileName+Constants.LINELINE+item.getOriginalFilename()).append(Constants.COMMA);
            byte[] bytes;
            try {
            	logger.error("aaaaaaaaaaaa" + file);	
            bytes = item.getBytes();
            FileOutputStream fos = new FileOutputStream(newFileName);
            fos.write(bytes);
            fos.close();
            logger.error("aaaaaaaaaaaa");
            } catch (IOException e) {
/*    	        response.setException(e);
    	        response.setErrorMessage("文件写入错误！");
    	       response.setResponseCode(-1);*/
            	logger.error("xxxxx" + e);
            }
        });
        String result = str.toString();
        logger.debug("result=" + result);

        return Result.createSuccessResult(result.substring(0,result.length()-1));
    
    }

    @RequestMapping("/ossDelete")
    @ResponseBody
    public Result<?> ossDelete(String fileName) throws IOException {
        logger.info("ossUpload:{}",fileName);
        if(fileName == null) {
            return Result.createFailResult("文件名为空！");
        }
        OSSClient ossClient = OssET.createOSSClient();
        //文件完整路径，不能以/开发
        try {
//            ossClient.deleteObject(bucket, fileName);
        } catch (Exception e) {
            logger.error("上传失败,文件名:{}", fileName);
        }

        return Result.createSuccessResult(fileName);
    }

    @RequestMapping("/ossGet")
    @ResponseBody
    public Result<?> ossGet(String fileName) throws IOException {
        logger.info("ossUpload:{}",fileName);
        if(fileName == null) {
            return Result.createFailResult("文件名为空！");
        }
        OSSClient ossClient = OssET.createOSSClient();
        //文件完整路径，不能以/开发
        OSSObject oo = null;
        try {
//            oo = ossClient.getObject(bucket, fileName);
        } catch (Exception e) {
            logger.error("上传失败,文件名:{}", fileName);
        }
        if(oo == null) {
            return Result.createSuccessResult("");
        }

        return Result.createSuccessResult(oo.getResponse().getUri());
    }
       
}
