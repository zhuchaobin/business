package com.xai.tt.business.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianan.common.api.bean.AuthUser;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.constant.CommonErrors;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.support.SecurityET;
import com.tianan.common.core.support.StringET;
import com.tianan.common.mvc.filter.SecurityFilter;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.MenuManager;
import com.xai.tt.business.biz.manager.RoleManager;
import com.xai.tt.business.biz.manager.UserManager;
import com.xai.tt.business.client.entity.Menu;
import com.xai.tt.business.client.entity.Role;
import com.xai.tt.business.client.entity.User;
import com.xai.tt.business.client.vo.LoginUser;

@Controller
public class AuthController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserManager userManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private MenuManager menuManager;
	
    /***
     * 登录页面
     * @return
     */
	@RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login(){
        return "auth/ui/index";
    }
	
	/***
	 * 登录操作
	 * @param uname		用户名
	 * @param pword		密码
	 * @param remenber	记住密码
	 * @param session	会话对象
	 * @return 登录结果
	 */
	@LogAspect(type=LogType.Login)
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	@ResponseBody
    public Result<?> authLogin(String uname, String pword, Integer remenber, HttpSession session){
		logger.info("login uname:{}", uname);
		
		if(StringET.isEmpty(uname) || StringET.isEmpty(pword)) {
			return Result.createFailResult(CommonErrors.PARAM_INVALID, "参数无效！");
		}
		
		//获取用户信息
		User user = userManager.findByUsername(uname);
		if(user == null) {
			return Result.createFailResult("用户名或密码错误！");
		}
		if(!SecurityET.encryptPassword(pword).equals(user.getPassword())) {
			return Result.createFailResult("用户名或密码错误！");
		}
		if(user.getLocked()) {
			return Result.createFailResult("帐号已被锁定！");
		}
		
		//获取用户角色和菜单
		LoginUser loginUser = new LoginUser(user);
		List<Role> roles = roleManager.findByUserId(loginUser.getId());
		loginUser.setRoles(roles);
		
		List<Integer> idList = new ArrayList<>();
		for (Role role : roles) {
			if(!role.getLocked()) {
				idList.add(role.getId());
			}
		}
		
		if(roles.size() > 0) {
			Integer[] ids = new Integer[roles.size()];
			for (int i = 0; i < roles.size(); i++) {
				ids[i] = roles.get(i).getId();
			}
			
			List<Menu> menus = menuManager.findByRoleIds(ids);
			
			loginUser.setMenus(createMenuTree(menus));
			loginUser.setPermits(createPermit(menus));
		}
		logger.info("{} roles:{}", uname, roles);
		
		//用户登录信息存放到session
		session.setAttribute(SecurityFilter.SESSION_USER_KEY, loginUser);

		return Result.createSuccessResult();
    }

	/***
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping(value = "/password")
    public String password(){
        return "auth/password";
    }
	
	/***
	 * 修改密码操作
	 * @param oldPassword	旧密码
	 * @param newPassword	新密码
	 * @return
	 */
	@LogAspect(type=LogType.Update_Password)
	@RequestMapping(value = "/modifyPassword")
	@ResponseBody
    public Result<?> modifyPassword(String oldPassword, String newPassword){
		logger.info("modifyPassword");
		if(StringET.isEmpty(oldPassword) || StringET.isEmpty(newPassword)) {
			return Result.createFailResult(CommonErrors.PARAM_INVALID, "参数无效！");
		}
		
		AuthUser loginUser = SecurityContext.getAuthUser();
		userManager.updatePassword(loginUser.getId(), oldPassword, newPassword);
		logger.info("modifyPassword username:{}", loginUser.getUsername());
		
		return Result.createSuccessResult();
	}
	
	/***
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout")
	@ResponseBody
    public Result<?> logout(HttpSession session){
		logger.info("logout");
		session.removeAttribute("loginUser");
		return Result.createSuccessResult();
    }
	
	/***
	 * 构建菜单数结构
	 * @param menuList 无结构菜单列表
	 * @return
	 */
	private List<Menu> createMenuTree(List<Menu> menuList) {
    	Map<Integer, Menu> menuMap = new HashMap<Integer,Menu>();
    	
    	List<Menu> treeMenus = new ArrayList<Menu>();
    	for (Menu menu : menuList) {
			Menu parent = menuMap.get(menu.getPid());
			
			if(parent == null) {
				treeMenus.add(menu);
			} else {
				if(parent.getChildrens() == null) {
					parent.setChildrens(new ArrayList<Menu>());
				}
				parent.getChildrens().add(menu);
			}
			menuMap.put(menu.getId(), menu);
		}
    	
    	return treeMenus;
	}
	
	/***
	 * 根据菜单项构造权限列表
	 * @param menus 菜单列表
	 * @return
	 */
	private List<String> createPermit(List<Menu> menus) {
    	List<String> permits = new ArrayList<String>();
    	
    	for (Menu menu : menus) {
    		if(StringET.isBlank(menu.getPermit())) {
    			continue;
    		}
    		
    		String[] permitArr = menu.getPermit().split(";|\n");
    		for (String permit : permitArr) {
				if(StringET.isNotBlank(permit)) {
					permits.add(permit);
				}
			}
    		
		}
    	
    	return permits;
	}
	
	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String forget() {
//		return "auth/forget";
		return "auth/ui/password_getback";
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "auth/ui/register";
	}

	@LogAspect(type=LogType.Forget_Password)
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Result<?> forgetPassword(String username, String mobile) {
		logger.info("forgetPassword");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(mobile)) {
			return Result.createFailResult(CommonErrors.PARAM_INVALID, "参数无效！");
		}
		JpaCriteria usernameMobileCriteria = JpaCriteria.instanceOr();
		usernameMobileCriteria.add("username", username);
		usernameMobileCriteria.add("mobile", mobile);

		User user = userManager.findOne(usernameMobileCriteria);
		if (user == null) {
			return Result.createFailResult("用户名不存在！");
		}
		Integer newPassword = (int) ((Math.random() * 9 + 1) * 100000);
		userManager.resetPassword(user.getId(), newPassword.toString());
		return Result.createSuccessResult(newPassword);
	}
}
