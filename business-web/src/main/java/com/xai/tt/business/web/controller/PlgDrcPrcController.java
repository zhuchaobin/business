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
import com.xai.tt.dc.client.service.PlgDrcPrcDcService;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.model.Company;
import com.xai.tt.dc.client.vo.inVo.PlgDrcPrcInVo;
import com.xai.tt.dc.client.vo.outVo.PlgDrcPrcOutVo;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ClassName:  PlgDrcPrcController  质押指导价
 * </pre>
 * <small> 2019-03-16 01:56:08 | xai-auto</small>
 */

@Controller
@RequestMapping("plgDrcPrc")
public class PlgDrcPrcController extends BaseController {
	@Autowired
	private PlgDrcPrcDcService plgDrcPrcDcService;
	@Autowired
	private CompanyDcService companyDcService;  
	  
    @RequestMapping(value = { "save" })
    @ResponseBody
    public Result<?>  save(PlgDrcPrcInVo inVo) {
   	logger.info("保存质押指导价请求报文inVo:={}", inVo);
    	Result<String> result = plgDrcPrcDcService.save(inVo);
    	logger.info("保存质押指导价返回结果：{}", JSON.toJSONString(result));
        return result;
    }  

   @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("plgDrcPrc/list");
      	CompanyQuery query = new CompanyQuery(); 

    	// 查询银行下拉菜单
        query.setUsrTp("06");
    	Result<PageInfo<Company>> result = companyDcService.queryPage(query);
    	logger.info("查询[银行]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[银行]公司信息异常");
//            throw new RuntimeException("查询[银行]公司信息异常");
        }
        mav.addObject("bnkModels", result.getData().getList());
        return mav;
    }
      
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(PlgDrcPrcInVo inVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    		
    	logger.info("分页查询质押指导价请求参数inVo=:{}，分页参数pageParam=：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
        Result<PageData<PlgDrcPrcOutVo>> rlt = plgDrcPrcDcService.queryPage(inVo, pageParam);
        logger.info("分页查询质押指导价返回结果:{}，", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());
    }
          
    @RequestMapping(value = { "getDetailById" })
    @ResponseBody
    public Result<?>   getDetailById(Integer id) {
    	logger.info("查询质押指导价详情，请求参数id=：{}", id);
    	Result<PlgDrcPrcOutVo> rlt = plgDrcPrcDcService.getDetailById(id);
    	logger.info("查询质押指导价详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "getDetailByCondition" })
    @ResponseBody
    public Result<?> getDetailByCondition(PlgDrcPrcInVo inVo) {
    	logger.info("查询质押指导价详情，请求参数inVo=：{}", inVo);
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	Result<PlgDrcPrcOutVo> rlt = plgDrcPrcDcService.getDetailByCondition(inVo);
    	logger.info("查询质押指导价详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
	
	@RequestMapping(value = { "delete" })
    @ResponseBody
    public Result<?> delete(String ids) {
    	logger.info("删除质押指导价，请求参数ids=：{}", ids);
    	Result<String> rlt = plgDrcPrcDcService.batchDelete(ids);
    	logger.info("删除质押指导价，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }	
}
