package com.xai.tt.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAspect {

	String value() default "";
	
	String[] argNames() default {};//参数名
	
	Class<?>[] objectNames() default {};//参数对象名

	LogType type();

	public enum LogType {
		Login("登录系统"),
		Update_Password("修改密码"),
		Forget_Password("忘记密码"),
		Update_Car_Model("更新车型信息"),
		Delete_Car_Model("删除车型信息"),
		Export_Car_Model("导出车型信息"),
		Add_Fleet_Task("新建任务"),
		Update_Fleet_Task("更新任务"),
		Delete_Fleet_Task("删除任务"),
		Export_Fleet_Task("导出任务"),
		Add_Electric_Fence("设置电子围栏"),
		Delete_Electric_Fence("删除电子围栏"),
		Update_Electric_Fence("更新电子围栏"),
		Add_Car_Info("新增车辆"),
		Update_Car_Info("更新车辆"),
		Delete_Car_Info("删除车辆"),
		Export_Car_Info("导出车辆"),
		Import_Car_Info("导入车辆"),
		Change_Car_Tbox("更换车辆设备"),
		Export_CarBounds("导出越界信息"),
		Export_CarHistoryFault("导出历史故障信息"),
		Export_Car_Owner("导出车主信息"),
		Update_CompanyCarGroup("更新车队信息"),
		Delete_CompanyCarGroup("删除车队信息"),
		Export_CompanyCarGroup("导出车队信息"),
		Update_Company("更新大客户信息"),
		Delete_Company("删除大客户信息"),
		Import_Company("导入大客户信息"),
		Export_Company("导出大客户信息"),
		Update_Dealer("更新经销商信息"),
		Delete_Dealer("删除经销商信息"),
		Import_Dealer("导入经销商信息"),
		Export_Dealer("导出经销商信息"),
		Update_Factory("更新车厂信息"),
		Delete_Factory("删除车厂信息"),
		Import_Factory("导入车厂信息"),
		Export_Factory("导出车厂信息"),
		Update_Driver("更新司机信息"),
		Delete_Driver("删除司机信息"),
		Import_Driver("导入司机信息"),
		Export_Driver("导出司机信息"),
		Update_Supplier("更新服务商信息"),
		Delete_Supplier("删除服务商信息"),
		Import_Supplier("导入服务商信息"),
		Export_Supplier("导出服务商信息"),
		Export_Log("导出操作日志信息"),
		Export_Order("导出远程控制记录信息"),
		Add_Order("导出远程控制记录信息"),
		Update_Apppackage("更新App版本包"),
		Del_Apppackage("删除App版本包"),
		Upload_Apppackage("上传App版本包"),
		Export_Apppackage("导出App版本信息"),
		Export_ApppackageLog("导出App版本更新日志信息"),
		Del_ApppackageLog("删除App版本更新日志"),
		Export_CarFault("导出车辆故障信息"),
		Update_CustomFaultConfig("更新自定义配置故障"),
		Del_CustomFaultConfig("删除自定义配置故障"),
		Export_CustomFaultConfig("导出自定义配置故障"),
		Update_CustomFault("更新自定义故障"),
		Del_CustomFault("删除自定义故障"),
		Export_CustomFault("导出自定义篇故障"),
		Export_BatteryData("导出电池健康状态信息"),
		Export_ChargeData("导出充电信息"),
		Export_ChargeDataDetail("导出充电信息明细"),
		Update_HelpFaq("更新FAQ"), Del_HelpFaq("删除FAQ"),
		Update_KnowledgeCatalog("更新知识库目录"), Del_KnowledgeCatalog("删除知识库目录"),
		Update_KnowledgeCatalogInfo("更新知识库"), Del_KnowledgeCatalogInfo("删除知识库"), Upload_KnowledgeCatalogInfo("上传知识库附件"),
		Export_Message("导出Message"), Export_UsageCar("导出车辆使用率"),
		Update_OpinionSuggestion("更新意见建议"), Del_OpinionSuggestion("删除意见建议"), Export_OpinionSuggestion("导出意见建议"),
		Export_TboxLog("导出Tbox更新日志"),
		Update_TboxInfo("更新Tbox信息"), Import_TboxInfo("导入TBox信息"), Export_TboxInfo("导出TBox信息"),
		Update_TboxParameter("更新TBox参数"), Export_TboxParameter("导出TBox参数"),
		Update_TerminalPackage("更新终端包信息"), Del_TerminalPackage("删除终端包信息"), Upload_TerminalPackage("上传终端包信息"), Export_TerminalPackage("导出终端包信息"),
		Update_TerminalPackageLog("更新终端包日志信息"), Del_TerminalPackageLog("删除终端包日志信息"), Export_TerminalPackageLog("导出终端包日志信息"),
		Export_ParkData("导出停车信息"), Export_MileageData("导出行驶里程信心"),
		Export_App_Log("导出APP操作日志信息"),
		Update_AppWarningContact("更新平台故障联系人信息"),
		Delete_AppWarningContact("删除平台故障联系人信息"),
		Export_AppWarningContact("导出平台故障联系人信息"),
		Export_CardStatiscs("导出通信套餐号卡统计信息"),
		Export_FlowOrder("导出通信套餐订单信息"),
		Export_FlowStatisics("导出通信套餐流量统计信息"),
		Export_PackageStatisics("导出通信套餐营收统计信息"),
		Export_Address("导出常用地址信息"),
		Update_Menu("更新菜单"),
		Delete_Menu("删除菜单"),
		Update_Role("更新角色"),
		Delete_Role("删除角色"),
		Locked_Role("锁定或解锁角色"),
		Distribute_Menu("分配菜单"),
		Update_TypeCode("更新业务代码"),
		Delete_TypeCode("删除业务代码"),
		Update_User("更新用户信息"),
		Delete_User("删除用户"),
		Locked_User("锁定或解锁用户"),
		Reset_Password("重置密码"),
		Distribute_Role("分配角色"),
		Add_Publish("新增推广活动"),
		Update_Publish("更新推广活动"),
		Delete_Publish("删除推广活动"),
		Upload_Publish("上传推广活动附件信息"),
		Add_PushPublish("新增投放活动"),
		Update_PushPublish("更新投放活动"),
		Delete_PushPublish("删除投放活动"),
		Update_AppCode("更新AppCode"),
		Delete_AppCode("删除AppCode"),
        Update_UserProtocol("更新用户协议"),
		Delete_UserProtocol("删除用户协议"),
		Update_MarketProduct("更新商品信息"),
		Delete_MarketProduct("删除商品协议"),

		Update_MaintenanceAppointment("更新维保记录"),
		Export_MaintenanceAppointment("导出维保记录");

		private final String msg;

		private LogType(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}
	}

}
