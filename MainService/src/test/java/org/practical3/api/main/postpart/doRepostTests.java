package org.practical3.api.main.postpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class doRepostTests {
    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(211,212,213));

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(211,701,"Post to check repost from MainAPI"),
                new Post(212,701,"Post to check repost from MainAPI"),
                new Post(213,701,"Post to check repost from MainAPI")
        ));
        TestUtils.createUser(new User(702,"User702","Pass702"));
    }
    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(postsToClean);
        TestUtils.cleanUser(702);
    }

    @Test
    public void doRepost_WhenPostExist_ShouldReturnCreatedPost() throws IOException {
        ArrayList<Post> returned =  (ArrayList<Post> )MainServiceAPI.doRepost("User702",211);

        assertEquals(1, returned.size());
        postsToClean.add(returned.get(0).PostId);
    }

    @Test
    public void doRepost_WhenPostNotExist_ShouldReturnAnswer() throws IOException {
        Object returned =  MainServiceAPI.doRepost("User702",215);
        assertEquals(Answer.class, returned.getClass());
    }

    @Test
    public void doRepost_ShouldUpdateCountRepost() throws Exception {

        ArrayList<Post> returned = (ArrayList<Post>) MainServiceAPI.doRepost("User702",212);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(
                new PostsRequest("212"));
        postsToClean.add(returned.get(0).PostId);
        assertEquals(1, actual.get(0).CountReposts);


    }
    @Test
    public void doRepost_ShouldCreateNewPost() throws Exception {

        ArrayList<Post> returned =(ArrayList<Post>) MainServiceAPI.doRepost("User702",213);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(
                new PostsRequest(returned.get(0).PostId.toString()));

        postsToClean.add(returned.get(0).PostId);
        assertNotNull(returned.get(0).Content,actual.get(0).Content );


    }

}
