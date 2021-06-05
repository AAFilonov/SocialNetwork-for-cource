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
import org.junit.Test;

import org.junit.jupiter.api.Disabled;
import org.practical3.Main;
import org.practical3.model.Post;
import org.practical3.model.Answer;
import org.practical3.utils.TestUtils;

import java.util.Collection;


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


    @Disabled
    @Test
    public void InsertAPItest() throws Exception
    {
        String url = "http://localhost:8027/posts?action";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        Collection<Post> inserted  = TestUtils.getTestPosts();
        Gson gson = new Gson();
        StringEntity entity = new StringEntity( gson.toJson(inserted));
        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);

        Answer a =  gson.fromJson(respStr, Answer.class);

        org.junit.Assert.assertEquals("OK",a.Status);
    }


}