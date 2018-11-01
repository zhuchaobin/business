package com.xai.tt.business.biz.manager;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.xai.tt.business.biz.repository.RoleMenuRepository;
import com.xai.tt.business.client.entity.RoleMenu;

@Service
public class RoleMenuManager {
	@Autowired
	private RoleMenuRepository roleMenuRepository;

	@Transactional
	public void deleteByMenuId(@Param("menuId") Integer menuId) {
		roleMenuRepository.deleteByMenuId(menuId);
	}

	@Transactional
	public void deleteByRoleId(@Param("roleId") Integer roleId) {
		roleMenuRepository.deleteByRoleId(roleId);
	}

	@Transactional
	public void updateRoleMenu(Integer roleId, Integer... menuIds) {
		roleMenuRepository.deleteByRoleId(roleId);

		if (menuIds != null && menuIds != null && menuIds.length > 0) {
			List<RoleMenu> list = new ArrayList<RoleMenu>();
			for (int i = 0; i < menuIds.length; i++) {
				list.add(new RoleMenu(roleId, menuIds[i]));
			}

			roleMenuRepository.save(list);
		}
	}
	
	public List<Integer> listMenuIdsByRoleId(Integer roleId) {
		return roleMenuRepository.listMenuIdsByRoleId(roleId);
	}
}
