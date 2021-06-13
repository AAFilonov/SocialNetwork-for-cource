package org.practical3.api.main.postpart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class doRepostTests {


    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(211,701,"Post to check repost from MainAPI"),
                new Post(212,701,"Post to check repost from MainAPI")
        ));

    }
    @BeforeAll
    public static void cleanup() {
        TestUtils.cleanData(Arrays.asList(211,212));
    }

    @Test
    public void doLike_WhenPostExist_ShouldReturnTrue() throws IOException {
        MainServiceAPI.doRepost("User702",211);
    }

    @Test
    public void doLike_WhenPostNotExist_ShouldReturnFalse() throws IOException {

        MainServiceAPI.doRepost("User702",213);
    }

    @Test
    public void doLike_ShouldUpdateCountRepost() throws Exception {

        MainServiceAPI.doRepost("User702",212);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("202"));
        assertEquals(1, actual.get(0).CountReposts);

    }
    @Test
    public void doLike_ShouldCreateNewPost() throws Exception {

        MainServiceAPI.doLike(212);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getWall(new WallRequest("702"));
        assertNotNull(actual);

    }

}
