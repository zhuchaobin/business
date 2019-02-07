package com.xai.tt.business.web.controller;

import com.alibaba.fastjson.JSON;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.service.KcDcService;
import com.xai.tt.dc.client.vo.inVo.KcManagementInVo;
import com.xai.tt.dc.client.vo.outVo.QueryKcDetailOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*
 *
 * @ClassName:  SpgManagementController
 * @Description:库存信息管理
 * @author: yuzhaoyang
 * @date:   2019年01月28日 上午11:00:23
 *
 */
@Controller
@RequestMapping("kcManagement")
public class KcController extends BaseController {


	@Autowired
	private KcDcService kcDcService;


	@RequestMapping(value = { "list_onRoad" })
	public ModelAndView list_onRoad() {
		ModelAndView mav = new ModelAndView("kcManagement/list_onRoad");

		return mav;
	}


	@RequestMapping(value = { "list_in" })
	public ModelAndView list_in() {
		ModelAndView mav = new ModelAndView("kcManagement/list_in");

		return mav;
	}


	@RequestMapping(value = { "list_out" })
	public ModelAndView list_out() {
		ModelAndView mav = new ModelAndView("kcManagement/list_out");

		return mav;
	}

	@RequestMapping(value = { "queryPage_onRoad" })
	@ResponseBody
	public Result<?>  queryPage_onRoad(KcManagementInVo inVo, PageParam pageParam) {
		LoginUser user = (LoginUser)SecurityContext.getAuthUser();
		inVo.setUserType(user.getUserType().ordinal());
		inVo.setUsername(user.getUsername());
		inVo.setCompanyId(user.getCompanyId());
		inVo.setNickname(user.getNickname());
		inVo.setChineseName(user.getChineseName());
		inVo.setIvntSt("62");
		// 因前后端名字不一样，转义排序参数


		logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
		Result<PageData<QueryKcDetailOutVo>> result = kcDcService.queryPage(inVo, pageParam);
		logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
		return Result.createSuccessResult(result.getData());
	}



	@RequestMapping(value = { "queryPage_in" })
	@ResponseBody
	public Result<?>  queryPage_in(KcManagementInVo inVo, PageParam pageParam) {
		LoginUser user = (LoginUser)SecurityContext.getAuthUser();
		inVo.setUserType(user.getUserType().ordinal());
		inVo.setUsername(user.getUsername());
		inVo.setCompanyId(user.getCompanyId());
		inVo.setNickname(user.getNickname());
		inVo.setChineseName(user.getChineseName());
		inVo.setIvntSt("63");
		// 因前后端名字不一样，转义排序参数


		logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
		Result<PageData<QueryKcDetailOutVo>> result = kcDcService.queryPage(inVo, pageParam);
		logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
		return Result.createSuccessResult(result.getData());
	}


    @RequestMapping(value = { "queryPage_out" })
    @ResponseBody
    public Result<?>  queryPage_out(KcManagementInVo inVo, PageParam pageParam) {
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	inVo.setUserType(user.getUserType().ordinal());
    	inVo.setUsername(user.getUsername());
    	inVo.setCompanyId(user.getCompanyId());
    	inVo.setNickname(user.getNickname());
    	inVo.setChineseName(user.getChineseName());
		inVo.setIvntSt("65");
    	// 因前后端名字不一样，转义排序参数


    	logger.info("发货信息查询请求参数:{}，分页参数：{}", JSON.toJSONString(inVo),JSON.toJSONString(pageParam));
        Result<PageData<QueryKcDetailOutVo>> result = kcDcService.queryPage(inVo, pageParam);
        logger.info("发货信息查询返回结果:{}，", JSON.toJSONString(result.getData()));
        return Result.createSuccessResult(result.getData());
    }







}