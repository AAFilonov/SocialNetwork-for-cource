package org.practical3.api.post;

import org.junit.jupiter.api.*;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.practical3.utils.TestUtils.createRequestWall;

class softdeleteTests {
    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(691,692,693,694));
    @BeforeAll
    static void init() {
        TestUtils.createPosts(Arrays.asList(
                new Post(691,791,"Post to check Like from softDelete PostAPI"),
                new Post(692,791,"Post to check Like from softDelete PostAPI"),
                new Post(693,792,"Post to check Like from softDelete PostAPI")

        ));
    }

    @AfterAll
    static void cleanup() throws Exception {
        TestUtils.cleanPosts(postsToClean);
    }

    @Test
    public void doRemovePost_whenPostExist_ThenPostIsRemovedTrue() throws Exception {

        Collection<Integer> ids = Arrays.asList(691);
        PostServiceAPI.removePosts(ids);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("691", 1, 0));

        assertEquals(true, actual.get(0).IsRemoved);
        PostServiceAPI.restorePosts(ids);
    }

    @Test
    public void doRestorePost_whenPostIsRemoveTrue_ThenPostIsRemovedBecameFalse() throws Exception {

        Collection<Integer> ids = Arrays.asList(692);
        PostServiceAPI.removePosts(ids);

        PostServiceAPI.restorePosts(ids);
        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("692", 1, 0));

        assertEquals(false, actual.get(0).IsRemoved);
    }


}