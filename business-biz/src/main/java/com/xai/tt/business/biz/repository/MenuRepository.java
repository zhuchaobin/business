package com.xai.tt.business.biz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tianan.common.core.repository.BaseRepository;
import com.xai.tt.business.client.entity.Menu;

/**
 * Created by ssr on 2017/3/10.
 */

public interface MenuRepository extends BaseRepository<Menu, Integer> {
	
	@Query(value = "select * from menu where id in (select menu_id from role_menu where role_id in :roleIds) order by level, sort", nativeQuery = true)
	List<Menu> findByRoleIds(@Param("roleIds") Integer... roleIds);
	
	@Modifying
	@Query(value = "insert into menu(version, pid, name, folder, url, icon, sort, permit, memo, level) "
			+ "values(0, :pid, :name, :folder, :url, :icon, (@@IDENTITY + 1) * 10, "
			+ ":permit, :memo, :level)", nativeQuery = true)
	int insert4sort(
			@Param("pid") Integer pid,
			@Param("name") String name,
			@Param("folder") Boolean folder,
			@Param("url") String url,
			@Param("icon") String icon,
			@Param("permit") String permit,
			@Param("memo") String memo,
			@Param("level") Integer level);
	
	
	long countByPid(Integer pid);
}
