package com.xai.tt.business.client.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.tianan.common.api.bean.AuthUser;
import com.tianan.common.api.data.CompanyAuthorized;
import com.tianan.common.api.data.DataAuthorized;
import com.tianan.common.api.data.DealerAuthorized;
import com.tianan.common.api.data.FactoryAuthorized;
import com.tianan.common.api.support.AntPathMatcher;
import com.xai.tt.business.client.entity.Menu;
import com.xai.tt.business.client.entity.Role;
import com.xai.tt.business.client.entity.User;
import com.xai.tt.business.client.enums.UserType;

public class LoginUser implements AuthUser {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date createTime;
	private String username;
	private String mobile;
	private String email;
	private UserType userType;
	private Integer companyId;
	private String headImg;
	private String nickname;
	private String chineseName;
	private String sex;
	private String birthday;
	private String location;
	private String registerApp;
	private String dataRange;
	
	private List<Role> roles = new ArrayList<Role>();
	private List<Menu> menus = new ArrayList<Menu>();
	private List<String> permits = new ArrayList<String>();
	
	public LoginUser() {}
	
	public LoginUser(User user) {
		this.id = user.getId();
		this.createTime = user.getCreateTime();
		this.username = user.getUsername();
		this.mobile = user.getMobile();
		this.email = user.getEmail();
		this.userType = user.getUserType();
		this.companyId = user.getCompanyId();
		this.headImg = user.getHeadImg();
		this.nickname = user.getNickname();
		this.chineseName = user.getChineseName();
		this.sex = user.getSex();
		this.birthday = user.getBirthday();
		this.location = user.getLocation();
		this.registerApp = user.getRegisterApp();
		this.dataRange = user.getDataRange();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRegisterApp() {
		return registerApp;
	}

	public void setRegisterApp(String registerApp) {
		this.registerApp = registerApp;
	}

	public String getDataRange() {
		return dataRange;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public List<String> getPermits() {
		return permits;
	}

	public void setPermits(List<String> permits) {
		this.permits = permits;
	}

	public boolean hasRole(String roleCode) {
		for (Role role : roles) {
			if(role.getCode().equals(roleCode)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasPermit(String uri) {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		for (String permit : permits) {
			if(pathMatcher.match(permit, uri)){
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean dataAuthorized(DataAuthorized entity) {
		if(userType == UserType.Group || hasRole("ROLE_ADMIN")) {
			return true;
		}
		
		if(this.companyId == null) {
			return false;
		}
		
		//厂商用户
		if(entity instanceof FactoryAuthorized && userType == UserType.Factory) {
			FactoryAuthorized f = (FactoryAuthorized)entity;
			if(Objects.equals(this.companyId, f.getFactoryId())) {
				return true;
			} else {
				return false;
			}
		}
		
		//经销商用户
		if(entity  instanceof DealerAuthorized && userType == UserType.Dealer) {
			DealerAuthorized d = (DealerAuthorized)entity;
			if(Objects.equals(this.companyId, d.getDealerId())) {
				return true;
			} else {
				return false;
			}
		}
		
		//大客户用户
		if(entity instanceof CompanyAuthorized && userType == UserType.Company) {
			CompanyAuthorized f = (CompanyAuthorized)entity;
			if(Objects.equals(this.companyId, f.getCompanyId())) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
