package com.common.es.resthigh;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guozhenhua
 * @date 2021/02/19
 */

@Configuration
public class RestHighClientConfig {

    @Bean
    public RestHighLevelClient RestHighClientConfig(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.0.105", 9200, "http")));
        return client;
    }



}
