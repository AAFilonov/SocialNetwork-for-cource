package org.practical3.api.main.postpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();


    }

    @Test
    public void getPostReturnNotHashMap() throws IOException {

        ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
        assertEquals(405, posts.get(0).OwnerId);

    }

    @Test
    public void getPostReturnElements() throws IOException {

        ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
        assertEquals(2, posts.size());

    }
    @Test
    public void getPost_WhenPostNotExist_ShouldReturn404() throws IOException {

        String url = String.format("http://localhost:8026/posts/");
        String params = String.format("?post_ids=%s&count=%s&offset=%s", "999", "10", "0");
        HttpResponse response = HttpClientManager.sendGet(url, params);


        assertEquals(404, response.getStatusLine().getStatusCode());

    }


}
