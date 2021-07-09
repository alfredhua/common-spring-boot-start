package com.common.es.resthigh;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author guozhenhua
 * @date 2021/02/19
 */
@Component
public class RestHighClient<T> {

    @Autowired
    RestHighLevelClient client;

    public boolean createIndex(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }


    public GetIndexResponse getIndex(String indexName) throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest(indexName);
        GetIndexResponse response = client.indices().get(getRequest, RequestOptions.DEFAULT);
        return response;
    }


    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }



    public IndexResponse index(T t,String id,String indexName) throws IOException {
        IndexRequest indexRequest=new IndexRequest();
        indexRequest.index(indexName).id(id);
        ObjectMapper mapper=new ObjectMapper();
        String entity = mapper.writeValueAsString(t);
        indexRequest.source(entity, XContentType.JSON);
        return client.index(indexRequest, RequestOptions.DEFAULT);
    }


    public UpdateResponse update(String id, String indexName) throws IOException {
        UpdateRequest updateRequest=new UpdateRequest();
        updateRequest.index(indexName).id(id);
        updateRequest.doc(XContentType.JSON,"sex",0);
        return client.update(updateRequest,RequestOptions.DEFAULT);
    }


    public String get(String id, String indexName) throws IOException {
        GetRequest getRequest=new GetRequest();
        getRequest.index(indexName).id(id);
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        return documentFields.getSourceAsString();
    }


    public DeleteResponse delete(String id, String indexName) throws IOException {
        DeleteRequest deleteRequest=new DeleteRequest();
        deleteRequest.index(indexName).id(id);
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return delete;
    }


    public void search(String indexName) throws IOException {
        SearchRequest searchRequest=new SearchRequest();
        searchRequest.indices(indexName);
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.boolQuery()));
        client.search(searchRequest,RequestOptions.DEFAULT);
    }


    public BulkResponse batchIndex() throws IOException {
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON,"name","张三"));
        bulkRequest.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON,"name","张三"));
        bulkRequest.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON,"name","张三"));
        bulkRequest.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON,"name","张三"));
        return  client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    public BulkResponse batchDelete() throws IOException {
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.add(new DeleteRequest().index("user").id("1001"));
        bulkRequest.add(new DeleteRequest().index("user").id("1002"));
        bulkRequest.add(new DeleteRequest().index("user").id("1003"));
        bulkRequest.add(new DeleteRequest().index("user").id("1004"));
        return  client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

}
