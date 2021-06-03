package org.practical3.handlers;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.practical3.Main;
import org.practical3.model.AnswerPosts;
import org.practical3.model.Post;
import org.practical3.model.RequestWall;
import org.practical3.utils.TestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WallServletTest {
    @Before
    public void init() throws Exception {
        Thread newThread = new Thread(() -> {
            Main.runServer(8027,"/");
        });
        newThread.start();

    }

    @Test
    void getWall() throws IOException {
        String url = "http://localhost:8027/wall/&action=getWall";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        RequestWall requestWall =  TestUtils.createRequestWall();

        ArrayList<Post> expected = new ArrayList<>(
                Arrays.asList(new Post(null, null, "First post")
                        , new Post(null, null, "Second post")
                        , new Post(null, null, "Post 3")
                        , new Post(null, null, "Post 4")
                )
        );
        Gson gson = new Gson();
        String jsonedRequest =gson.toJson(requestWall);
        StringEntity entity = new StringEntity( jsonedRequest);
        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);

        AnswerPosts a =  gson.fromJson(respStr, AnswerPosts.class);

        org.junit.Assert.assertEquals(a.Status, "OK");



    }
}