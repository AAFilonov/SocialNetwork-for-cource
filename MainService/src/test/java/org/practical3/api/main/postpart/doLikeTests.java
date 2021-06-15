package org.practical3.api.main.postpart;

import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class doLikeTests {


    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(201,701,"Post to check Like from MainAPI"),
                new Post(202,701,"Post to check Like from MainAPI")
        ));

    }
    @AfterAll
    public static void cleanup() {
        TestUtils.cleanPosts(Arrays.asList(201,202, 204));
    }

    @Disabled
    @Test
    @RepeatedTest(10)
    public void doLike_WhenPostExist_ShouldReturnTrue() throws IOException
    {
        String url = String.format("http://localhost:8026/posts/like");
        String params = String.format("?post_id=%s", "201");
        HttpResponse response = HttpClientManager.sendPost(url + params, null);

        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doLike_WhenPostNotExist_ShouldReturnFalse() throws IOException {

        String url = String.format("http://localhost:8026/posts/like");
        String params = String.format("?post_id=%s", "208");
        HttpResponse response = HttpClientManager.sendPost(url + params, null);

        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doLike_ShouldUpdateCountLikes() throws Exception {

        MainServiceAPI.doLike(202);
       ArrayList<Post> actual =  PostServiceAPI.getPosts(new PostsRequest("202"));
        assertEquals(1, actual.get(0).CountLikes);

    }

}
