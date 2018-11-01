package com.xai.tt.business.biz.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Component
public class AcsClientFactory{
	private static IAcsClient client = null;
	
	private static String accessKeyId;
	private static String secret;
	private static String regionId;
	public static String aliyunUserId;
	public static String ecsGroupId;

	public static IAcsClient getClient() {
		if(client == null) {
			IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
			client = new DefaultAcsClient(profile);
		}

		return client;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	@Value("${ecs.accessKeyId}")
	public void setAccessKeyId(String accessKeyId) {
		AcsClientFactory.accessKeyId = accessKeyId;
	}

	public String getSecret() {
		return secret;
	}

	@Value("${ecs.secret}")
	public void setSecret(String secret) {
		AcsClientFactory.secret = secret;
	}

	public String getRegionId() {
		return regionId;
	}

	@Value("${ecs.regionId}")
	public void setRegionId(String regionId) {
		AcsClientFactory.regionId = regionId;
	}

	public String getAliyunUserId() {
		return aliyunUserId;
	}

	@Value("${ecs.aliyunUserId}")
	public void setAliyunUserId(String aliyunUserId) {
		AcsClientFactory.aliyunUserId = aliyunUserId;
	}

	public String getEcsGroupId() {
		return ecsGroupId;
	}

	@Value("${ecs.ecsGroupId}")
	public void setEcsGroupId(String ecsGroupId) {
		AcsClientFactory.ecsGroupId = ecsGroupId;
	}
}
