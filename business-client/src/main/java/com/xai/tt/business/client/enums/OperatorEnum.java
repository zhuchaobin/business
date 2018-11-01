package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  OperatorEnum {

    GT("gt","大于"),
    LT("lt","小于"),
    GE("ge","大于等于"),
    LE("le","小于等于"),
    NE("ne","不等于"),
    EQ("eq","等于"),
     ;

    public String code;
    public String msg;

    OperatorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        try {
            return Arrays.stream(OperatorEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
