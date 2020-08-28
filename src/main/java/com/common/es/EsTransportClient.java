package com.common.es;

import com.common.util.GsonUtils;
import lombok.Data;
import org.elasticsearch.client.Client;

/**
 * EsTransportClient简介
 * TransportClient客户端
 * @author guozhenhua
 * @date 2020-08-28 14:46
 */

@Data
public class EsTransportClient<V> {

    private Client client;

    private String indexName;

    private String typeName;

    public EsTransportClient(Client client) {
        this.client = client;
    }
//
//    public void serIndexAndType(String indexName,String typeName){
//        indexName.set(indexName);
//        typeName.set(typeName);
//    }

    public void saveIndex(V indexBean, String id) throws Exception {
        client.prepareIndex(indexName, typeName, id).setSource(GsonUtils.toJSON(indexBean)).setRouting(id).execute().actionGet();
    }

}
