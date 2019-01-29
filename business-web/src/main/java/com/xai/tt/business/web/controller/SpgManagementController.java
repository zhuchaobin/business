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
import com.xai.tt.dc.client.model.T7SpgDetail;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.query.SubmitSpgQuery;
import com.xai.tt.dc.client.query.UserInfoQuery;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.service.SpgManagementDcService;
import com.xai.tt.dc.client.vo.inVo.SpgManagementInVo;
import com.xai.tt.dc.client.vo.outVo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/*
 * 
 * @ClassName:  SpgManagementController
 * @Description:发货信息管理
 * @author: yuzhaoyang
 * @date:   2018年12月23日 上午11:00:23
 * 
 */
@Controller
@RequestMapping("spgManagement")
public class SpgManagementController extends BaseController {
//	@Autowired
//	private ArManagementDcService arManagementDcService;
	@Autowired
	private CompanyDcService companyDcService;


	@Autowired
	private SpgManagementDcService spgManagementDcService;


    @RequestMapping(value = { "save" })
    @ResponseBody
	public Result<?>  save(String inVo, String detail, String fileUrl) {
    	logger.info("保存发货信息请求报文：inVo={}, detail={},fileUrl={}", JSON.toJSONString(inVo), JSON.toJSONString(detail), JSON.toJSONString(fileUrl));

		List<T7SpgDetail> t7SpgDetailList = JSON.parseArray(detail, T7SpgDetail.class);
		SpgManagementInVo spgManagementInVo = JSON.parseObject(inVo,SpgManagementInVo.class);

		logger.info("t7SpgDetailList{}", JSON.toJSON(t7SpgDetailList));

		logger.info("spgManagementInVo{}", JSON.toJSON(spgManagementInVo));

		if (StringUtils.isNotEmpty(fileUrl)) {
			spgManagementInVo.setFileNames(fileUrl);
		}

		LoginUser user = (LoginUser)SecurityContext.getAuthUser();




		// 	logger.info("长约附件信息长度：{}", spgManagementInVo.getList().size());

		spgManagementInVo.setUserType(user.getUserType().ordinal());
		spgManagementInVo.setUsername(user.getUsername());
		spgManagementInVo.setCompanyId(user.getCompanyId());
		spgManagementInVo.setNickname(user.getNickname());
		spgManagementInVo.setChineseName(user.getChineseName());


		spgManagementInVo.setT7SpgDetailList(t7SpgDetailList);
       	// 
    	Result<Boolean> result = spgManagementDcService.save(spgManagementInVo);
    	logger.info("保存发货信息返回结果：{}", JSON.toJSONString(result));
        return result;
    }   
    
    @RequestMapping(value = { "queryCompanyModels" })
    public ModelAndView queryCompanyModels(CompanyQuery query) {
    	logger.info("查询公司信息请求报文：CompanyQuery={}", JSON.toJSONString(query));
    	ModelAndView mv = new ModelAndView("spgManagement/add");
    	Result<PageInfo<Company>> result = companyDcService.queryPage(query);
    	logger.info("查询公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
            throw new RuntimeException("公司信息异常");
        }
        mv.addObject("CompanyModels", result.getData().getList());

        return mv;
    }  
    
    @RequestMapping(value = { "submitSpg" })
    @ResponseBody
    public Result<?>  submitSpg(SubmitSpgQuery query, String fileUrl2) {
    	logger.info("提交发货请求报文：{}, fileUrl2={}", JSON.toJSONString(query), JSON.toJSONString(fileUrl2));
        if (StringUtils.isNotEmpty(fileUrl2)) {
        	query.setFileNames(fileUrl2);
        }
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	query.setUsername(user.getUsername());
    	query.setCompanyId(user.getCompanyId());
    	Result<Boolean> result = spgManagementDcService.submitSpg(query);
    	logger.info("提交发货返回结果：{}", JSON.toJSONString(result));
        return result;
    }   
    
    @RequestMapping(value = { "deleteSpg" })
    @ResponseBody
    public Result<?>   deleteSpg(String id) {
    	logger.info("删除发货，请求参数id=：{}", id);
    	Result<Boolean> rlt = spgManagementDcService.deleteSpg(id);
    	logger.info("删除发货，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    

    
    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?>  queryPage(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "queryPage_adted" })
    @ResponseBody
    public Result<?>  queryPage_adted(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(5);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "queryPage_add" })  @ResponseBody
    public Result<?>  queryPage_add(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(4);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
	/**
	 * 描述：查询待处理任务数
	 * 
	 * @author zhang 2019-1-29
	 */
    @RequestMapping(value = { "getAdtTaskNum" })
    @ResponseBody
	public Result<?> getAdtTaskNum() {
    	UserInfoQuery query = new UserInfoQuery();
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	query.setUserType(user.getUserType().ordinal());
    	query.setUsername(user.getUsername());
    	query.setCompanyId(user.getCompanyId());
    	query.setNickname(user.getNickname());
    	query.setChineseName(user.getChineseName());
    	Result<List<Integer>> rlt = spgManagementDcService.getAdtTaskNum(query);
    	return Result.createSuccessResult(rlt.getData());
    }
    
    @RequestMapping(value = { "queryPage_ing" })
    @ResponseBody
    public Result<?>  queryPage_ing(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(1);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "queryPage_adt" })
    @ResponseBody
    public Result<?>  queryPage_adt(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(2);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "queryPage_fnsh" })
    @ResponseBody
    public Result<?>  queryPage_fnsh(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(3);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    
    @RequestMapping(value = { "queryPage_by_order" })
    @ResponseBody
    public Result<?>  queryPage_by_order(SpgManagementInVo spgManagementInVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	spgManagementInVo.setQueryType(6);
    	// 因前后端名字不一样，转义排序参数
    	String sortName = spgManagementInVo.getSortName();
    	if(StringUtils.isNotBlank(sortName)) {
    		sortName = sortName.replace("fncEntpName", "fncEntp");
    		sortName = sortName.replace("ustrmSplrName", "ustrmSplr");
    		sortName = sortName.replace("stgcoName", "stgco");
    		sortName = sortName.replace("bnkName", "bnk");
    		sortName = sortName.replace("lgstcCoName", "lgstcCo");
    		sortName = sortName.replace("insCoName", "insCo");
    		sortName = sortName.replace("splchainCoName", "splchainCo");
    		sortName = sortName.replace("aplyPcstpCd", "aplypcstpcd");
    		spgManagementInVo.setSortName(sortName);
    	}
    		
    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(spgManagementInVo),JSON.toJSONString(pageParam));
        Result<PageData<QuerySpgInfDetailOutVo>> result = spgManagementDcService.queryPage(spgManagementInVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }
    

        
    @RequestMapping(value = { "getDetail" })
    @ResponseBody
	public Result<?>   getDetail(String id, String aplyPcstpCd, String type) {

		logger.info("查询长约详情，请求参数id=：{}", id);
		logger.info("查询长约详情，请求参数aplyPcstpCd=：{}", aplyPcstpCd);
		logger.info("查询长约详情，请求参数type=：{}", type);
		SpgManagementInVo inVo = new SpgManagementInVo();
		inVo.setId(Long.parseLong(id));
		inVo.setAplyPcstpCd(aplyPcstpCd);
		inVo.setFlag(type);


		inVo.setId(Long.parseLong(id));
		LoginUser user = (LoginUser)SecurityContext.getAuthUser();
		inVo.setUserType(user.getUserType().ordinal());
		inVo.setUsername(user.getUsername());
		inVo.setCompanyId(user.getCompanyId());
		inVo.setNickname(user.getNickname());
		inVo.setChineseName(user.getChineseName());
		Result<QuerySpgInfDetailOutVo> rlt = spgManagementDcService.querySpgDetail(inVo);
		logger.info("查询发货详情，返回结果rlt：{}", JSON.toJSONString(rlt));
		return Result.createSuccessResult(rlt.getData());

	}

	//test git push
    // 撤销发货
    @RequestMapping(value = { "unDoSpg" })
    @ResponseBody
    public Result<?>   unDoSpg(String id) {
    	logger.info("撤销发货，请求参数id=：{}", id);
    	SpgManagementInVo spgManagementInVo = new SpgManagementInVo();
    	spgManagementInVo.setId(Long.parseLong(id));
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	spgManagementInVo.setUserType(user.getUserType().ordinal());
    	spgManagementInVo.setUsername(user.getUsername());
    	spgManagementInVo.setCompanyId(user.getCompanyId());
    	spgManagementInVo.setNickname(user.getNickname());
    	spgManagementInVo.setChineseName(user.getChineseName());
    	Result<Boolean> rlt = spgManagementDcService.unDoSpg(spgManagementInVo);
    	logger.info("撤销发货，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    
    @RequestMapping(value = { "getSpgSubmmitDetail" })
    @ResponseBody
    public Result<?>   getSpgSubmmitDetail(String id, String arId, String aplyPcstpCd) {
    	logger.info("查询发货提交详情，请求参数id=:{} arId=：{} aplyPcstpCd=：{}", id, arId, aplyPcstpCd);
    	Result<QuerySpgSubmmitDetailOutVo> rlt = spgManagementDcService.getSpgSubmmitDetail(id, arId, aplyPcstpCd);
    	logger.info("查询发货提交详情，返回结果rlt：{}", JSON.toJSONString(rlt));
        return Result.createSuccessResult(rlt.getData());        
    }
    
    @RequestMapping(value = { "list_ing" })
    public ModelAndView list_ing() {
    	logger.info("查询执行中的发货开始");
    	ModelAndView mav = lists(1);
    	return mav;
    }
    
    @RequestMapping(value = { "list_adted" })
    public ModelAndView list_adted() {
    	ModelAndView mav = lists(5);
    	return mav;
    }
    
    @RequestMapping(value = { "list_adt" })
    public ModelAndView list_adt() {
    	ModelAndView mav = lists(2);
    	return mav;
    }
    
    @RequestMapping(value = { "list_fnsh" })
    public ModelAndView list_fnsh() {
    	ModelAndView mav = lists(3);
    	return mav;
    }
    
    @RequestMapping(value = { "list_by_order" })
    public ModelAndView list_by_ar(@RequestParam(name="ordrId") String ordrId) {
 //   public ModelAndView list_by_ar() {
    	logger.info("list_by_order 请求参数ordrId=" + ordrId);
    	ModelAndView mav = new ModelAndView("spgManagement/list_by_order");
    	mav.addObject("ordrId", ordrId);
    	return mav;
    }
    
    @RequestMapping(value = { "add" })
    public ModelAndView add() {
    	ModelAndView mav = new ModelAndView("spgManagement/add");
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
    	mav.addObject("userType", "Group");
    	return mav;
    }
    	
    @RequestMapping(value = { "list" })
    public ModelAndView list() {
    	ModelAndView mav = new ModelAndView("spgManagement/list");
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
    	mav.addObject("userType", "Group");
        return mav;
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
        	if(null != fileExistMap.get(item.getOriginalFilename()) && (1== fileExistMap.get(item.getOriginalFilename()))){
				return;
			}
        	else{
				fileExistMap.put(item.getOriginalFilename(), 1);
			}

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
    	if(1 == flag){
			mav = new ModelAndView("spgManagement/list_ing");
		} else if(2 == flag){
			mav = new ModelAndView("spgManagement/list_adt");
		} else if(3 == flag){
			mav = new ModelAndView("spgManagement/list_fnsh");
		} else if(5 == flag){
			mav = new ModelAndView("spgManagement/list_adted");
		}

    	
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
    	mav.addObject("userType", "Group");
        return mav;
    } 
       
}
