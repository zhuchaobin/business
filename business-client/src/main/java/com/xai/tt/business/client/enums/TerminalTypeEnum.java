package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  TerminalTypeEnum {

    Y_T_J((byte)1,"一体机"),
    TBOX((byte)2,"Tbox"),
    ;
    public Byte code;
    public String msg;

    TerminalTypeEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Byte code) {
        try {
            return Arrays.stream(TerminalTypeEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
