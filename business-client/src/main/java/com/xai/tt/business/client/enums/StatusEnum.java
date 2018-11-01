package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  StatusEnum {

    ON_LINE((byte)1, "在线"),
    OFF_LINE((byte)0, "下线"),
    ;

    public Byte code;
    public String msg;

    StatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Byte code) {
        try {
            return Arrays.stream(StatusEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
