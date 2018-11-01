package com.xai.tt.business.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.tianan.common.api.data.CompanyAuthorized;
import com.tianan.common.api.data.DealerAuthorized;
import com.tianan.common.api.data.FactoryAuthorized;
import com.tianan.common.api.mybatis.Queryable;
import com.tianan.common.api.support.DubboSecurityContext;
import com.tianan.common.api.support.SecurityContext;
import com.xai.tt.business.client.enums.UserType;
import com.xai.tt.business.client.vo.LoginUser;

@Aspect
@Component
public class DataAuthorizedAOP {
	@Pointcut("execution(* com.tianan..inter..*.*(..)) || execution(* com.tianan..service..*.*(..))")
	public void serviceMethodPointcut() {
	}

	@Around("serviceMethodPointcut()") 
	public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
		if(pjp.getTarget().getClass().getName().startsWith("com.tianan.kltsp.admin")) {
			return pjp.proceed();
		}
		
		LoginUser user = (LoginUser)SecurityContext.getAuthUser();
		if(user == null) {
			return pjp.proceed();
		}

		Map<String, String> map = new HashMap<>();
		map.put("userId", user.getId().toString());
		map.put("username", user.getUsername());
		map.put("userType", user.getUserType().toString());
		map.put("userCompanyId", String.valueOf(user.getCompanyId()));
		DubboSecurityContext.contextTransfer(map);
		
		if(user.hasRole("ROLE_ADMIN") || UserType.Group == user.getUserType()) {
			return pjp.proceed();
		}
		
		for (Object param : pjp.getArgs()) {
			if(param == null) {
				continue;
			}
			if(param instanceof Queryable) {
				if(param instanceof FactoryAuthorized && UserType.Factory == user.getUserType()) {
					FactoryAuthorized query = (FactoryAuthorized)param;
					query.setFactoryId(user.getCompanyId());
				} else if(param instanceof DealerAuthorized && UserType.Dealer == user.getUserType()) {
					DealerAuthorized query = (DealerAuthorized)param;
					query.setDealerId(user.getCompanyId());
				} else if(param instanceof CompanyAuthorized && UserType.Company == user.getUserType()) {
					CompanyAuthorized query = (CompanyAuthorized)param;
					query.setCompanyId(user.getCompanyId());
				}
			}
		} 

		return pjp.proceed();
	}
}
