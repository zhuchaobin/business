package com.xai.tt.business.client.enums;

import java.util.Arrays;

/**
 * Created by linkun on 2017/10/29.
 */
public enum  MonitorItemEnum {

    C_S("1","车速"),
    SOC("2","SOC"),
    Z_G_L("3","总里程"),
    P_B_MAX_Y("4","单体电池最高电压"),
    P_B_MIN_Y("5","单体电池最低电压"),
    ALL_Y("6","总电压"),
    ALL_L("7","总电流"),
    C_MAX_W("8","充电时电池最高温度"),
    C_MIN_W("9","充电时电池最低温度"),
    F_B_MAX_W("10","放电时电池最高温度"),
    F_B_MIN_W("11","放电时电池最低温度"),
    DK_INPU_Y("12","电机控制器输入电压"),
    DJ_ZL_MXD("13","电机控制器直流母线电"),
    QD_DY_W("14","驱动电机温度"),
    QD_DK_W("15","驱动电机控制器温度"),
    QD_DJ_Z("16","驱动电机转速"),
    ;
    public String code;
    public String msg;

    MonitorItemEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(String code) {
        try {
            return Arrays.stream(MonitorItemEnum.values()).filter(item -> item.code.equals(code)).findFirst().get().msg;
        } catch (Exception e) {
            return "未知";
        }

    }
}
