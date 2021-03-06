package com.xai.tt.business.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.AuthUser;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Pair;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.constant.CommonErrors;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.api.jpa.JpaMatchType;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.support.StringET;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.UserManager;
import com.xai.tt.business.biz.manager.UserRoleManager;
import com.xai.tt.business.client.entity.User;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.business.client.vo.UserVo;
import com.xai.tt.dc.client.model.Company;
import com.xai.tt.dc.client.query.CompanyQuery;
import com.xai.tt.dc.client.query.FactoryQuery;
import com.xai.tt.dc.client.service.CompanyDcService;
import com.xai.tt.dc.client.service.FactoryService;
import com.xai.tt.dc.client.service.UserManagementDcService;
import com.xai.tt.dc.client.vo.FactoryVo;
import com.xai.tt.dc.client.vo.UserManagementVo;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserRoleManager userRoleManager;   
    @Autowired
    private UserManagementDcService userManagementDcService;
    @Autowired
    private FactoryService factoryService;
    
	@Autowired
	private CompanyDcService companyDcService;   

    @RequestMapping("/list")
    public ModelAndView list(){
    	ModelAndView mav = new ModelAndView("user/user_list");
    	//根据用户角色决定下拉框内容 
    	boolean flag = false;
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	UserType userType = user.getUserType();
    	Integer company = user.getCompanyId();
    	if(user.hasRole("ROLE_ADMIN") || UserType.Group == userType) {
    		mav.addObject("userType", UserType.Group);
    		flag = true;
		}else{
			mav.addObject("userType", userType);
		}
    	
    	List<Pair> factoryList = new ArrayList<>();
    	PageParam pageParam = new PageParam();
    	pageParam.setPageSize(Integer.MAX_VALUE);
    	FactoryQuery factoryQuery = new FactoryQuery();
    	Result<PageData<FactoryVo>> factoryDatas = factoryService.queryFactoryList(factoryQuery, pageParam);
    	if(factoryDatas.success()){
    		List<FactoryVo> list = factoryDatas.getData().getRows();
    		for(FactoryVo fv : list){
    			if(UserType.Factory.equals(userType)){
    				if(company != null && company == fv.getId()){
    					factoryList.add(new Pair(fv.getId(), fv.getName()));
    				}
    			}else if(flag){
    				factoryList.add(new Pair(fv.getId(), fv.getName()));
    			}
    		}
    	}
    	//用户身份
    	
    	mav.addObject("factoryList", factoryList);
        return mav;
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id){
    	UserVo vo = userManager.get(id, UserVo.class);
        return Result.createSuccessResult(vo);
    }
    
    @LogAspect(type=LogType.Update_User, objectNames=User.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(User user){
    	logger.info("save user",user);
    	
    	LoginUser loginUser = (LoginUser)SecurityContext.getAuthUser();
    	if(loginUser.getUserType() == UserType.Company) {
    		user.setUserType(loginUser.getUserType());
    		user.setCompanyId(loginUser.getCompanyId());
    	} else if(loginUser.getUserType() != UserType.Group) {
    		return Result.createFailResult("没有权限！");
    	}
		
    	if(user.getAppEnabled() == null) {
    		user.setAppEnabled(false);
    	}
    	
    	userManager.save(user);
        return Result.createSuccessResult();
    }
    
    
    /***
     * 打开注册页面
     * @return
     */
	@RequestMapping(value = "/toRegisterPage", method=RequestMethod.GET)
    public ModelAndView toRegisterPage(){
    	ModelAndView mv = new ModelAndView("auth/ui/register");

    	CompanyQuery query = new CompanyQuery(); 
    	// 查询供应链公司下拉菜单
        query.setUsrTp("03");
        Result<PageInfo<Company>> result = companyDcService.queryPage(query);
    	result = companyDcService.queryPage(query);
    	logger.info("查询[供应链公司]公司信息返回结果：{}", JSON.toJSONString(result));
        if (result == null || result.getCode() != 0) {
        	logger.error("查询[供应链公司]公司信息异常");
        }
        mv.addObject("splchainCoModels", result.getData().getList());        
        return mv;
    }
	
    /***
     * 打开用户注册协议页面
     * @return
     */
	@RequestMapping(value = "/privacy", method=RequestMethod.GET)
    public ModelAndView privacy(){
    	ModelAndView mv = new ModelAndView("auth/ui/privacy");
        return mv;
    }
	
	@RequestMapping(value = "/register")
    @ResponseBody
    public Result<?> register(UserManagementVo inVo){
    	logger.info("register UserManagementInVo", JSON.toJSONString(inVo));
		
    	if(inVo.getAppEnabled() == null) {
    		inVo.setAppEnabled(false);
    	}        
        Result<Boolean> result = userManagementDcService.save(inVo);
    	logger.info("用户注册返回结果：{}", JSON.toJSONString(result));
        return result;
    }
    @LogAspect(type=LogType.Reset_Password, argNames="id")
    @RequestMapping("/resetPassword")
    @ResponseBody
    public Result<?> resetPassword(Integer id, String newPassword){
    	logger.info("resetPassword");
    	if(id == null) {
    		return Result.createFailResult(CommonErrors.PARAM_INVALID, "参数无效！");
    	}
    	userManager.resetPassword(id, StringET.isEmpty(newPassword) ? "123456" : newPassword);
    	
    	return Result.createSuccessResult();
    }
    
    @LogAspect(type=LogType.Update_Password)
    @RequestMapping("/modifyPassword")
    @ResponseBody
    public Result<?> modifyPassword(String oldPassword, String newPassword){
    	logger.info("modifyPassword");
    	AuthUser user = SecurityContext.getAuthUser();
    	userManager.updatePassword(user.getId(), oldPassword, newPassword);
    	return Result.createSuccessResult();
    }

    @LogAspect(type=LogType.Locked_User, argNames={"id", "locked"})
    @RequestMapping("/updateLocked")
    @ResponseBody
    public Result<?> updateLocked(Integer id, Boolean locked){
    	logger.info("updateLocked id:{}, locked:{}", id, locked);
    	userManager.updateLocked(id, locked);
        return Result.createSuccessResult();
    }

    @RequestMapping("/query")
    @ResponseBody
    public Result<?> query(HttpCriteria params) {
    	logger.info("query params:{}", params);
    	JpaCriteria criteria = params.toJpaCriteria(User.class);
    	//用户身份
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	//暂时放开控制，20190323
/*    	if(UserType.pltfrm != user.getUserType() && !user.hasRole("ROLE_ADMIN")) {
			criteria.add("userType", user.getUserType(), JpaMatchType.EQ);//除了管理员和集团用户，其他用户只能查本类型下的用户
			criteria.add("companyId", user.getCompanyId(), JpaMatchType.EQ);//只能查本公司的员工
		}*/
    	
    	PageData<UserVo> userList = userManager.findPage(criteria, UserVo.class);
        return Result.createSuccessResult(userList);
    }
    
    @RequestMapping("/role")
    public ModelAndView menu(Integer userId){
    	//用户身份
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
/*    	if(UserType.Group != user.getUserType() && !user.hasRole("ROLE_ADMIN")) {
    		return new ModelAndView("error/error");
    	}*/
    	
    	if((UserType.Group != user.getUserType() && !user.hasRole("ROLE_ADMIN")) && (UserType.pltfrm != user.getUserType())) {
    		return new ModelAndView("error/error");
    	}
    	
    	ModelAndView mv = new ModelAndView("user/user_role");
    	
    	List<Integer> idList = userRoleManager.listRoleIdsByUserId(userId);
        mv.addObject("ids", JSON.toJSONString(idList));
    	
        return mv;
    }
    
    @LogAspect(type=LogType.Distribute_Role, argNames={"userId", "ids"})
    @RequestMapping("/saveRole")
    @ResponseBody
    public Result<?> saveRole(Integer userId, String ids){
    	logger.info("saveRole userId:{}, ids:{}", userId, ids);
    	//用户身份
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	if(UserType.Group != user.getUserType() && !user.hasRole("ROLE_ADMIN") && UserType.pltfrm != user.getUserType()) {
    		return Result.createFailResult(CommonErrors.PARAM_INVALID, "没有权限！");
    	}
    	
    	if(userId == null || ids == null) {
    		return Result.createFailResult(CommonErrors.PARAM_INVALID, "参数无效！");
    	}
    	
    	Integer[] idArr = null;
    	if(StringET.isNotEmpty(ids)) {
        	String[] arr = ids.split(",");
        	idArr = new Integer[arr.length];
        	for (int i = 0; i < arr.length; i++) {
        		idArr[i] = Integer.parseInt(arr[i]);
    		}
    	}
    	
    	userRoleManager.updateUserRole(userId, idArr);

        return Result.createSuccessResult();
    }
}
