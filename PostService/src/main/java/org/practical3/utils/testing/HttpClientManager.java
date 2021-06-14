package org.practical3.utils.testing;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.Commons;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static org.practical3.utils.StaticGson.fromJson;
import static org.practical3.utils.StaticGson.toJson;

public class HttpClientManager {
    public  static HttpClient httpClient = HttpClientBuilder.create().build();


    public static HttpResponse sendPost(String url, Object body) throws IOException {
        HttpPost request = new HttpPost(url);
        String jsonBody = toJson(body);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        return httpClient.execute(request);
    }

    public static Answer getResponseBody(HttpResponse response) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return fromJson(respStr, Answer.class);
    }

    public static<T> Collection<T>  getResponseBodyAsCollection(HttpResponse response, Type userListType) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return  fromJson(respStr, userListType);
    }


    public static Collection<Post>  getPostsCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
       return HttpClientManager.getResponseBodyAsCollection(response, userListType);
    }
    public static Collection<Post>  getIntegerCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return HttpClientManager.getResponseBodyAsCollection(response, userListType);
    }



}
