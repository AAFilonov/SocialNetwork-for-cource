package org.practical3.api.main.postpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class getOwnerTests {




    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(1500, 101, "Post to test getowner")));

        TestUtils.createUsers(Arrays.asList(
                new User(101,"User101","Pass101")));
    }

    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(Collections.singleton(1500));
        TestUtils.cleanUsers("101");
    }

    @Test
    void getOwner_WhenOwnerExiist_ShouldReturnUser() throws IOException {
      HttpResponse response = MainServiceAPI.getOwner(1500);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
    @Test
    void getOwner_WhenOwnerExiist_ReturnedShouldMatch() throws IOException {
        HttpResponse response = MainServiceAPI.getOwner(1500);
        User actual = ResponseReader.getBodyAsObject(response, User.class);
        assertEquals("User101", actual.username);
    }

    @Test
    void getOwner_WhenPostNotExist_ShouldReturn404() throws IOException {
        HttpResponse response = MainServiceAPI.getOwner(1501);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }




}
