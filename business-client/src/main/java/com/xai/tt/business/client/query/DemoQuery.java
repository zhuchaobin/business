package com.xai.tt.business.client.query;

import com.tianan.common.api.mybatis.SortQuery;

public class DemoQuery extends SortQuery {
	private static final long serialVersionUID = 1L;

	private String usernameLike;
	private String chineseNameLike;
	private String sex;
	private String mobile;

	public String getUsernameLike() {
		return usernameLike;
	}

	public void setUsernameLike(String usernameLike) {
		this.usernameLike = usernameLike;
	}

	public String getChineseNameLike() {
		return chineseNameLike;
	}

	public void setChineseNameLike(String chineseNameLike) {
		this.chineseNameLike = chineseNameLike;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
