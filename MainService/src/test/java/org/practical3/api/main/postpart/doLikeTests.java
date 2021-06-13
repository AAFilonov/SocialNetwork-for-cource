package org.practical3.api.main.postpart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.TestUtils;
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
    @BeforeAll
    public static void cleanup() {
        TestUtils.cleanData(Arrays.asList(201,202));
    }

    @Test
    public void doLike_WhenPostExist_ShouldReturnTrue() throws IOException {
        assertTrue(MainServiceAPI.doLike(201));
    }

    @Test
    public void doLike_WhenPostNotExist_ShouldReturnFalse() throws IOException {

        assertFalse(MainServiceAPI.doLike(203));
    }

    @Test
    public void doLike_ShouldUpdateCountLikes() throws Exception {

        MainServiceAPI.doLike(202);
       ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("202"));
        assertEquals(1, actual.get(0).CountLikes);

    }

}
