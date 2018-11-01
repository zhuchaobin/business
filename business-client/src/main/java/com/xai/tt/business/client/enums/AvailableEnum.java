package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  AvailableEnum {

    AVAILABLE((byte)0, "不可用"),
    UN_AVAILABLE((byte)1, "可用"),
    ;
    public byte code;
    public String msg;

    AvailableEnum(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(byte code) {
        try {
            return Arrays.stream(AvailableEnum.values()).filter(item -> code == item.code).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }

}
