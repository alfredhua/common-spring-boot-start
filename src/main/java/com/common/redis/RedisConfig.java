package com.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

@Configuration
@ConditionalOnClass(RedisUtils.class)
@ConditionalOnProperty(prefix = "spring.redis", value = "enable", matchIfMissing = true)
public class RedisConfig {


    @Bean
    public RedisUtils redisUtils(@Autowired RedisConnectionFactory connectionFactory){
        return new RedisUtils(createRedisTemplate(connectionFactory));
    }

    public static<T> RedisTemplate<String, T> createRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public static RedisSerializer valueSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    public static RedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }


    @Bean
    public RedisLockUtils redirsLockUtils(@Autowired RedissonClient redissonClient){
        return new RedisLockUtils(redissonClient);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.redis.redisson.config")
    public RedissonClient redisson() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource("redisson.yaml").getInputStream());
        return Redisson.create(config);
    }

}