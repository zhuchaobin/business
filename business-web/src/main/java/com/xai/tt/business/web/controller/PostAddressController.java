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
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.inter.PostAddressDcService;
import com.xai.tt.dc.client.model.T0LnkJrnlInf;
import com.xai.tt.dc.client.query.SubmitArQuery;
import com.xai.tt.dc.client.service.ArManagementDcService;
import com.xai.tt.dc.client.vo.T1ARInfDetailVo;
import com.xai.tt.dc.client.vo.T1ARInfVo;
import com.xai.tt.dc.client.vo.inVo.ArManagementInVo;
import com.xai.tt.dc.client.vo.outVo.QueryPageArOutVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
	private ArManagementDcService ArManagementDcService2;
	
    @RequestMapping(value = { "add" })
    @ResponseBody
    public ModelAndView add(ArManagementInVo postAddressDcQuery, PageParam pageParam) {
        ModelAndView mav = new ModelAndView("postAddress/add");
        return mav;
    }
    
    @RequestMapping(value = { "save" })
    @ResponseBody
    public Result<?>  save(ArManagementInVo inVo, String fileUrl) {
    	logger.info("保存长约信息请求报文：ArManagementInVo={}, fileUrl={}", JSON.toJSONString(inVo), JSON.toJSONString(fileUrl));
        if (StringUtils.isNotEmpty(fileUrl)) {
        	inVo.setFileNames(fileUrl);
        }
//    	logger.info("长约附件信息长度：{}", inVo.getList().size());
       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	inVo.setUserType(user.getUserType().ordinal());
       	inVo.setUsername(user.getUsername());
       	inVo.setCompanyId(user.getCompanyId());
       	inVo.setNickname(user.getNickname());
       	inVo.setChineseName(user.getChineseName());
    	Result<Boolean> result = ArManagementDcService2.save(inVo);
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
    	Result<Boolean> result = ArManagementDcService2.submitAr(query);
    	logger.info("提交长约返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    
    
    @RequestMapping(value = { "queryLnkJrnlInfPage" })
    @ResponseBody
    public Result<?>  queryLnkJrnlInfPage(ArManagementInVo ArManagementInVo, PageParam pageParam) {
/*    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	ArManagementInVo.setUserType(user.getUserType().ordinal());
    	ArManagementInVo.setUsername(user.getUsername());
    	ArManagementInVo.setCompanyId(user.getCompanyId());
    	ArManagementInVo.setNickname(user.getNickname());
    	ArManagementInVo.setChineseName(user.getChineseName());
    	logger.info("长约信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(ArManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<T0LnkJrnlInf>> result = ArManagementDcService2.queryLnkJrnlInfPage(ArManagementInVo, pageParam);
        logger.info("长约信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());*/
    	return null;
    }
    
    @RequestMapping(value = { "queryUploadFilePage" })
    @ResponseBody
    public Result<?>  queryUploadFilePage(ArManagementInVo ArManagementInVo, PageParam pageParam) {
   /* 	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	ArManagementInVo.setUserType(user.getUserType().ordinal());
    	ArManagementInVo.setUsername(user.getUsername());
    	ArManagementInVo.setCompanyId(user.getCompanyId());
    	ArManagementInVo.setNickname(user.getNickname());
    	ArManagementInVo.setChineseName(user.getChineseName());
    	logger.info("长约信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(ArManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<T0LnkJrnlInf>> result = ArManagementDcService2.queryLnkJrnlInfPage(ArManagementInVo, pageParam);
        logger.info("长约信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());*/
    	return null;
    }
    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(ArManagementInVo ArManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	ArManagementInVo.setUserType(user.getUserType().ordinal());
    	ArManagementInVo.setUsername(user.getUsername());
    	ArManagementInVo.setCompanyId(user.getCompanyId());
    	ArManagementInVo.setNickname(user.getNickname());
    	ArManagementInVo.setChineseName(user.getChineseName());
    	logger.info("长约信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(ArManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QueryPageArOutVo>> result = ArManagementDcService2.queryPage(ArManagementInVo, pageParam);
        logger.info("长约信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping("/detail2")
    public ModelAndView  getDetail2(String id) {
    	logger.info("查询长约详情，请求参数id=：{}", id);
/*    	Result<T1ARInfDetailVo> rlt = ArManagementDcService2.queryArDetail(id);
    	logger.info("查询长约详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());*/
        
/*        ModelAndView mv = new ModelAndView("postAddress/detail");
        Result<T1ARInfDetailVo> result = ArManagementDcService2.queryArDetail(id);
        T1ARInfDetailVo info = result.getData();
        logger.info("查询长约详情，返回结果rlt：{}", JSON.toJSONString(result));*/
/*        if (StringUtils.isNotEmpty(key)) {
            info.setContent(info.getContent().replaceAll(key, "<font color='red'>" + key + "</font>"));
        }*/
/*		// 获取工程路径
		String webContentPath = "";
		try {
			String path = Class.class.getResource("/").toURI().getPath();
			webContentPath = new File(path).getParentFile().getParentFile().getCanonicalPath();
			logger.info("webContentPath=" + webContentPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
/*        mv.addObject("item", info);
        return mv; */ 
    	return null;
    }
    
    
    @RequestMapping(value = { "getDetail" })
    @ResponseBody
    public Result<?>   getDetail(String id) {
/*    	logger.info("查询长约详情，请求参数id=：{}", id);
    	Result<T1ARInfDetailVo> rlt = ArManagementDcService2.queryArDetail(id);
    	logger.info("查询长约详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());   */    
    	return null;
    }
    
    @RequestMapping(value = { "list" })
//    @ResponseBody
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
        Result<PageData<T1ARInfVo>> result = ArManagementDcService2.queryPage(postAddressDcQuery, pageParam);

        return Result.createSuccessResult(result.getData());*/
    }
    @LogAspect(type = LogAspect.LogType.Upload_Ar_Atch)
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
			// 获取工程路径
			String webContentPath = "";
			try {
				String path = Class.class.getResource("/").toURI().getPath();
				webContentPath = new File(path).getParentFile().getParentFile().getCanonicalPath();
				logger.info("webContentPath=" + webContentPath);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String newFilePathRear = "\\upload_files\\" + UUID.randomUUID().toString();
			int lastSeparator = item.getOriginalFilename().lastIndexOf(".");
            if(lastSeparator >= 0) {
            	newFilePathRear += item.getOriginalFilename().substring(lastSeparator);
            }
            String newFileName = webContentPath + "\\src\\main\\webapp" + newFilePathRear;

            try {

            } catch (Exception e) { 
                logger.error("上传失败,文件名:{}", item.getOriginalFilename());
            }
            
            str.append(newFilePathRear+Constants.LINELINE+item.getOriginalFilename()).append(Constants.COMMA);
            byte[] bytes;
            try {
            	logger.error("aaaaaaaaaaaa" + file);	
            bytes = item.getBytes();
            FileOutputStream fos = new FileOutputStream(newFileName);
            fos.write(bytes);
            fos.close();
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
