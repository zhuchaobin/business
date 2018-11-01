package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * 卡的动作类型，激活，停卡
 */
public enum CardOperEnum {
    ACTIVE(1,"激活"),
    DISABLE(2,"停用"),
    ;

    public Integer code;
    public String msg;

    CardOperEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Integer code) {
        try {
            return Arrays.stream(CardOperEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
