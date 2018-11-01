package com.xai.tt.business.client.enums;

import java.util.Arrays;

public enum  CardStatusEnum {
    ACTIVE(1,"已激活"),
    DISABLE(2,"已停用"),
    READY(3,"待激活"),
            ;

    public Integer code;
    public String msg;

    CardStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Integer code) {
        try {
            return Arrays.stream(CardStatusEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
