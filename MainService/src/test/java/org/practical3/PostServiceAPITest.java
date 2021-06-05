package org.practical3;

import org.junit.jupiter.api.Test;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.TestUtils;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceAPITest {

    @Test
    public void testGetPosts() throws Exception {
        PostsRequest postsRequest = new PostsRequest("888,889",0,10);

        Collection posts =  PostServiceAPI.getPosts(postsRequest);

        assertEquals(2,posts.size());
    }
}