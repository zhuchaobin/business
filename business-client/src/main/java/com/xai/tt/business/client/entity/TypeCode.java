package com.xai.tt.business.client.entity;

import javax.persistence.Entity;

import com.tianan.common.api.jpa.IncrEntity;

@Entity
public class TypeCode extends IncrEntity {
	private static final long serialVersionUID = 1L;
	
	private String tcode;
	private String tname;
	private String content;
	
	public String getTcode() {
		return this.tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
