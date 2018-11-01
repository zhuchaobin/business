package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  UpgradeTypeEnum {
    DX_UP((byte)1,"定向升级"),
    ALL_UP((byte)2,"全部升级"),
    ;

    public Byte code;
    public String msg;

    UpgradeTypeEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Byte code) {
        try {
            return Arrays.stream(UpgradeTypeEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
