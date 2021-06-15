package org.practical3.api.main.postpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.SubscriptionRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class getFeedTests {

    @BeforeAll
    public static void init() throws IOException {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(1301, 301, "Post to test getfeed"),
                new Post(1302, 301, "Post to test getfeed")));

        TestUtils.createUsers(Arrays.asList(
                new User(301, "User301", "Pass101"),
                new User(302, "User302", "Pass101")));
        MainServiceAPI.subscribeUser(new SubscriptionRequest(302,301));
    }

    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(Arrays.asList(1301,1302));
        TestUtils.cleanUsers("301,302");
    }

    @Test
    void getFeedShouldReturnPosts() throws IOException {
        String url = String.format("http://localhost:8026/feed/");
        WallRequest wallrequest = TestUtils.createRequestWall(302);

        String params = String.format("?user_login=%s&before=%s&after=%s&&count=%s&offset=%s", "User302",
                wallrequest.Before.toString(),
                wallrequest.After.toString(),
                "10",
                "0"
        );

        HttpResponse response = HttpClientManager.sendGet(url, params);


        assertFalse(ResponseReader.getPostsCollection(response).isEmpty());

    }
    @Test
    void getFeedShouldReturn200() throws IOException {
        String url = String.format("http://localhost:8026/feed/");
        WallRequest wallrequest = TestUtils.createRequestWall(101);

        String params = String.format("?user_login=%s&before=%s&after=%s&&count=%s&offset=%s", "User302",
                wallrequest.Before.toString(),
                wallrequest.After.toString(),
                "10",
                "0"
        );
        HttpResponse response = HttpClientManager.sendGet(url, params);
        assertEquals(200, response.getStatusLine().getStatusCode());



    }
}
