package com.github.michael_sharko;

import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.User;
import com.github.michael_sharko.utils.PropertyManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.NotImplementedException;
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
import java.util.Collection;

public class UserServiceAPI {
    private static class HttpManager {
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

        static GsonBuilder builder = new GsonBuilder();
        static Gson gson = builder.create();

        static HttpClient client = HttpClientBuilder.create().build();
        static URI uri = PropertyManager.getURI();

        static HttpResponse sendPost(Collection<User> data) throws IOException {
            HttpPost request = new HttpPost(uri);
            StringEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        static HttpResponse sendPut(Collection<User> data) throws IOException {
            HttpPut request = new HttpPut(uri);
            StringEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        static HttpResponse sendGet(String parameters) throws IOException {
            HttpGet request = new HttpGet(uri + parameters);
            return client.execute(request);
        }

        static HttpResponse sendDelete(Object data) throws IOException {
            HttpDeleteWithBody request = new HttpDeleteWithBody(uri);
            HttpEntity entity = new StringEntity(gson.toJson(data));
            request.setEntity(entity);
            return client.execute(request);
        }

        public static Answer readResponse(HttpResponse response) throws IOException {
            HttpEntity resp  = response.getEntity();
            String respStr = EntityUtils.toString(resp);
            return HttpManager.gson.fromJson(respStr, Answer.class);
        }
    }

    public static void register(Collection<User> users) throws IOException {
        HttpManager.sendPost(users);
    }

    public static void update(Collection<User> users) throws IOException {
        HttpManager.sendPut(users);
    }

    public static Collection<User> getUsers(String parametres) throws IOException {
        HttpResponse response = HttpManager.sendGet(parametres);
        return (Collection<User>) HttpManager.readResponse(response).getData();
    }

    public static void delete(Collection<User> users) throws IOException {
        HttpManager.sendDelete(users);
    }

    public static Collection<Integer> getSubscriptions(String username) throws IOException {
        throw new NotImplementedException();
    }

    public static Collection<Integer> getFollowers(String username) throws IOException {
        throw new NotImplementedException();
    }
}
