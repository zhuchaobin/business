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
import com.xai.tt.business.biz.manager.VrtyManager;
import com.xai.tt.business.client.entity.Vrty;

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
		root.setName("品种管理");
		root.setLevel(0);
		root.setIcon("fa-tasks");
		root.setFolder(true);
		
    	menuList.add(root);
    	menuList.addAll(list);
    	
        return menuList;
    }
}
