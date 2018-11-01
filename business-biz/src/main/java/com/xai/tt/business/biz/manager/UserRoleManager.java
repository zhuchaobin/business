package com.xai.tt.business.biz.manager;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.xai.tt.business.biz.repository.UserRoleRepository;
import com.xai.tt.business.client.entity.UserRole;

@Service
public class UserRoleManager {
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Transactional
    void deleteByUserId(@Param("userId") Integer userId) {
    	userRoleRepository.deleteByUserId(userId);
    }
	
    @Transactional
	void deleteByRoleId(@Param("roleId") Integer roleId) {
    	userRoleRepository.deleteByRoleId(roleId);
	}

	@Transactional
	public void updateUserRole(Integer userId, Integer... roleIds) {
		userRoleRepository.deleteByUserId(userId);

		if (roleIds != null && roleIds != null && roleIds.length > 0) {
			List<UserRole> list = new ArrayList<UserRole>();
			for (int i = 0; i < roleIds.length; i++) {
				list.add(new UserRole(userId, roleIds[i]));
			}

			userRoleRepository.save(list);
		}
	}
	
	public List<Integer> listRoleIdsByUserId(Integer userId) {
		return userRoleRepository.listRoleIdsByUserId(userId);
	}
}
