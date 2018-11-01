package com.xai.tt.business.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.constant.CommonErrors;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.core.support.StringET;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.RoleManager;
import com.xai.tt.business.biz.manager.RoleMenuManager;
import com.xai.tt.business.client.entity.Role;

@RequestMapping("role")
@Controller
public class RoleController extends BaseController {
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private RoleMenuManager roleMenuManager;

    @RequestMapping("/list")
    public String list(){
        return "role/role_list";
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id){
        logger.info("get id:{}", id);
    	Role role = roleManager.get(id);
        return Result.createSuccessResult(role);
    }
    
    @LogAspect(type=LogType.Delete_Role, argNames={"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id){
        logger.info("delete id:{}", id);
        roleManager.delete(id);
        return Result.createSuccessResult();
    }
    
    @LogAspect(type=LogType.Update_Role, objectNames={Role.class})
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(Role role){
    	logger.info("save role:{}", role);
    	if(role.getId() == null) {
    		role.setLocked(false);
    		role.setSystem(false);
    		role.setAssignable(true);
    	}

    	roleManager.save(role);
        return Result.createSuccessResult();
    }

    @LogAspect(type=LogType.Locked_Role, argNames={"id"})
    @RequestMapping("/updateLocked")
    @ResponseBody
    public Result<?> updateLocked(Integer id, Boolean locked){
    	logger.info("updateLocked id:{}, locked:{}", id, locked);
    	roleManager.updateLocked(id, locked);
        return Result.createSuccessResult();
    }

    @RequestMapping("/query")
    @ResponseBody
    public Result<?> query(HttpCriteria params) {
    	logger.info("query params:{}", params);
    	PageData<Role> roleList = roleManager.findPage(params.toJpaCriteria(Role.class));
        return Result.createSuccessResult(roleList);
    }
    
    @RequestMapping("/query4alloc")
    @ResponseBody
    public Result<?> query4alloc() {
    	logger.info("query4alloc");
    	
    	JpaCriteria criteria = JpaCriteria.instanceAnd();
    	criteria.add("assignable", true);
    	criteria.add("locked", false);
    	List<Role> roleList = roleManager.findAll(criteria);
    	
        return Result.createSuccessResult(roleList);
    }
    
    @RequestMapping("/menu")
    public ModelAndView menu(Integer roleId){
    	ModelAndView mv = new ModelAndView("role/role_menu");
    	
    	List<Integer> idList = roleMenuManager.listMenuIdsByRoleId(roleId);
        	mv.addObject("ids", JSON.toJSONString(idList));
    	
        return mv;
    }
    
    @LogAspect(type=LogType.Distribute_Menu, argNames={"roleId", "ids"})
    @RequestMapping("/saveMenu")
    @ResponseBody
    public Result<?> saveMenu(Integer roleId, String ids){
    	logger.info("saveMenu roleId:{}, ids:{}", roleId, ids);
    	
    	if(roleId == null || ids == null) {
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
    	roleMenuManager.updateRoleMenu(roleId, idArr);
        return Result.createSuccessResult();
    }
}
