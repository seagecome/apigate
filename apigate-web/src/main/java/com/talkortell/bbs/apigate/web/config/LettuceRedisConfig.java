package com.talkortell.bbs.apigate.web.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkortell.bbs.apigate.config.RedisConfig;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

@Configuration
public class LettuceRedisConfig {
	@Autowired
	private RedisConfig redisConfig;
	
	@Bean
	@Primary
	@Qualifier("redisClusterConfiguration")
	public RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration redisCluster = new RedisClusterConfiguration();
		String[] serverArray = redisConfig.getClusterNodes();
		Set<RedisNode> nodes = new HashSet<RedisNode>();
		for(String serverInfo : serverArray) {
			String[] ipPort = serverInfo.split(":");
			nodes.add(new RedisNode(ipPort[0].trim(), Integer.valueOf(ipPort[1].trim())));
		}
		
		if(StringUtils.isNotBlank(redisConfig.getPassword())) {
			redisCluster.setPassword(redisConfig.getPassword());
		}
		
		redisCluster.setClusterNodes(nodes);
		redisCluster.setMaxRedirects(redisConfig.getMaxRedirects());
		
		return redisCluster;
	}
	
	@Bean
	public GenericObjectPoolConfig<?> genericObjectPoolConfig(){
		GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
		genericObjectPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
		genericObjectPoolConfig.setMinIdle(redisConfig.getMinIdle());
		genericObjectPoolConfig.setMaxTotal(redisConfig.getMaxActive());
		genericObjectPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait());
		return genericObjectPoolConfig;
	}
	
	@Bean
	public ClientOptions clientOptions() {
		final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(redisConfig.getSocketTimeout()).build();
		return ClientOptions.builder().socketOptions(socketOptions).disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
				.autoReconnect(true).build();
	}
	
	@Bean(destroyMethod="shutdown")
	@ConditionalOnMissingBean(ClientResources.class)
	public DefaultClientResources clientResources() {
		return DefaultClientResources.create();
	}
	
	@Bean
	@Qualifier("lettucePoolConfig")
	public LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions clientOptions, ClientResources clientResources) {
		return LettucePoolingClientConfiguration.builder()
				.poolConfig(genericObjectPoolConfig())
				.clientOptions(clientOptions)
				.commandTimeout(redisConfig.getCommandTimeout())
				.clientResources(clientResources)
				.build();
	}
	
	@Bean
	@Primary
	@Qualifier("clusterConnectionFactory")
	public RedisConnectionFactory clusterConnectionFactory(
			RedisClusterConfiguration clusterConfiguration, 
			LettucePoolingClientConfiguration clientConfig) {
		return new LettuceConnectionFactory(clusterConfiguration, clientConfig);
	}
	
	@Bean
	public Jackson2JsonRedisSerializer<?> jsonRedisSerialize(){
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
	}
	
	@Bean
	@Primary
	@Qualifier("redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory connectionFactory){
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setValueSerializer(jsonRedisSerialize());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
}
