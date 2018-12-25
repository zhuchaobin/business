package com.xai.tt.business.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tianan.common.api.jpa.IncrEntity;
import com.xai.tt.business.client.enums.UserType;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username", "mobile", "email"})})
public class User extends IncrEntity {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String mobile;
	private Boolean mobileVerified;
	private String email;
	private Boolean emailVerified;
	private UserType userType;
	private Integer companyId;
	private String companyName;
	private String headImg;
	private String nickname;
	private String chineseName;
	private String sex;
	private String birthday;
	private String location;
	private String memo;
	private Boolean appEnabled;
	private Boolean locked;
	private String registerApp;
	private String dataRange;
	private String ext;
	// 供应链
	private Integer splchainCo;
	// 角色类型
	private String usrTp;
	// 认证类型
	private String ctfnTp;
	// 联系人
	private String ctcpsn;
	// 联系电话
	private String ctcTel;
	// 审核标志
	private Integer adtInd;


	public Integer getSplchainCo() {
		return splchainCo;
	}

	public void setSplchainCo(Integer splchainCo) {
		this.splchainCo = splchainCo;
	}

	public String getUsrTp() {
		return usrTp;
	}

	public void setUsrTp(String usrTp) {
		this.usrTp = usrTp;
	}

	public String getCtfnTp() {
		return ctfnTp;
	}

	public void setCtfnTp(String ctfnTp) {
		this.ctfnTp = ctfnTp;
	}

	public String getCtcpsn() {
		return ctcpsn;
	}

	public void setCtcpsn(String ctcpsn) {
		this.ctcpsn = ctcpsn;
	}

	public String getCtcTel() {
		return ctcTel;
	}

	public void setCtcTel(String ctcTel) {
		this.ctcTel = ctcTel;
	}

	public Integer getAdtInd() {
		return adtInd;
	}

	public void setAdtInd(Integer adtInd) {
		this.adtInd = adtInd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(Boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Boolean getAppEnabled() {
		return appEnabled;
	}

	public void setAppEnabled(Boolean appEnabled) {
		this.appEnabled = appEnabled;
	}

	@Column(updatable = false)
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Column(updatable = false)
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
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}
