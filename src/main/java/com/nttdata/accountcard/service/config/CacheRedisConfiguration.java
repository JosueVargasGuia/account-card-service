package com.nttdata.accountcard.service.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration; 
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.nttdata.accountcard.service.entity.AccountCard;

import reactor.core.publisher.Mono;
 

@Configuration
public class CacheRedisConfiguration {
	@Bean
	public RedisCacheConfiguration cacheConfiguracion() {
		
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)).disableCachingNullValues()
				//.serializeValuesWith(SerializationPair.fromSerializer(new ))
				;
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder.withCacheConfiguration("AccountCardCache",
				RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)));
	}

}
