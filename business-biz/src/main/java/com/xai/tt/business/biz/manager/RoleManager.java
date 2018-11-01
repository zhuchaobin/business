package com.xai.tt.business.biz.manager;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.api.constant.CommonErrors;
import com.tianan.common.api.exception.TspException;
import com.tianan.common.core.manager.BaseManager;
import com.xai.tt.business.biz.repository.RoleRepository;
import com.xai.tt.business.client.entity.Role;

@Service
public class RoleManager extends BaseManager<Role, Integer> {
	@Autowired
	private RoleMenuManager roleMenuManager;
	@Autowired
	private UserRoleManager userRoleManager;
	
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.repository = roleRepository;
    }
    
    public List<Role> findByUserId(Integer userId) {
    	return getRepository().findByUserId(userId);
    }
    
	@Transactional
	public void save(Role role) {
		logger.info("save role:{}", role);
		
		Role dbRole = null;
		if(role.getId() != null) {
			dbRole = repository.findOne(role.getId());
			if (dbRole == null) {
				throw new TspException(CommonErrors.DATA_NOT_FOUND, "角色不存存或已删除！");
			}
			if (dbRole.getSystem()) {
				throw new TspException("系统角色，不能修改！");
			}
		}
		
		//角色代码唯一校验
		if (codeExist(role, dbRole)) {
			throw new TspException("角色代码已存在！");
		}
		//角色名称唯一校验
		if (nameExist(role, dbRole)) {
			throw new TspException("角色名称已存在！");
		}

        super.save(role);
	}
	
    
    @Transactional
    @Override
    public void delete(Integer id) {
        logger.info("delete id:{}", id);
        repository.delete(id);
        
        roleMenuManager.deleteByRoleId(id);
        
        userRoleManager.deleteByRoleId(id);
    }
    
    @Transactional
    public void updateLocked(Integer id, Boolean locked) {
    	Role dbRole = repository.findOne(id);
		if (dbRole == null) {
			throw new TspException(CommonErrors.DATA_NOT_FOUND, "角色不存存或已删除！");
		}
		if (dbRole.getSystem()) {
			throw new TspException("系统角色，不能锁定/解锁！");
		}
    	getRepository().updateLocked(id, locked);
    }

    private boolean codeExist(Role role, Role dbRole) {
    	if(role.getId() == null || !Objects.equals(role.getCode(), dbRole.getCode())) {
    		return getRepository().countByCode(role.getCode()) > 0;
    	}
    	
    	return false;
    }

    private boolean nameExist(Role role, Role dbRole) {
    	if(role.getId() == null || !Objects.equals(role.getName(), dbRole.getName())) {
    		return getRepository().countByName(role.getName()) > 0;
    	}
    	
    	return false;
    }
    
    private RoleRepository getRepository() {
    	return (RoleRepository)this.repository;
    }
}
