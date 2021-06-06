package org.practical3.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.practical3.model.transfer.Answer;

import java.io.IOException;

public class HttpClientManager {
    public HttpClient httpClient ;
    HttpClientManager(){
        httpClient =  HttpClientBuilder.create().build();
    }

    public HttpResponse  sendPost(String url, Object body) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity( Commons.gson.toJson(body));
        request.setEntity(entity);
        return httpClient.execute(request);
    }


    public  static Answer getResponseBody(HttpResponse response) throws IOException {
        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return Commons.gson.fromJson(respStr, Answer.class);
    }

}
