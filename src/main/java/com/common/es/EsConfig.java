package com.common.es;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnClass(RedisUtils.class)
@ConditionalOnProperty(prefix = "es", value = "enable", matchIfMissing = true)
public class EsConfig {
}
