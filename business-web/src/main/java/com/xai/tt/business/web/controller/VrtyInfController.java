package com.xai.tt.business.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.api.jpa.JpaMatchType;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.VrtyManager;
import com.xai.tt.business.client.entity.User;
import com.xai.tt.business.client.entity.Vrty;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;
import com.xai.tt.business.client.vo.UserVo;
/*品种品名管理*/
@RequestMapping("vrty")
@Controller
public class VrtyInfController extends BaseController {

    @Autowired
    private VrtyManager vrtyManager;

    @RequestMapping("/list")
    public String list(){
    	logger.info("vrty->list");
        return "vrty/vrty_list";
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id){
    	Vrty vrty = vrtyManager.get(id);
        return Result.createSuccessResult(vrty);
    }
    
    @RequestMapping("/queryPage")
    @ResponseBody
    public Result<?> query(HttpCriteria params) {
    	logger.info("query params:{}", params);
    	JpaCriteria criteria = params.toJpaCriteria(Vrty.class);
/*    	//用户身份
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	if(UserType.pltfrm != user.getUserType() && !user.hasRole("ROLE_ADMIN")) {
			criteria.add("userType", user.getUserType(), JpaMatchType.EQ);//除了管理员和集团用户，其他用户只能查本类型下的用户
			criteria.add("companyId", user.getCompanyId(), JpaMatchType.EQ);//只能查本公司的员工
		}*/
    	
    	PageData<Vrty> vrtyList = vrtyManager.findPage(criteria, Vrty.class);
        return Result.createSuccessResult(vrtyList);
    }
        
    @LogAspect(type=LogType.Delete_vrty, argNames={"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id){
    	vrtyManager.delete(id);
        return Result.createSuccessResult();
    }
    
    @LogAspect(type=LogType.Update_vrty, objectNames=Vrty.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(Vrty vrty){
    	if("1".equals(vrty.getFolder()))
    		vrty.setIcon("fa-cubes");
    	else if("0".equals(vrty.getFolder()))
    		vrty.setIcon("fa-cube");
    	vrtyManager.save(vrty);
        return Result.createSuccessResult();
    }   

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Vrty> queryAll(HttpCriteria params) {
    	params.setSortName("level,sort");
    	params.setSortOrder("asc,asc");
    	
    	List<Vrty> menuList = new ArrayList<>();
    	List<Vrty> list = vrtyManager.findAll(params.toJpaCriteria(Vrty.class));
    	for (Vrty menu : list) {
			if(StringUtils.isNotBlank(menu.getMemo())) {
				menu.setName(menu.getName() + " 【" + menu.getMemo() + "】");
			}
		}
    	Vrty root = new Vrty();
		root.setId(0);
		root.setName("所有");
		root.setLevel(0);
		root.setIcon("fa-cubes");
		root.setFolder(true);
		
    	menuList.add(root);
    	menuList.addAll(list);
    	
        return menuList;
    }
}
