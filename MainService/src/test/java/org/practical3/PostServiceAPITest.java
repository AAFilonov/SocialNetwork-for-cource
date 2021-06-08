package org.practical3;

import org.junit.jupiter.api.Test;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.TestUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.practical3.utils.TestUtils.createRequestWall;

class PostServiceAPITest {

    @Test
    public void testGetPosts() throws Exception {
        PostsRequest postsRequest = new PostsRequest("844,845",10,0);

        Collection posts =  PostServiceAPI.getPosts(postsRequest);

        assertEquals(2,posts.size());
    }


    @Test
    public void testGetWall() throws Exception {
        WallRequest wallRequest = createRequestWall(TestUtils.TestOwnerId);

        Collection posts =  PostServiceAPI.getWall(wallRequest);

        assertEquals(2,posts.size());
    }
    @Test
    public void testInsertPosts() throws Exception {

        Collection posts = TestUtils.getTestPosts(844,845);

        int affectedRows =  PostServiceAPI.insertPosts(posts);

        assertEquals(2,affectedRows);
    }
    @Test
    public void testUpdatePosts() throws Exception {

        ArrayList<Post> posts = new ArrayList<>(
                Arrays.asList(
                        new Post(888, null, null, Instant.now()),
                        new Post(889, null, null, Instant.now()))
        );
        int affectedRows =  PostServiceAPI.insertPosts(posts);

        assertEquals(2,affectedRows);
    }

    @Test
    public void testDeletePosts() throws Exception {

        Collection<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(844,845));
        int affectedRows =  PostServiceAPI.deletePosts(ids);

        assertEquals(2,affectedRows);
    }
}
