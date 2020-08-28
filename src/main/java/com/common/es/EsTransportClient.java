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

public class EsTransportClient<T> {

    private Client client;

    public EsTransportClient(Client client) {
        this.client = client;
    }

    public void saveIndex(EsSettingName settingName,String id,T indexBean) throws Exception {
        client.prepareIndex(settingName.getIndexName(),settingName.getTypeName(),id)
                .setSource(GsonUtils.toJSON(indexBean))
                .setRouting(id).execute().actionGet();
    }


}
