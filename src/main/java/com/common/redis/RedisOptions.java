package com.common.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @auth guozhenhua
 * @date 2018/12/18
 */
public class RedisOptions {

    public static<T> ValueOperations<String, T>  createValueOptions(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper, Class<T> tClass){
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<T> tmp0=new Jackson2JsonRedisSerializer<T>(tClass);
        tmp0.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(tmp0);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate.opsForValue();
    }

    public static<T> ValueOperations<String, T>  createListOptions(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate.opsForValue();
    }
}
