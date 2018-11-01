package com.xai.tt.business.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianan.kltsp.monitor.sdk.FailRateMonitor;

@Aspect
@Component
public class MonitorAOP {
	@Around("execution(* com.tianan..controller..*.*(..)) and @annotation(rm)")
	public Object Interceptor(ProceedingJoinPoint pjp, RequestMapping rm) throws Throwable {
		FailRateMonitor frm = FailRateMonitor.create("admin", getApiName(pjp));
		try {
			Object result = pjp.proceed();
			frm.incrTotel();
			return result;
		} catch (Exception e) {
			frm.incrFail();
			throw e;
		}
	}

	private String getApiName(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod(); // 获取被拦截的方法
		String methodName = method.getName(); // 获取被拦截的方法名
		return signature.getDeclaringType().getSimpleName() + "-" + methodName;
	}

}
