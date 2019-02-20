package com.xai.tt.business.client.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.tianan.common.api.jpa.IncrEntity;

@Entity
@Table(name="oper_log")
public class OperLog extends IncrEntity {
	private static final long serialVersionUID = 1L;

	private String username;
	private String mobile;
	@Column(name="company_name")
	private String companyName;
	private String content;
	@Column(name="oper_data")
	private String operData;
	private String ip;
	@Column(name="oper_time")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date operTime;
	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOperData() {
		return operData;
	}
	public void setOperData(String operData) {
		this.operData = operData;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

}
