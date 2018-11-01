package com.xai.tt.business;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ai.tsp.gis.api.DistrictTools;

@Component
public class DistrictLoader {

	private static Logger logger = LoggerFactory.getLogger(DistrictLoader.class);

	@PostConstruct
	private void init() {
		long start = System.currentTimeMillis();
		logger.info("加载行政区划缓存");
		DistrictTools.init();
		logger.info("加载行政区划缓存完成，耗时：{}", System.currentTimeMillis() - start);
	}
}
