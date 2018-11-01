package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  AppTypeEnum {

    APP_ADMIN((byte)0,"手机App管理者"),
    OTHER((byte)1, "其他"),
    ;

    public byte code;
    public String msg;

    AppTypeEnum(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(byte code) {
        try {
            return Arrays.stream(AppTypeEnum.values()).filter(item -> code == item.code).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
