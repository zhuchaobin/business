package com.xai.tt.business;

import java.util.List;
import java.util.Properties;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.pagehelper.PageHelper;
import com.tianan.common.mvc.filter.SecurityFilter;
import com.tianan.common.mvc.handler.HttpCriteriaResolver;

/**
 * Created by ssr on 2017/3/21.
 */
@Configuration
@ImportResource({ "classpath:dubbo.xml", "classpath:job.xml"})  
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	/***
	 * filter 注册
	 */
    @Bean 
    public FilterRegistrationBean loginFilterRegistration() {
    	SecurityFilter.loginUrls = new String[] { "/", "/index", "/home", "/logout", "/modifyPassword", "/haveFault","/typeCode/*","/home/*","/user/register","/user/toRegisterPage","/user/privacy","*/www.shfe.com.cn/*","/ancmNews/queryPage","/ancmNews/ancmNewsList","/ancmNews/get","/ancmNews/newsContent"};
    	SecurityFilter.anonUrls =  new String[] { "/checkstartup.html", "/resources/**", "/login", "/error", "/favicon.ico" ,"/forget","/forgetPassword","/publish/showPage","/dynamicApp/*" ,"/register","/upload_files/*","/user/register","/user/toRegisterPage","/user/privacy","*/www.shfe.com.cn/*","/ancmNews/queryPage","/ancmNews/ancmNewsList","/ancmNews/get","/ancmNews/newsContent"};
        FilterRegistrationBean registration = new FilterRegistrationBean(new SecurityFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
    
    /***
     * 使用Fastjson做为转换器
     */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
    
	/***
	 * 添加参数转换器，把表单提交的查询条件封装成HttpCriteria对像
	 */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HttpCriteriaResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
    
    /***
     * mybatis pagehelper插件支持
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
