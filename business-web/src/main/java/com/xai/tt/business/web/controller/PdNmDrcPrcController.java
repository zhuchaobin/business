package com.xai.tt.business.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.api.jpa.JpaMatchType;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.biz.manager.VrtyManager;
import com.xai.tt.business.client.entity.Vrty;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.service.PdNmDrcPrcDcService;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.model.Company;
import com.xai.tt.dc.client.vo.inVo.PdNmDrcPrcInVo;
import com.xai.tt.dc.client.vo.outVo.PdNmDrcPrcOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ClassName:  PdNmDrcPrcController  品名指导价
 * </pre>
 * <small> 2019-03-21 15:23:49 | zhuchaobin</small>
 */

@Controller
@RequestMapping("pdNmDrcPrc")
public class PdNmDrcPrcController extends BaseController {
	@Autowired
	private PdNmDrcPrcDcService pdNmDrcPrcDcService;
	@Autowired
	private CompanyDcService companyDcService;  
    @Autowired
    private VrtyManager vrtyManager;
    @RequestMapping(value = { "save" })
    @ResponseBody
    public Result<?>  save(PdNmDrcPrcInVo inVo) {
    	logger.info("保存品名指导价请求报文inVo:={}", inVo);
		LoginUser user = (LoginUser)SecurityContext.getAuthUser();
		inVo.setUserType(user.getUserType().ordinal());
		inVo.setUsername(user.getUsername());
		inVo.setCompanyId(user.getCompanyId());
		inVo.setNickname(user.getNickname());
		inVo.setChineseName(user.getChineseName());
    	Result<String> result = pdNmDrcPrcDcService.save(inVo);
    	logger.info("保存品名指导价返回结果：{}", JSON.toJSONString(result));
        return result;
    }  

   @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("pdNmDrcPrc/list");
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
        LoginUser user = (LoginUser)SecurityContext.getAuthUser();
       	logger.info("user.getUserType() =" + user.getUserType());
    	mav.addObject("userType", user.getUserType());
    	
    	// 品名下拉菜单
    	HttpCriteria params = new HttpCriteria();
    	JpaCriteria criteria = params.toJpaCriteria(Vrty.class);
    	criteria.add("folder", 0, JpaMatchType.EQ);//只查品名
    	PageData<Vrty> vrtyList = vrtyManager.findPage(criteria, Vrty.class);
        mav.addObject("pmModels", vrtyList.getRows());
        logger.info("品名下拉菜单结果：" +  JSON.toJSONString(vrtyList.getRows()));
        
        return mav;
    }
      
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(PdNmDrcPrcInVo inVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    		
    	logger.info("分页查询品名指导价请求参数inVo=:{}，分页参数pageParam=：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
        Result<PageData<PdNmDrcPrcOutVo>> rlt = pdNmDrcPrcDcService.queryPage(inVo, pageParam);
        logger.info("分页查询品名指导价返回结果:{}，", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());
    }
          
    @RequestMapping(value = { "getDetailById" })
    @ResponseBody
    public Result<?>   getDetailById(Integer id) {
    	logger.info("查询品名指导价详情，请求参数id=：{}", id);
    	Result<PdNmDrcPrcOutVo> rlt = pdNmDrcPrcDcService.getDetailById(id);
    	logger.info("查询品名指导价详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "getDetailByCondition" })
    @ResponseBody
    public Result<?> getDetailByCondition(PdNmDrcPrcInVo inVo) {
    	logger.info("查询品名指导价详情，请求参数inVo=：{}", inVo);
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
    	Result<PdNmDrcPrcOutVo> rlt = pdNmDrcPrcDcService.getDetailByCondition(inVo);
    	logger.info("查询品名指导价详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
	
	@RequestMapping(value = { "delete" })
    @ResponseBody
    public Result<?> delete(String ids) {
    	logger.info("删除品名指导价，请求参数ids=：{}", ids);
    	Result<String> rlt = pdNmDrcPrcDcService.batchDelete(ids);
    	logger.info("删除品名指导价，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }	
}
