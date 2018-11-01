package com.xai.tt.business.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.tianan.common.api.jpa.IncrEntity;

@Entity
public class Role extends IncrEntity {
	private static final long serialVersionUID = 1L;

	private String code;
	private String name;
	private Boolean assignable;
	private Boolean system;
	private Boolean locked;
	private String memo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(updatable = false)
	public Boolean getAssignable() {
		return assignable;
	}

	public void setAssignable(Boolean assignable) {
		this.assignable = assignable;
	}

	@Column(updatable = false)
	public Boolean getSystem() {
		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;
	}

	@Column(updatable = false)
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
