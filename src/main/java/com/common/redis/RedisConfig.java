package com.common.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        //开启事务
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
//    }

    @Bean
    public RedisUtils redisUtils(@Autowired RedisConnectionFactory connectionFactory,
                                                  @Autowired ObjectMapper objectMapper){
        return new RedisUtils(
            RedisOptions.createValueOptions(connectionFactory,objectMapper,Object.class),
            RedisOptions.createValueOptions(connectionFactory,objectMapper,String.class),
            RedisOptions.createListOptions(connectionFactory));
    }
}