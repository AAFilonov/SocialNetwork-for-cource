package org.practical3.api.main.userpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class registerTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();


    }



    @AfterAll
    public static void cleanup() {
        TestUtils.cleanUsers("451,452,453");
    }

    @Test
    public void register_WhenUserNotExist_ShouldCreateUser() throws IOException {
        User user = new User(451, "User451", "User from register tests");
        String url = String.format("http://localhost:8026/users/");

        HttpClientManager.sendPost(url, user);

        ArrayList<User> actualUser = (ArrayList<User>) MainServiceAPI.getUsers("451");
        assertEquals(user.username, actualUser.get(0).username);

    }




    @Test
    public void register_WhenNewUser_ShouldReturn200() throws IOException {
        User user = new User(452, "User452", "User from register tests");
        String url = String.format("http://localhost:8026/users/");

       HttpResponse response =  HttpClientManager.sendPost(url, user);
       assertEquals(200, response.getStatusLine().getStatusCode());

    }

    @Test
    public void register_WhenUserExist_ShouldReturn400() throws IOException {
        User user = new User(453, "User453", "User from register tests");
        String url = String.format("http://localhost:8026/users/");

        HttpClientManager.sendPost(url, user);
        HttpResponse response =  HttpClientManager.sendPost(url, user);

        assertEquals(400, response.getStatusLine().getStatusCode());

    }


}
