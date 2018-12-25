package com.xai.tt.business.biz.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.api.constant.CommonErrors;
import com.tianan.common.api.exception.TspException;
import com.tianan.common.api.support.SecurityContext;
import com.tianan.common.core.manager.BaseManager;
import com.tianan.common.core.support.SecurityET;
import com.tianan.common.core.support.StringET;
import com.xai.tt.business.biz.repository.UserRepository;
import com.xai.tt.business.biz.repository.UserRoleRepository;
import com.xai.tt.business.client.entity.User;
import com.xai.tt.business.client.entity.UserRole;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;

@Service
public class UserManager extends BaseManager<User, Integer> {
	private static final Map <UserType, Integer> userRoleMap = new HashMap<>();
	static {
		// 平台角色ID
		userRoleMap.put(UserType.pltfrm, 1);
		// 上游供应商角色ID
		userRoleMap.put(UserType.ustrmSplr, 2);
		// 供应链公司角色ID
		userRoleMap.put(UserType.splchainCo, 3);
		// 融资企业角色ID
		userRoleMap.put(UserType.fncEntp, 4);
		// 保险公司角色ID
		userRoleMap.put(UserType.insCo, 5);
		// 银行角色ID
		userRoleMap.put(UserType.bnk, 6);
		// 物流公司角色ID
		userRoleMap.put(UserType.lgstcCo, 7);
		// 仓储公司角色ID
		userRoleMap.put(UserType.stgco, 8);
		// 集团用户角色ID
		userRoleMap.put(UserType.Group, 2);
		// 厂商用户角色ID
		userRoleMap.put(UserType.Factory, 3);
		// 经销商用户角色ID
		userRoleMap.put(UserType.Dealer, 4);
		// 大客户用户角色ID
		userRoleMap.put(UserType.Company, 5);
	}
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.repository = userRepository;
    }
    
    @Transactional
    public void delete(Integer id) {
        logger.info("delete id:{}", id);
        userRoleRepository.deleteByUserId(id);
        repository.delete(id);
    }
    
   	@Transactional
	public void save(User user) {
		logger.info("save user:{}", user);
		
		User dbUser = null;
		if(user.getId() != null) {
			dbUser = repository.findOne(user.getId());
			if (dbUser == null) {
				throw new TspException( "用户未找到或已删除！");
			}
		}
		
		//用户名唯一验证
		if (usernameExist(user, dbUser)) {
			throw new TspException("用户名已存在！");
		}
		//手机号唯一验证
		if (StringET.isNotEmpty(user.getMobile()) && mobileExist(user, dbUser)) {
			throw new TspException("手机号码已存在！");
		}
		//邮箱唯一验证
		if (StringET.isNotEmpty(user.getEmail()) && emailExist(user, dbUser)) {
			throw new TspException("邮箱地址已存在！");
		}

		//新增用户
		if(user.getId() == null) {
    		user.setLocked(false);
    		user.setPassword("123456");
			user.setPassword(SecurityET.encryptPassword(user.getPassword()));
			super.save(user);
			
			//根据用户类型，添加默认角色
			UserRole ur = new UserRole();
			ur.setRoleId(userRoleMap.get(user.getUserType()));
			ur.setUserId(user.getId());
			userRoleRepository.save(ur);
		} else {
		//修改用户
			dbUser.setUsername(user.getUsername());
			dbUser.setSex(user.getSex());
			dbUser.setChineseName(user.getChineseName());
			dbUser.setMobile(user.getMobile());
			dbUser.setNickname(user.getNickname());
			dbUser.setEmail(user.getEmail());
			dbUser.setMemo(user.getMemo());
			dbUser.setAppEnabled(user.getAppEnabled());
			
			repository.save(dbUser);
		}
	}
     
    public User findByUsername(String username) {
    	return getRepository().findByUsername(username);
    }
    
    @Transactional
    public void updateLocked(Integer id, Boolean locked) {
    	User dbUser = repository.findOne(id);
		if (dbUser == null) {
			throw new TspException( "用户未找到或已删除！");
		}
		
		//用户身份
    	LoginUser user = (LoginUser)SecurityContext.getAuthUser();
    	if(UserType.Group != user.getUserType() && !user.hasRole("ROLE_ADMIN") && !dbUser.getCompanyId().equals(user.getCompanyId())) {
			throw new TspException( "没有权限！");
    	}
    	getRepository().updateLocked(id, locked);
    }
    
    @Transactional
    public void resetPassword(Integer id, String newPassword) {
    	User user = repository.getOne(id);
		if(user == null) {
			throw new TspException(CommonErrors.DATA_NOT_FOUND, "用户未找到或已删除！");
		}
    	getRepository().updatePassword(id, SecurityET.encryptPassword(newPassword));
    }
    
    @Transactional
    public void updatePassword(Integer id, String oldPassword, String newPassword) {
    	User user = repository.getOne(id);
    	if(user == null) {
    		throw new TspException(CommonErrors.DATA_NOT_FOUND, "用户未找到或已删除！");
    	}
    	
    	if(!SecurityET.encryptPassword(oldPassword).equals(user.getPassword())) {
    		throw new TspException("原密码错误！");
    	}

    	getRepository().updatePassword(id, SecurityET.encryptPassword(newPassword));
    }
    
    @Transactional
    public void updatePasswordExt(Integer id, String oldPassword, String newPassword) {
    	User user = repository.getOne(id);
    	if(user == null) {
    		throw new TspException(CommonErrors.DATA_NOT_FOUND, "用户未找到或已删除！");
    	}
    	String ext = user.getExt();
    	if(StringUtils.isEmpty(ext)){
    		ext = SecurityET.encryptPassword("0000");
    	}
    	if(!SecurityET.encryptPassword(oldPassword).equals(ext)) {
    		throw new TspException("原密码错误！");
    	}
    	getRepository().updateExt(id, SecurityET.encryptPassword(newPassword));
    }
    
    public List<User> queryByIds4Channel(Integer[] ids) {
    	return  getRepository().findByIds(ids);
    }
    
    
    private boolean usernameExist(User user, User dbUser) {
    	if(user.getId() == null || !Objects.equals(user.getUsername(), dbUser.getUsername())) {
    		return getRepository().countByUsername(user.getUsername()) > 0;
    	}
    	
    	return false;
    }

    private boolean mobileExist(User user, User dbUser) {
    	if(user.getId() == null || !Objects.equals(user.getMobile(), dbUser.getMobile())) {
    		return getRepository().countByMobile(user.getMobile()) > 0;
    	}
    	
    	return false;
    }

    private boolean emailExist(User user, User dbUser) {
    	if(user.getId() == null || !Objects.equals(user.getEmail(), dbUser.getEmail())) {
    		return getRepository().countByEmail(user.getEmail()) > 0;
    	}
    	
    	return false;
    }
    
    private UserRepository getRepository() {
    	return (UserRepository)this.repository;
    }
}
