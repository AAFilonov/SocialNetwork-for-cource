package com.github.michael_sharko.handlers;

import com.github.michael_sharko.Main;
import com.github.michael_sharko.models.Answer;
import com.github.michael_sharko.models.data.User;
import com.github.michael_sharko.utils.PropertyManager;
import com.github.michael_sharko.utils.StaticGson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.*;

import java.net.URI;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceServletTest {
    HttpClient client = HttpClientBuilder.create().build();

    @BeforeAll
    static void start() throws ClassNotFoundException {
        PropertyManager.load("./main.props");
        Main.runServer();
    }

    @AfterAll
    static void finish() {
        Main.stopServer();
    }

    @Order(1)
    @Test
    public void doPost() throws Exception {
        String url = "http://localhost:8080/users";
        // HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        User req = new User();

        req.username = "java_username";
        req.password = "java_password";

        StringEntity entity = new StringEntity(StaticGson.gson.toJson(req));
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        HttpEntity resp = response.getEntity();
        String respStr = IOUtils.toString(resp.getContent());

        Answer answer = StaticGson.gson.fromJson(respStr, Answer.class);
        System.out.println(answer.getStatus());
        System.out.println(answer.getData());
    }

    @Order(2)
    @Test
    public void doPut() throws Exception {
    }

    @Order(3)
    @Test
    public void doGet() throws Exception {
        String url = "http://localhost:8080/users?user_ids=red,white,black,4,java_username";
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);
        HttpEntity resp = response.getEntity();
        String respStr = IOUtils.toString(resp.getContent());

        Answer answer = StaticGson.gson.fromJson(respStr, Answer.class);
        System.out.println(answer.getStatus());
        System.out.println(answer.getData());
    }

    @Order(4)
    @Test
    public void doDelete() throws Exception {
        User req = new User();
        req.username = "java_username";
        req.password = "java_password";

        String url = "http://localhost:8080/users";

        HttpEntity entity = new StringEntity(StaticGson.gson.toJson(req));
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
        httpDeleteWithBody.setEntity(entity);

        HttpResponse response = client.execute(httpDeleteWithBody);
        HttpEntity resp = response.getEntity();
        String respStr = IOUtils.toString(resp.getContent());

        Answer answer = StaticGson.gson.fromJson(respStr, Answer.class);
        System.out.println(answer.getStatus());
        System.out.println(answer.getData());
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