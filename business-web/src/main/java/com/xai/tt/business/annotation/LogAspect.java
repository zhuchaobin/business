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
		Upload_Ar_Atch("长约发起上传附件"),
		//
		Login("登录系统"),
		Update_Password("修改密码"),
		Forget_Password("忘记密码"),
		Update_KnowledgeCatalog("更新知识库目录"), Del_KnowledgeCatalog("删除知识库目录"),
		Update_KnowledgeCatalogInfo("更新知识库"), Del_KnowledgeCatalogInfo("删除知识库"), Upload_KnowledgeCatalogInfo("上传知识库附件"),
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
		// added by zhuchaobin,20190202
		Update_vrty("更新品种"),
		Delete_vrty("删除品种"),
		Export_Log("导出操作日志信息"),
		Update_HelpFaq("更新FAQ"), Del_HelpFaq("删除FAQ"),
		Update_OpinionSuggestion("更新意见建议"), Del_OpinionSuggestion("删除意见建议"), Export_OpinionSuggestion("导出意见建议"),
		;

		private final String msg;

		private LogType(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}
	}

}
