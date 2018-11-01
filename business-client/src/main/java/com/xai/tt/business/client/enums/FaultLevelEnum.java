package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  FaultLevelEnum {

    LEVEL_ONE(1,"一级等级"),
    LEVEL_TWO(2,"二级等级"),
    LEVEL_THREE(3,"三级等级"),
    LEVEL_FORE(4,"四级等级"),
    LEVEL_FIVE(5,"五级等级"),
    ;

    public Integer code;
    public String msg;

    FaultLevelEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Integer code) {
        try {
            return Arrays.stream(FaultLevelEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
