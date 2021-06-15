package com.github.michael_sharko.utils;

import com.github.michael_sharko.models.Answer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class HttpManagerForUserService {
    private static final HttpClient client = HttpClientBuilder.create().build();

    public static HttpResponse sendPost(String url, Object data) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(StaticGson.toJson(data));
        request.setEntity(entity);
        return client.execute(request);
    }

    public static HttpResponse sendPut(String url, Object data) throws IOException {
        HttpPut request = new HttpPut(url);
        StringEntity entity = new StringEntity(StaticGson.toJson(data));
        request.setEntity(entity);
        return client.execute(request);
    }

    public static HttpResponse sendGet(String url, String parameters) throws IOException {
        HttpGet request = new HttpGet(url + "?" + parameters);
        return client.execute(request);
    }

    public static HttpResponse sendDelete(String url, Object data) throws IOException {
        HttpDeleteWithBody request = new HttpDeleteWithBody(url);
        HttpEntity entity = new StringEntity(StaticGson.toJson(data));
        request.setEntity(entity);
        return client.execute(request);
    }

    public static Answer readResponse(HttpResponse response) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return StaticGson.fromJson(respStr, Answer.class);
    }

    static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {
            super();
        }

        public String getMethod() {
            return METHOD_NAME;
        }
    }
}
