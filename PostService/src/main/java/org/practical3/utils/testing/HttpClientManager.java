package org.practical3.utils.testing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.Commons;

import java.io.IOException;

public class HttpClientManager {
    public  static HttpClient httpClient = HttpClientBuilder.create().build();


    public static HttpResponse sendPost(String url, Object body) throws IOException {
        HttpPost request = new HttpPost(url);
        String jsonBody = Commons.toJson(body);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        return httpClient.execute(request);
    }

    public static Answer getResponseBody(HttpResponse response) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return Commons.fromJson(respStr, Answer.class);
    }

}
