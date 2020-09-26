package com.common.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.net.InetAddress;

@Configuration
@ConditionalOnClass(Client.class)
@ConditionalOnProperty(prefix = "es", value = "enable")
@EnableConfigurationProperties(value = EsProperties.class)
public class EsConfig {

    @Autowired
    EsProperties esProperties;

    @Bean
    public EsTransportClient esTransportClient() throws IOException {
        TransportClient client=null;
        if (esProperties.isCluster()){
            //集群模式，配置机器名称
            Settings settings= Settings.builder().put("cluster.name",esProperties.getCluster_name()).put("client.transport.sniff", false).build();
            client = new PreBuiltTransportClient(settings);
        }else{
            //非集群运行
            client = new PreBuiltTransportClient(Settings.EMPTY);
        }
        System.out.println("-----------init---------------");
        String[] ipPorts = esProperties.getIps().split(",");
        for (String ipPort : ipPorts) {
            String[] ipPortArray = StringUtils.split(ipPort, ":");
            client.addTransportAddress(
                    new TransportAddress(InetAddress.getByName(ipPortArray[0]), Integer.parseInt(ipPortArray[1])));
        }
        return new EsTransportClient(client);
    }

}
