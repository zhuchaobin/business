package com.xai.tt.business.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianan.common.api.bean.Result;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.MenuManager;
import com.xai.tt.business.client.entity.Menu;

@RequestMapping("menu")
@Controller
public class MenuController extends BaseController {

    @Autowired
    private MenuManager menuManager;

    @RequestMapping("/list")
    public String list(){
        return "menu/menu_list";
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id){
    	Menu menu = menuManager.get(id);
        return Result.createSuccessResult(menu);
    }
    
    @LogAspect(type=LogType.Delete_Menu, argNames={"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id){
    	menuManager.delete(id);
        return Result.createSuccessResult();
    }
    
    @LogAspect(type=LogType.Update_Menu, objectNames=Menu.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(Menu menu){
    	menuManager.save(menu);
        return Result.createSuccessResult();
    }   

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Menu> queryAll(HttpCriteria params) {
    	params.setSortName("level,sort");
    	params.setSortOrder("asc,asc");
    	
    	List<Menu> menuList = new ArrayList<>();
    	List<Menu> list = menuManager.findAll(params.toJpaCriteria(Menu.class));
    	for (Menu menu : list) {
			if(StringUtils.isNotBlank(menu.getMemo())) {
				menu.setName(menu.getName() + " 【" + menu.getMemo() + "】");
			}
		}
		Menu root = new Menu();
		root.setId(0);
		root.setName("菜单管理");
		root.setLevel(0);
		root.setIcon("fa-tasks");
		root.setFolder(true);
		
    	menuList.add(root);
    	menuList.addAll(list);
    	
        return menuList;
    }
}
