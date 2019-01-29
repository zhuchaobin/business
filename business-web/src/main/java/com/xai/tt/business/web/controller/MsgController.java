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
import com.xai.tt.dc.client.model.T0LnkJrnlInf;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.query.SubmitArQuery;
import com.xai.tt.dc.client.service.ArManagementDcService;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.service.InvInfDcService;
import com.xai.tt.dc.client.service.MsgDcService;
import com.xai.tt.dc.client.vo.T1ARInfDetailVo;
import com.xai.tt.dc.client.vo.T1ARInfVo;
import com.xai.tt.dc.client.vo.MsgVo;
import com.xai.tt.dc.client.vo.inVo.ArManagementInVo;
import com.xai.tt.dc.client.vo.inVo.OrderManagementInVo;
import com.xai.tt.dc.client.vo.outVo.QueryOrderInfDetailOutVo;
import com.xai.tt.dc.client.vo.outVo.QueryPageArOutVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/*
 * 
 * @ClassName:  MsgController   
 * @Description:站内信息信息管理
 * @author: zhuchaobin
 * @date:   2019年1月25日 
 * 
 */
@Controller
@RequestMapping("msgManagement")
public class MsgController extends BaseController {
	
	@Autowired
	private CompanyDcService companyDcService; 
	@Autowired
	private MsgDcService msgDcService;    
     
	   @RequestMapping(value = { "list_snd" })
	    public ModelAndView list_snd() {
	    	ModelAndView mav = new ModelAndView("msgManagement/list");
	    	MsgVo inVo = new MsgVo();
	    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
	    	inVo.setUserType(user.getUserType().ordinal());
	    	inVo.setUsername(user.getUsername());
	    	inVo.setCompanyId(user.getCompanyId());
	    	inVo.setNickname(user.getNickname());
	    	inVo.setChineseName(user.getChineseName());
	    	inVo.setSecSrvCd("01");
	    	
	    	CompanyQuery query = new CompanyQuery();        
	    	// 查询平台下拉菜单
	        query.setUsrTp("01");
	        /*Result<PageInfo<Company>> result = companyDcService.queryPage_skf_fkf(inVo);*/
	        Result<PageInfo<Company>> result = null;
	    	logger.info("查询付款方列表返回结果：{}", JSON.toJSONString(result));
	        if (result == null || result.getCode() != 0) {
	        	logger.error("查询付款方列表返回结果异常");
	        }
	//        mav.addObject("skFkFModels", result.getData().getList());
	        mav.addObject("secSrvCd", "01");
	    	mav.addObject("userType", "Group");
	        return mav;
	    }
	   
	   @RequestMapping(value = { "list_rev" })
	    public ModelAndView list_rev() {
	    	ModelAndView mav = new ModelAndView("msgManagement/list");
	    	MsgVo inVo = new MsgVo();
	    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
	    	inVo.setUserType(user.getUserType().ordinal());
	    	inVo.setUsername(user.getUsername());
	    	inVo.setCompanyId(user.getCompanyId());
	    	inVo.setNickname(user.getNickname());
	    	inVo.setChineseName(user.getChineseName());
	    	inVo.setSecSrvCd("02");
	    	
	    	CompanyQuery query = new CompanyQuery();        
	    	// 查询平台下拉菜单
	        query.setUsrTp("01");
	        /*Result<PageInfo<Company>> result = companyDcService.queryPage_skf_fkf(inVo);*/
	        Result<PageInfo<Company>> result = null;
	    	logger.info("查询付款方列表返回结果：{}", JSON.toJSONString(result));
	        if (result == null || result.getCode() != 0) {
	        	logger.error("查询付款方列表返回结果异常");
	        }
	        mav.addObject("secSrvCd", "02");
	    	mav.addObject("userType", "Group");
	        return mav;
	    }
    
    @RequestMapping(value = { "queryCompanyModels" })
    public ModelAndView queryCompanyModels(CompanyQuery query) {
    	logger.info("查询公司信息请求报文：CompanyQuery={}", JSON.toJSONString(query));
    	ModelAndView mv = new ModelAndView("arManagement/add");
    	Result<PageInfo<Company>> result = companyDcService.queryPage(query);
    	logger.info("查询公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
            throw new RuntimeException("公司信息异常");
        }
        mv.addObject("CompanyModels", result.getData().getList());

        return mv;
    }  
   
    @RequestMapping(value = { "getDetail" })
    @ResponseBody
    public Result<?>   getDetail(String id, String secSrvCd, String msgRevId) {
    	logger.info("查询站内详情，请求参数id=：{} secSrvCd=：{} msgRevId=：{}", id, secSrvCd, msgRevId);
    	MsgVo inVo = new MsgVo();
    	inVo.setId(Long.parseLong(id));
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	Result<MsgVo> rlt = msgDcService.queryDetail(id, secSrvCd, msgRevId);
    	logger.info("查询站内详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "delete" })
    @ResponseBody
    public Result<?>   delete(String id) {
    	logger.info("删除站内，请求参数id=：{}", id);
    	Result<Boolean> rlt = msgDcService.delete(id);
    	logger.info("删除站内，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "getWdMgsNum" })
    @ResponseBody
    /*查询未读消息数目*/
	Result<Integer> getWdMgsNum(){
      	MsgVo inVo = new MsgVo();
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	inVo.setCurrentUserId(user.getId());
		inVo.setSecSrvCd("02");//收信
		inVo.setMsgRevStcd("03");//未读
    	Result<Integer> rlt = msgDcService.getWdMgsNum(inVo);
    	return rlt;
	}
    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(MsgVo queryPageInvInfVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	queryPageInvInfVo.setUserType(user.getUserType().ordinal());
    	queryPageInvInfVo.setUsername(user.getUsername());
    	queryPageInvInfVo.setCompanyId(user.getCompanyId());
    	queryPageInvInfVo.setNickname(user.getNickname());
    	queryPageInvInfVo.setChineseName(user.getChineseName());    
//    	queryPageInvInfVo.setSecSrvCd("01");
    	queryPageInvInfVo.setCurrentUserId(user.getId());
    	// 因前后端名字不一样，转义排序参数
    	String sortName = queryPageInvInfVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		queryPageInvInfVo.setSortName(sortName);
    	}
    		
    	logger.info("站内信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(queryPageInvInfVo),JSON.toJSONString(pageParam));
        Result<PageData<MsgVo>> result = msgDcService.queryPage(queryPageInvInfVo, pageParam);
        logger.info("站内信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
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
          
}
