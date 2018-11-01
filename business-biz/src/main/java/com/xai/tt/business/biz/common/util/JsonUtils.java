package com.xai.tt.business.biz.common.util;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linkun on 2017/10/1.
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);


    public static String toJson(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.error("序列化发生异常", e);
            return "";
        }
    }

    public static <T> T fromJson(String str, Class<T> tClass) {
        try {
            T object = JSON.parseObject(str, tClass);
            return object;
        } catch (Exception e) {
            logger.error("反序列化失败", e);
            return null;
        }

    }
}
