package com.common.es;

import com.common.redis.RedisUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RedisUtils.class)
@ConditionalOnProperty(prefix = "es", value = "enable", matchIfMissing = true)
public class EsConfig {
}
