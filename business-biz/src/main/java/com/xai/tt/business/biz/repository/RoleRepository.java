package com.xai.tt.business.biz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tianan.common.core.repository.BaseRepository;
import com.xai.tt.business.client.entity.Role;

/**
 * Created by ssr on 2017/3/10.
 */

public interface RoleRepository extends BaseRepository<Role, Integer> {
	
	@Query(value = "select * from role where id in (select role_id from user_role where user_id = :userId)", nativeQuery = true)
	List<Role> findByUserId(@Param("userId") Integer userId);
	
	@Modifying
	@Query(value = "update role set locked = :locked where id = :id", nativeQuery=true)
	int updateLocked(@Param("id") Integer id, @Param("locked") Boolean locked);

	long countByCode(String name);
	
	long countByName(String name);
}
