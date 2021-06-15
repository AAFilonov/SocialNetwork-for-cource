package org.practical3.api.main.userpart;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.User;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class getTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        Collection<User> users = Arrays.asList(
                new User(471, "User471", "User from getuser tests"),
                new User(472, "User472", "User from getuser tests")


        );
        TestUtils.createUsers(users);

    }

    @AfterAll
    public static void cleanup() {
        TestUtils.cleanUsers("471,472");
    }

    @Test
    public void getUser__ShouldReturn200() throws IOException {
        String url = String.format("http://localhost:8026/users/");
        String params = String.format("?user_ids=%s", "471,472");

        HttpResponse response = HttpClientManager.sendGet(url, params);

        assertEquals(200, response.getStatusLine().getStatusCode());

    }

    @Test
    public void getUser_WhenGetMultiple_ShouldMultiple() throws IOException {
        String url = String.format("http://localhost:8026/users/");
        String params = String.format("?user_ids=%s", "471,472");
        HttpResponse response = HttpClientManager.sendGet(url, params);
        Collection<User> actual = ResponseReader.getUsersCollection(response);

        assertEquals(2, actual.size());

    }

    @Test
    public void getUser_WhenGetSingle_ShouldMatchAndBeSingle() throws IOException {


        String url = String.format("http://localhost:8026/users/");
        String params = String.format("?user_ids=%s", "471");
        HttpResponse response = HttpClientManager.sendGet(url, params);
        ArrayList<User> actual = ( ArrayList<User> )ResponseReader.getUsersCollection(response);

        assertEquals(1, actual.size());
        assertEquals("User471", actual.get(0).username);

    }


    @Test
    public void getUser_WhenUserNotExist_ShouldReturn404() throws IOException {
        String url = String.format("http://localhost:8026/users/");
        String params = String.format("?user_ids=%s", "478");
        HttpResponse response = HttpClientManager.sendGet(url, params);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }


}
