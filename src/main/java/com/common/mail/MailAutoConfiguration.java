package com.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = MailProperties.class)
@ConditionalOnClass(MailUtils.class)
@ConditionalOnProperty(prefix = "mail", value = "enable", matchIfMissing = true)
public class MailAutoConfiguration {

  @Autowired
  private MailProperties mailProperties;

  @Bean
  public MailUtils mailUtils() {
    return new MailUtils(mailProperties);
  }


}
