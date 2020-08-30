package com.common.es;

import com.common.response.PageBean;
import com.common.util.GsonUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
                .setSource(GsonUtils.toJSON(indexBean),XContentType.JSON)
                .setRouting(id).execute().actionGet();
    }


    public  void deleteIndex(EsSettingName settingName,String id){
        client.prepareDelete(settingName.getIndexName(),settingName.getTypeName(),id).get();
    }

    public PageBean<T> getIndexes(T searchBean, Class<T> vClass, int pageSize, int page, EsSettingName settingName) throws IllegalAccessException {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(settingName.getIndexName())
                .setTypes(settingName.getTypeName()).setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setFrom(pageSize * (page - 1)).setSize(pageSize).setExplain(true);
        Field[] fields = searchBean.getClass().getFields();
        for (int i=0;i<fields.length;i++){
            //如果searchbean上包含某些注解，则进行搜索
            searchRequestBuilder.setQuery(QueryBuilders.matchQuery(fields[i].getName(),fields[i].get(fields[i].getName())));
        }

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        PageBean<T> indexBeans = new PageBean<>();
        indexBeans.setList(new ArrayList<>(pageSize));
        for (SearchHit hit : response.getHits()) {
            indexBeans.getList().add(GsonUtils.gson.fromJson(hit.getSourceAsString(), vClass));
        }
        indexBeans.setPage_num(page);
        indexBeans.setPage_size(pageSize);
        indexBeans.setTotal(response.getHits().getTotalHits());
        return indexBeans;
    }



    public List<T> searchAll(QueryBuilder queryBuilder,EsSettingName settingName, Class<T> vClass){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(settingName.getIndexName())
                .setTypes(settingName.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequestBuilder.setQuery(queryBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        ArrayList<T> objects = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            objects.add(GsonUtils.gson.fromJson(hit.getSourceAsString(), vClass));
        }
        return objects;
    }

    public Client getClient(){
        return client;
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
