package com.xai.tt.business.biz.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tianan.common.core.repository.BaseRepository;
import com.xai.tt.business.client.entity.User;

/**
 * Created by ssr on 2017/3/10.
 */
public interface UserRepository extends BaseRepository<User, Integer> {
	
	User findByUsername(String username);
	
	User findByMobile(String mobile);

	User findByEmail(String email);
	
	long countByUsername(String username);
	
	long countByMobile(String mobile);

	long countByEmail(String email);
	
	@Modifying
	@Query(value = "update user set locked = :locked where id = :id", nativeQuery=true)
	int updateLocked(@Param("id") Integer id, @Param("locked") Boolean locked);
	
	@Modifying
	@Query(value = "update user set password = :password where id = :id", nativeQuery=true)
	int updatePassword(@Param("id") Integer id, @Param("password") String password);
	
	@Query(value = "select id, locked, createTime from user where id in :ids", nativeQuery=true)
	List<User> findByIds(@Param("ids") Integer[] ids);
	
	
	@Modifying
	@Query(value = "update user set ext = :ext where id = :id", nativeQuery=true)
	int updateExt(@Param("id") Integer id, @Param("ext") String ext);
}
