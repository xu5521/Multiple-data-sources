package com.example.leo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * RedisTemplate  配置
 *
 * @author L.cm
 */
@EnableCaching
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfig {

//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//		return redisTemplate;
//	}


//	@Bean
//	@ConditionalOnProperty(value = "spring.redis.cluster.enable",havingValue = "true")
//	public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
//		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
//
//		// https://github.com/lettuce-io/lettuce-core/wiki/Redis-Cluster#user-content-refreshing-the-cluster-topology-view
//		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
//				.enablePeriodicRefresh()
//				.enableAllAdaptiveRefreshTriggers()
//				.refreshPeriod(Duration.ofSeconds(5))
//				.build();
//
//		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
//				.topologyRefreshOptions(clusterTopologyRefreshOptions).build();
//
//		// https://github.com/lettuce-io/lettuce-core/wiki/ReadFrom-Settings
//		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
//				.readFrom(ReadFrom.SLAVE_PREFERRED)
//				.clientOptions(clusterClientOptions).build();
//
//		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
//	}

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		//key 采用的String 的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		//hashde key 也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		//value 序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//hash 的 value序列化方式采用jackson
		template.setHashKeySerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();template.afterPropertiesSet();
		return template;
	}
}
