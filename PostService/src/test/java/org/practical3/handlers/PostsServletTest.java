package org.practical3.handlers;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import org.practical3.Main;
import org.practical3.model.Post;
import org.practical3.model.AnswerPosts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class PostsServletTest {
    //TODO сделать тестовую базу и тесты вокруг нее
    @Before
    public void init() throws Exception {
        Thread newThread = new Thread(() -> {
            try {
                Main.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newThread.start();

    }


    @Test
    public void doGet() throws Exception
    {
        String url = "http://localhost:8027/posts?post_ids=7,8&fields=post_id,owner_id,content&count=10&offset=0";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        org.junit.Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doIsert() throws Exception
    {
        String url = "http://localhost:8027/posts?post_ids=1,2&fields=post_id,owner_id,content&count=10&offset=0";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        ArrayList<Post> inserted = new ArrayList<>(
                Arrays.asList(
                        new Post(null,0,"Post 3", new Date(System.currentTimeMillis())),
                        new Post(null,0,"Post 4",new Date(System.currentTimeMillis())))
        );
        Gson gson = new Gson();
        StringEntity entity = new StringEntity( gson.toJson(inserted));
        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);

        AnswerPosts a =  gson.fromJson(respStr, AnswerPosts.class);

        org.junit.Assert.assertEquals(a.Status, "OK");
    }


}