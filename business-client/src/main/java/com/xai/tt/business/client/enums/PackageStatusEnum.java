package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * 套餐状态
 * yangyu
 */
public enum PackageStatusEnum {
    USE(1,"使用中"),
    EXPIRE(2,"已过期"),
    USE_UP(3,"用尽"),
    ;

    public Integer code;
    public String msg;

    PackageStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Integer code) {
        try {
            return Arrays.stream(PackageStatusEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
