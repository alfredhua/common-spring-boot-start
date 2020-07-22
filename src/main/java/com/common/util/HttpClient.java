package com.common.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author guozhenhua
 * @date 2019/01/14
 */
public class HttpClient {


    static  String defaultContentType="application/json; charset=utf-8";


    public static String get(String url, java.util.Map<String,String> parms)throws  Exception {
        String urlparms="";
        for (Map.Entry<String, String> map:parms.entrySet()) {
            urlparms=urlparms + map.getKey() + "=" + map.getValue() + "&";
        }
        if(!StringUtils.isEmpty(urlparms)){
            url=url+"?"+urlparms.substring(0,urlparms.length()-1);
        }
        HttpGet request = new HttpGet(url);//这里发送get请求
        org.apache.http.client.HttpClient httpClient= HttpClients.createDefault();
        HttpResponse response = httpClient.execute(request);
        String result=null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        return result;
    }

    public static String post(String url,  Map<String,String> paramsMap,String contentType)throws Exception {
        final HttpClientConnectionManager hccm = createConnManager();
        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(hccm).build();
        HttpPost requestPost=new HttpPost(url);
       if (StringUtils.isEmpty(contentType)){
            contentType=defaultContentType;
        }
        requestPost.addHeader("Content-type",contentType);
        requestPost.setHeader("Accept", "application/json");
        requestPost.setEntity(new StringEntity(GsonUtils.toJSON(paramsMap),"UTF-8"));
        HttpResponse response = httpClient.execute(requestPost);
        String result= EntityUtils.toString(response.getEntity(),"utf-8"); //body
        return result;
    }

    private static HttpClientConnectionManager createConnManager() throws KeyManagementException, NoSuchAlgorithmException {
        PoolingHttpClientConnectionManager connManager = null;
        final SSLContext sslc = createIgnoreVerifySSL();
        final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslc, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslsf).build();
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        return connManager;
    }

    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new javax.net.ssl.TrustManager[]{trustManager}, null);
        return sc;
    }



}
