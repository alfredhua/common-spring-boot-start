package com.common.es;

import lombok.Data;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;

@Data
@Configuration
@ConditionalOnClass(EsTransportClient.class)
@ConditionalOnProperty(prefix = "es", value = "enable", matchIfMissing = true)
public class EsConfig {

    public String clusterName;

    public String ips;

    public boolean cluster;

    @Bean
    @ConditionalOnProperty(value = "es")
    public EsTransportClient createClient() throws IOException {
        TransportClient client=null;
        if (cluster){
            //集群模式，配置机器名称
            Settings settings= Settings.builder().put("cluster.name",clusterName).put("client.transport.sniff", false).build();
            client = new PreBuiltTransportClient(settings);
        }else{
            //非集群运行
            client = new PreBuiltTransportClient(Settings.EMPTY);
        }
        String[] ipPorts = ips.split(",");
        for (String ipPort : ipPorts) {
            String[] ipPortArray = StringUtils.split(ipPort, ":");
            client.addTransportAddress(
                    new InetSocketTransportAddress(InetAddress.getByName(ipPortArray[0]), Integer.parseInt(ipPortArray[1])));
        }
        return new EsTransportClient(client);
    }

}
