package com.common.es.tranport;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "es")
public class EsProperties {

  /**
   * 集群名称
   */
  public String cluster_name;

  /**
   * ip地址
   */
  public String ips;

  /**
   * 是否开启集群
   */
  public boolean cluster;

}
