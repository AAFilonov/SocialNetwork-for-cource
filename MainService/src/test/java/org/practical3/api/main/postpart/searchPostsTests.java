package org.practical3.api.main.postpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class searchPostsTests {


    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(1601,1602,1603));
    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(1601, 1401, "Sanya inserted a text here"),
                new Post(1602, 1401, "Goat goes here"),
                new Post(1603, 1400, "goat  inserted a text")));
        TestUtils.createUsers(Arrays.asList(new User(1401,"User1401","Pass1401")));
    }

    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(postsToClean);
        TestUtils.cleanUsers("1401");
    }

    @Test
    void search_WhenOwnerNull_ShouldReturnPosts() throws IOException {
        ArrayList<Post> actual = (ArrayList<Post>) MainServiceAPI.searchPost(new SearchPostRequest("goat"));
        assertEquals(2, actual.size());
    }

    @Test
    void search_WhenUserIdNotNull_ShouldReturnPosts() throws IOException {
        ArrayList<Post> actual = (ArrayList<Post>) MainServiceAPI.searchPost(new SearchPostRequest("goat", 1401, null));
        assertEquals(1, actual.size());
    }
    @Test
    void search_WhenUsernameNotNull_ShouldReturnPosts() throws IOException {
        ArrayList<Post> actual = (ArrayList<Post>) MainServiceAPI.searchPost(new SearchPostRequest("goat", null, "User1401"));
        assertEquals(1, actual.size());
    }

    @Test
    void search_WhenOwnerNotReal_ShouldReturnAnswer() throws  IOException {
        Object actual = MainServiceAPI.searchPost(new SearchPostRequest("goat",null,"NoRealUser"));
        assertEquals(Answer.class ,actual.getClass());
    }
}
