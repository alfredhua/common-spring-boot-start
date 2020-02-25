package com.common.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@ConditionalOnClass(RedisUtils.class)
@ConditionalOnProperty(prefix = "spring.redis", value = "enable", matchIfMissing = true)
public class RedisConfig {

    @Bean
    public RedisUtils redisUtils(@Autowired RedisConnectionFactory connectionFactory,
                                 @Autowired ObjectMapper objectMapper){
        return new RedisUtils(
                RedisOptions.createValueOptions(connectionFactory,objectMapper,Object.class),
                RedisOptions.createValueOptions(connectionFactory,objectMapper,String.class),
                RedisOptions.createListOptions(connectionFactory));
    }
}