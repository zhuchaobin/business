package com.xai.tt.business.web.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.support.OssET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.model.Company;
import com.xai.tt.dc.client.model.T13GdsDetail;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.query.KnowledgeCatalogQuery;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.service.PlgAplyDcService;
import com.xai.tt.dc.client.vo.inVo.PlgAplyInVo;
import com.xai.tt.dc.client.vo.outVo.QueryArSubmmitDetailOutVo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/*
 * 
 * @ClassName:  plgAplyController   
 * @Description:质押信息管理
 * @author: zhuchaobin
 * @date:   
 * 
 */
@Controller
@RequestMapping("plgAply")
public class plgAplyController extends BaseController {
	@Autowired
	private PlgAplyDcService plgAplyDcService;
	@Autowired
	private CompanyDcService companyDcService;    
    @RequestMapping(value = { "save" })
    @ResponseBody
    public Result<?>  save(String inVo, String detail, String fileUrl) {
    	logger.info("保存质押请求报文：plgAplyManagementInVo={}, fileUrl={}", JSON.toJSONString(inVo), JSON.toJSONString(fileUrl));

		List<T13GdsDetail> detailList = JSON.parseArray(detail, T13GdsDetail.class);
		logger.info("保存质押请求报文,货物明细：detailList={}", JSON.toJSONString(detailList));
		PlgAplyInVo plgAplyInVo = JSON.parseObject(inVo,PlgAplyInVo.class);
        if (StringUtils.isNotEmpty(fileUrl)) {
        	plgAplyInVo.setFileNames(fileUrl);
        }
   // 	logger.info("长约附件信息长度：{}", plgAplyInVo.getList().size());
       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	plgAplyInVo.setUserType(user.getUserType().ordinal());
       	plgAplyInVo.setUsername(user.getUsername());
       	plgAplyInVo.setCompanyId(user.getCompanyId());
       	plgAplyInVo.setNickname(user.getNickname());
       	plgAplyInVo.setChineseName(user.getChineseName());
       	plgAplyInVo.setT13GdsDetailList(detailList);
       	// 
    	Result<Boolean> result = plgAplyDcService.save(plgAplyInVo);
    	logger.info("保存质押返回结果：{}", JSON.toJSONString(result));
        return result;
    }  
    
    // 质押审核提交
    @RequestMapping(value = { "adt" })
    @ResponseBody
    public Result<?>  adt(String inVo, String detail, String fileUrl) {
    	logger.info("保存质押请求报文：inVo={}, fileUrl={}", JSON.toJSONString(inVo), JSON.toJSONString(fileUrl));

		List<T13GdsDetail> detailList = JSON.parseArray(detail, T13GdsDetail.class);
		logger.info("保存质押请求报文,货物明细：detailList={}", detailList);
		PlgAplyInVo plgAplyInVo = JSON.parseObject(inVo,PlgAplyInVo.class);
        if (StringUtils.isNotEmpty(fileUrl)) {
        	plgAplyInVo.setFileNames(fileUrl);
        }
   // 	logger.info("长约附件信息长度：{}", plgAplyInVo.getList().size());
       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	plgAplyInVo.setUserType(user.getUserType().ordinal());
       	plgAplyInVo.setUsername(user.getUsername());
       	plgAplyInVo.setCompanyId(user.getCompanyId());
       	plgAplyInVo.setNickname(user.getNickname());
       	plgAplyInVo.setChineseName(user.getChineseName());
       	plgAplyInVo.setT13GdsDetailList(detailList);
       	// 
       	logger.info("保存质押拼装后请求报文：plgAplyInVo={}", JSON.toJSONString(plgAplyInVo));
    	Result<Boolean> result = plgAplyDcService.adt(plgAplyInVo);
    	logger.info("保存质押返回结果：{}", JSON.toJSONString(result));
        return result;
    }  
    
    @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("plgAply/list");
      	CompanyQuery query = new CompanyQuery(); 

       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	logger.info("user.getUserType() =" + user.getUserType());
    	mav.addObject("userType", user.getUserType());
    	
        Result<PageInfo<Company>> result = companyDcService.queryPage(query);
        mav.addObject("pltfrmModels", result.getData().getList());
    	// 查询上游供应商下拉菜单
        query.setUsrTp("02");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[上游供应商]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[上游供应商]公司信息异常");
//            throw new RuntimeException("查询[上游供应商]公司信息异常");
        }
        mav.addObject("ustrmSplrModels", result.getData().getList());

    	// 查询融资企业下拉菜单
        query.setUsrTp("04");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[融资企业]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[融资企业]公司信息异常");
//            throw new RuntimeException("查询[融资企业]公司信息异常");
        }
        mav.addObject("fncEntpModels", result.getData().getList());

    	// 查询银行下拉菜单
        query.setUsrTp("06");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[银行]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[银行]公司信息异常");
//            throw new RuntimeException("查询[银行]公司信息异常");
        }
        mav.addObject("bnkModels", result.getData().getList());
    	// 查询上游供应商下拉菜单
        query.setUsrTp("02");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[上游供应商]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[上游供应商]公司信息异常");
//            throw new RuntimeException("查询[上游供应商]公司信息异常");
        }
        mav.addObject("ustrmSplrModels", result.getData().getList());
    	// 查询仓储公司下拉菜单
        query.setUsrTp("08");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[仓储公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[仓储公司]公司信息异常");
//            throw new RuntimeException("查询[仓储公司]公司信息异常");
        }
        mav.addObject("stgcoModels", result.getData().getList());    
        return mav;
    }
  
    @RequestMapping(value = { "delete" })
    @ResponseBody
    public Result<?>   delete(String id) {
    	logger.info("删除质押信息，请求参数id=：{}", id);
    	Result<Boolean> rlt = plgAplyDcService.delete(id);
    	logger.info("删除质押信息，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(PlgAplyInVo inVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	// 因前后端名字不一样，转义排序参数
//    	String sortName = inVo.getSortName();
    		
    	logger.info("质押查询请求参数:{}，分页参数：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
        Result<PageData<PlgAplyInVo>> result = plgAplyDcService.queryPage(inVo, pageParam);
        logger.info("质押查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
          
    @RequestMapping(value = { "getDetail" })
    @ResponseBody
    public Result<?>   getDetail(String id) {
    	logger.info("查询长约详情，请求参数id=：{}", id);
    	PlgAplyInVo inVo = new PlgAplyInVo();
    	inVo.setId(Long.parseLong(id));
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	Result<PlgAplyInVo> rlt = plgAplyDcService.queryDetail(inVo);
    	logger.info("查询长约详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
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
        // 文件名重复去重
        Map<String, Integer> fileExistMap = new HashMap<String, Integer>();
        Arrays.asList(file).stream().forEach(item->{
        	// 判断文件是否已存在
        	if(null != fileExistMap.get(item.getOriginalFilename()) && (1== fileExistMap.get(item.getOriginalFilename())))
        		return;
        	else
        		fileExistMap.put(item.getOriginalFilename(), 1);
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
            	logger.debug("上传文件：" + file);	
	            bytes = item.getBytes();
	            FileOutputStream fos = new FileOutputStream(newFileName);
	            fos.write(bytes);
	            fos.close();
            } catch (IOException e) {
            	logger.error("文件写入错误" + e);
            	return;
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
    
    public ModelAndView lists(Integer flag) {
    	ModelAndView mav = null;
    	if(1 == flag)
    		mav = new ModelAndView("plgAply/list_ing");
    	else if(2 == flag)
    		mav = new ModelAndView("plgAply/list_adt");
    	else if(3 == flag)
    		mav = new ModelAndView("plgAply/list_fnsh");
    	else if(5 == flag)
    		mav = new ModelAndView("plgAply/list_adted");
    	
       	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	logger.info("user.getUserType() =" + user.getUserType());
    	mav.addObject("userType", user.getUserType());
    	
    	logger.info("flag=" + flag);
    	CompanyQuery query = new CompanyQuery(); 
    	// 查询平台下拉菜单
        query.setUsrTp("01");  	
        Result<PageInfo<Company>> result = companyDcService.queryPage(query);
    	logger.info("查询[平台]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[平台]公司信息异常");
//            throw new RuntimeException("查询[平台]公司信息异常");
        }
        mav.addObject("pltfrmModels", result.getData().getList());
    	// 查询上游供应商下拉菜单
        query.setUsrTp("02");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[上游供应商]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[上游供应商]公司信息异常");
//            throw new RuntimeException("查询[上游供应商]公司信息异常");
        }
        mav.addObject("ustrmSplrModels", result.getData().getList());
    	// 查询供应链公司下拉菜单
        query.setUsrTp("03");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[供应链公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[供应链公司]公司信息异常");
//            throw new RuntimeException("查询[供应链公司]公司信息异常");
        }
        mav.addObject("splchainCoModels", result.getData().getList());
    	// 查询融资企业下拉菜单
        query.setUsrTp("04");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[融资企业]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[融资企业]公司信息异常");
//            throw new RuntimeException("查询[融资企业]公司信息异常");
        }
        mav.addObject("fncEntpModels", result.getData().getList());
    	// 查询保险公司下拉菜单
        query.setUsrTp("05");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[保险公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[保险公司]公司信息异常");
//            throw new RuntimeException("查询[保险公司]公司信息异常");
        }
        mav.addObject("insCoModels", result.getData().getList());
    	// 查询银行下拉菜单
        query.setUsrTp("06");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[银行]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[银行]公司信息异常");
//            throw new RuntimeException("查询[银行]公司信息异常");
        }
        mav.addObject("bnkModels", result.getData().getList());
    	// 查询物流公司下拉菜单
        query.setUsrTp("07");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[物流公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[物流公司]公司信息异常");
//            throw new RuntimeException("查询[物流公司]公司信息异常");
        }
        mav.addObject("lgstcCoModels", result.getData().getList());
    	// 查询仓储公司下拉菜单
        query.setUsrTp("08");
    	result = companyDcService.queryPage(query);
    	logger.info("查询[仓储公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[仓储公司]公司信息异常");
//            throw new RuntimeException("查询[仓储公司]公司信息异常");
        }
        mav.addObject("stgcoModels", result.getData().getList());    
//    	mav.addObject("userType", "Group");
        return mav;
    } 
       
}
