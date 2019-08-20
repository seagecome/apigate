package com.talkortell.bbs.apigate.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@Getter
@PropertySource("classpath:redis.properties")
public class RedisConfig {

	@Value("#{'${spring.redis.cluster.nodes}'.split(',')}")
	private String[] clusterNodes;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.timeout}")
	private Duration socketTimeout;
	@Value("${spring.redis.command.timeout}")
	private Duration commandTimeout;
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private int minIdle;
	@Value("${spring.redis.lettuce.pool.max-active}")
	private int maxActive;
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private long maxWait;
	@Value("${spring.redis.cluster.max-redirects}")
	private int maxRedirects;
	
}
