package org.practical3.utils.http;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.StaticGson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class HttpClientManager {
    public static  HttpClient httpClient = HttpClientBuilder.create().build();;



    public static HttpResponse sendGet(String url, String parameters) throws IOException {
        HttpGet request = new HttpGet(url + parameters);
        return httpClient.execute(request);
    }

    public static HttpResponse sendPost(String url, Object data) throws IOException {
        HttpPost request = new HttpPost(url);
        setBody(request, data);
        return httpClient.execute(request);
    }


    public static HttpResponse sendPut(String url, Object data) throws IOException {
        HttpPut request = new HttpPut(url);
        setBody(request, data);
        return httpClient.execute(request);
    }


    public static HttpResponse sendDelete(String url, Object data) throws IOException {
        HttpDeleteWithBody request = new HttpDeleteWithBody(url);
        setBody(request, data);
        return httpClient.execute(request);
    }

    private static void setBody(HttpEntityEnclosingRequestBase request, Object data) throws UnsupportedEncodingException {
        String jsonBody = StaticGson.toJson(data);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
    }

    public static void sendOk(Object a, HttpServletResponse resp) throws Exception {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(StaticGson.toJson( a));
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
