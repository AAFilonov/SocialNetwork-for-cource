package org.practical3.api;


import org.junit.jupiter.api.*;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.TestUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.practical3.utils.TestUtils.createRequestWall;

@Disabled
class PostServiceAPITest {
    @BeforeAll
    public  static  void init(){
        PropertyManager.load("./main.props");
    }


    @Test
    public void testGetPosts() throws Exception {
        PostsRequest postsRequest = new PostsRequest("988,989",10,0);

        Collection<Post> posts =  PostServiceAPI.getPosts(postsRequest);

        assertEquals(2,posts.size());
    }


    @Test
    public void testGetWall() throws Exception {
        WallRequest wallRequest = createRequestWall(405);

        Collection<Post> posts =  PostServiceAPI.getWall(wallRequest);

        assertEquals(2,posts.size());
    }
    @Test
    public void testInsertPosts() throws Exception {
        TestUtils.clearTestData();
        Collection<Post> posts = TestUtils.getTestPosts(888,889, 400);

        Answer answer = (Answer) PostServiceAPI.insertPosts(posts);

        assertNotNull(answer);


    }
    @Test
    public void testUpdatePosts() throws Exception {

        ArrayList<Post> posts = new ArrayList<>(
                Arrays.asList(
                        new Post(888, null, null, Instant.now()),
                        new Post(889, null, null, Instant.now()))
        );

        Answer answer = (Answer) PostServiceAPI.updatePosts(posts);

        assertNotNull(answer);

    }

    @Test
    public void testDeletePosts() throws Exception {

        Collection<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(888,889));

        Answer answer = (Answer) PostServiceAPI.deletePosts(ids);

        assertNotNull(answer);

    }





}
