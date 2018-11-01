package com.xai.tt.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by ssr on 2017/3/21.
 */
//@Configuration
public class JedisConfig extends WebMvcConfigurerAdapter {
	
    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;
    
    @Value("${redis.server.ip}")
    private String ip;
    @Value("${redis.server.port}")
    private Integer port;
    @Value("${redis.server.timeout}")
    private Integer timeout;
    @Value("${redis.server.password}")
    private String password;
    @Value("${redis.server.database}")
    private Integer database;
    
    @Bean 
    public JedisPoolConfig getJedisPoolConfig() {
    	JedisPoolConfig config = new JedisPoolConfig();
    	config.setMaxIdle(maxIdle);
    	config.setMaxTotal(maxTotal);
    	config.setTestOnBorrow(false);
    	config.setTestOnReturn(false);
    	
        return config;
    }
    
    @Bean 
    public JedisPool getJedisPool() {
    	return new JedisPool(
    		getJedisPoolConfig(),
			ip,
			port,
			timeout,
			password,
			database
    	);
    }
}
