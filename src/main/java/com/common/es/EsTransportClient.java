package com.common.es;

import com.common.response.PageBean;
import com.common.util.GsonUtils;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;

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

    public void saveIndex(EsSettingName settingName,String id,T indexBean) {
        client.prepareIndex(settingName.getIndexName(),settingName.getTypeName(),id)
                .setSource(GsonUtils.toJSON(indexBean))
                .setRouting(id).execute().actionGet();
    }

    public PageBean<T> getIndexes(T searchBean, Class<T> vClass, int pageSize, int page, EsSettingName settingName) throws Exception {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(settingName.getIndexName())
                .setTypes(settingName.getTypeName()).setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setFrom(pageSize * (page - 1)).setSize(pageSize).setExplain(true);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        PageBean<T> indexBeans = new PageBean<T>();
        indexBeans.setList(new ArrayList<T>(pageSize));
        for (SearchHit hit : response.getHits()) {
            indexBeans.getList().add(GsonUtils.gson.fromJson(hit.getSourceAsString(), vClass));
        }
        indexBeans.setPage_num(page);
        indexBeans.setPage_size(pageSize);
        indexBeans.setTotal(response.getHits().getTotalHits());
        return indexBeans;
    }

    public T getByIndex(String id, Class<T> vClass, EsSettingName settingName) {
        GetResponse response = client.prepareGet(settingName.getIndexName(), settingName.getTypeName(), id).setRouting(id).execute().actionGet(1500);
        T indexBean = null;
        if (response != null && StringUtils.isNotBlank(response.getSourceAsString())) {
            indexBean = GsonUtils.gson.fromJson(response.getSourceAsString(), vClass);
        }
        return indexBean;
    }


}
