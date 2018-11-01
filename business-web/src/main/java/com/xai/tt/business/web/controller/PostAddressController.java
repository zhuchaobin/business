package com.xai.tt.business.web.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.core.support.OssET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.dc.client.inter.PostAddressDcService;
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
 * @Description:收货地址信息管理
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
	
/*    @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("postAddress/list");

        CarModelQuery query = new CarModelQuery();
        query.setStatus(true);
        query.setPageNo(1);
        query.setPageSize(Integer.MAX_VALUE);
        Result<PageInfo<CarModelBaseVo>> carModelResult = carModelService.queryPage(query);
        if (carModelResult == null || carModelResult.getCode() != 0) {
            throw new RuntimeException("查询车型异常");
        }
        mv.addObject("carModels", carModelResult.getData().getList());

        LoginUser user = (LoginUser) SecurityContext.getAuthUser();
        mv.addObject("usertype", user.getUserType().name());
        return mv;
    	
    	ModelAndView mv = new ModelAndView("postAddress/list");

		T1ARInfVo query = new T1ARInfVo();

		Result<PageData<T1ARInfVo>> rlt = tB0001DcService2.queryPage(query, null);
		
        CarModelQuery query = new CarModelQuery();
        query.setStatus(true);
        query.setPageNo(1);
        query.setPageSize(Integer.MAX_VALUE);
        Result<PageInfo<CarModelBaseVo>> carModelResult = carModelService.queryPage(query);
        if (carModelResult == null || carModelResult.getCode() != 0) {
            throw new RuntimeException("查询车型异常");
        }
        mv.addObject("carModels", carModelResult.getData().getList());

        LoginUser user = (LoginUser) SecurityContext.getAuthUser();
        mv.addObject("usertype", user.getUserType().name());
        return mv;
    	return null;
    }
*/
	
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
    	Result<Boolean> result = tB0001DcService2.save(inVo);
    	logger.info("保存长约信息返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    
    
/*    @RequestMapping(value = { "list" })
    @ResponseBody
    public ModelAndView list(TB0001InVo postAddressDcQuery, PageParam pageParam) {
    	ModelAndView mav = new ModelAndView("postAddress/list");
        mav.addObject("catalogList", iKnowledgeBaseService.queryPage(new KnowledgeCatalogQuery()).getData().getList());
        LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	UserType userType = user.getUserType();
    	if(user.hasRole("ROLE_ADMIN")){
    		userType = UserType.Group;
    	}
    	mav.addObject("userType", userType);
        return mav;
        
    	logger.info("收货地址查询请求参数:{}，分页参数：{}", JSON.toJSONString(postAddressDcQuery),JSON.toJSONString(pageParam));
        Result<PageData<T1ARInfVo>> result = tB0001DcService2.queryPage(postAddressDcQuery, pageParam);

        return Result.createSuccessResult(result.getData());
    }*/
    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(TB0001InVo postAddressDcQuery, PageParam pageParam) {
        
    	logger.info("收货地址查询请求参数:{}，分页参数：{}", JSON.toJSONString(postAddressDcQuery),JSON.toJSONString(pageParam));
        Result<PageData<T1ARInfVo>> result = tB0001DcService2.queryPage(postAddressDcQuery, pageParam);
        return Result.createSuccessResult(result.getData());
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
        
/*    	logger.info("收货地址查询请求参数:{}，分页参数：{}", JSON.toJSONString(postAddressDcQuery),JSON.toJSONString(pageParam));
        Result<PageData<T1ARInfVo>> result = tB0001DcService2.queryPage(postAddressDcQuery, pageParam);

        return Result.createSuccessResult(result.getData());*/
    }
    
    @RequestMapping("/ossUpload")
    @ResponseBody
    public Result<?> ossUpload(MultipartFile[] file) throws IOException {
 /*       logger.info("ossUpload:{}",files.length);
        if(files == null || files.length == 0) {
            return Result.createFailResult("请选择文件！");
        }
        if(files.length != 1) {
            return Result.createFailResult("只能上传一个文件文件！");
        }
        String fileName = files[0].getOriginalFilename();
        if(!fileName.contains(".jpg") && !fileName.contains(".png")) {
            return Result.createFailResult("只能上传jpg或png文件！");
        }
        MultipartFile file = files[0];

        OSSClient ossClient = OssET.createOSSClient();
        String str = "";
        //文件完整路径，不能以/开发
        PutObjectResult por = null;
        String newFileName = "test/" + UUID.randomUUID().toString();
        int lastSeparator = file.getOriginalFilename().lastIndexOf(".");
        if(lastSeparator >= 0) {
            newFileName += file.getOriginalFilename().substring(lastSeparator);
        }
        try {
            por = ossClient.putObject(bucket, newFileName, file.getInputStream());
            str = newFileName+"||"+file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传失败,文件名:{}", file.getOriginalFilename());
        }
        
        str = newFileName+"||"+file.getOriginalFilename();
        byte[] bytes;
        try {
        	logger.error("aaaaaaaaaaaa" + file);	
        bytes = file.getBytes();
        FileOutputStream fos = new FileOutputStream("D:\\2.jpg");
        fos.write(bytes);
        fos.close();
        logger.error("aaaaaaaaaaaa");
        } catch (IOException e) {
	        response.setException(e);
	        response.setErrorMessage("文件写入错误！");
	       response.setResponseCode(-1);
        	logger.error("xxxxx" + e);
        }

        return Result.createSuccessResult(str);
   */     
        

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
    
 /*   @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询收货地址信息，id={}", id);
        Result<postAddress> result = postAddressDcService.queryrById(id);
        logger.info("查询养参数信息返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(postAddress model) {
        logger.info("保存收货地址信息：{}", JSON.toJSONString(model));

        if(StringUtils.isBlank(model.getName())) {
        	Log.error("保存或新增收货地址信息, [套餐名称]不能为空！");
        	return Result.createFailResult("保存或新增收货地址信息, [套餐名称]不能为空！");
        }
        if(null == model.getFlowQuantity()) {
        	Log.error("保存或新增收货地址信息, [流量]不能为空！");
        	return Result.createFailResult("保存或新增收货地址信息, [流量]不能为空！");
        }
        if(null == model.getEffMonth()) {
        	Log.error("保存或新增收货地址信息, [有效期]不能为空！");
        	return Result.createFailResult("保存或新增收货地址信息, [有效期]不能为空！");
        }
        if(StringUtils.isBlank(model.getOperatorFlag())) {
        	Log.error("保存或新增收货地址信息, [运营商标志]不能为空！");
        	return Result.createFailResult("保存或新增收货地址信息, [运营商标志]不能为空！");
        }
        if(StringUtils.isBlank(model.getWhiteList())) {
        	Log.error("保存或新增收货地址信息, [白名单]不能为空！");
        	return Result.createFailResult("保存或新增收货地址信息, [白名单]不能为空！");
        }
        Result<?> result = postAddressDcService.updateOrInsert(model);
        logger.info("保存收货地址信息返回结果：{}", JSON.toJSONString(result));

        return result;
    }
    
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {
        logger.info("删除收货地址信息，id={}", id);
        if(null == id) {
        	Log.error("删除收货地址信息, [收货地址ID]不能为空！");
        	return Result.createFailResult("删除收货地址信息, [收货地址ID]不能为空！");
        }
        Result<?> result = postAddressDcService.deleteById(id);
        logger.info("删除养参数信息返回结果：{}", JSON.toJSONString(result));
        return result;
    }*/
   
}
