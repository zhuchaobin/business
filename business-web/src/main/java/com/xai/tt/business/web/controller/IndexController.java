package com.xai.tt.business.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.dc.client.query.UserInfoQuery;
import com.xai.tt.dc.client.service.SpgManagementDcService;

import com.tianan.common.api.bean.Result;
import com.tianan.common.api.support.SecurityContext;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private SpgManagementDcService spgManagementDcService;
	
	@RequestMapping(value= {"/", "index"})
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		return mv;
	}
	    	
	@RequestMapping(value= {"checkstartup.html"})
	@ResponseBody
	public String checkstartup() {
		return "success";
	}
	
	@RequestMapping(value= {"/home"})
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		
		return mv;
	}	
}
