package com.xai.tt.business.biz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xai.tt.business.client.entity.RoleMenu;

/**
 * Created by ssr on 2017/3/10.
 */
public interface RoleMenuRepository extends  JpaRepository<RoleMenu, Integer> {
	
	@Modifying
	@Query(value = "delete from role_menu where menu_id = :menuId", nativeQuery = true)
	int deleteByMenuId(@Param("menuId") Integer menuId);
	
	@Modifying
	@Query(value = "delete from role_menu where role_id = :roleId", nativeQuery = true)
	int deleteByRoleId(@Param("roleId") Integer roleId);
	
	@Query(value = "select menu_id from role_menu where role_id = :roleId", nativeQuery = true)
	List<Integer> listMenuIdsByRoleId(@Param("roleId") Integer roleId);
	
}
