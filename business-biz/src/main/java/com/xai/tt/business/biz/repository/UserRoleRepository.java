package com.xai.tt.business.biz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xai.tt.business.client.entity.UserRole;

/**
 * Created by ssr on 2017/3/10.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
	@Modifying
	@Query(value = "delete from user_role where role_id = :roleId", nativeQuery = true)
	int deleteByRoleId(@Param("roleId") Integer roleId);

	@Modifying
	@Query(value = "delete from user_role where user_id = :userId and role_id not in (select id from role where assignable = 0)", nativeQuery = true)
	int deleteByUserId(@Param("userId") Integer userId);
	
	@Query(value = "select role_id from user_role where user_id = :userId", nativeQuery = true)
	List<Integer> listRoleIdsByUserId(@Param("userId") Integer userId);
}
