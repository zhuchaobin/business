package com.xai.tt.business.biz.manager;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.api.exception.TspException;
import com.tianan.common.core.manager.BaseManager;
import com.xai.tt.business.biz.repository.MenuRepository;
import com.xai.tt.business.client.entity.Menu;

@Service
public class MenuManager extends BaseManager<Menu, Integer> {
	@Autowired
	private RoleMenuManager roleMenuManager;
	
    @Autowired
    public void setMenuRepository(MenuRepository menuRepository){
        this.repository = menuRepository;
    }
    
	@Transactional
	public void save(Menu menu) {
		logger.info("save menu:{}", menu);
		
		if(menu.getId() == null) {
			if(menu.getSort() == null) {
				getRepository().insert4sort(
						menu.getPid(),
						menu.getName(),
						menu.getFolder(),
						menu.getUrl(),
						menu.getIcon(),
						menu.getPermit(),
						menu.getMemo(),
						menu.getLevel());
			} else {
				super.save(menu);
			}
		} else {
			Menu dbMenu = repository.findOne(menu.getId());
			if (dbMenu == null) {
				throw new TspException("菜单不存在或已删除！");
			}
			
			super.save(menu);
		}
	}
	
    @Transactional
    @Override
    public void delete(Integer id) {
        logger.info("delete id:{}", id);
        
        if(getRepository().countByPid(id) > 0) {
			throw new TspException("存在子菜单，不能删除！");
        }
        
        super.delete(id);
        
        roleMenuManager.deleteByMenuId(id);
    }
    
    public List<Menu> findByRoleIds(Integer... roleIds) {
    	return getRepository().findByRoleIds(roleIds);
    }

    private MenuRepository getRepository() {
    	return (MenuRepository)this.repository;
    }
}
