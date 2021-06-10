package org.practical3;

import org.junit.jupiter.api.*;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.model.transfer.requests.WallRequest;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.TestUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.practical3.utils.TestUtils.createRequestWall;

class PostServiceAPITest {
    @BeforeAll
    public  static  void init(){
        PropertyManager.load("./main.props");
    }
    @BeforeEach
    void beforeEach() {
        TestUtils.insertTestData();
    }

    @AfterEach
    void afterEach() {
        TestUtils.clearTestData();
    }

    @Test
    public void testGetPosts() throws Exception {
        PostsRequest postsRequest = new PostsRequest("988,989",10,0);

        Collection posts =  PostServiceAPI.getPosts(postsRequest);

        assertEquals(2,posts.size());
    }


    @Test
    public void testGetWall() throws Exception {
        WallRequest wallRequest = createRequestWall(405);

        Collection posts =  PostServiceAPI.getWall(wallRequest);

        assertEquals(2,posts.size());
    }
    @Test
    public void testInsertPosts() throws Exception {
        TestUtils.clearTestData();
        Collection posts = TestUtils.getTestPosts(888,889);

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
        int affectedRows =  PostServiceAPI.updatePost(posts);

        assertEquals(2,affectedRows);

    }

    @Test
    public void testDeletePosts() throws Exception {

        Collection<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(888,889));

         int affectedRows =  PostServiceAPI.deletePosts(ids);

        assertEquals(2,affectedRows);

    }

    @Disabled
    @Nested
    class softDeleteTests {

        @BeforeEach
        void beforeEach() {
            System.out.println("Before each test method of the A class");
        }

        @AfterEach
        void afterEach() {
            System.out.println("After each test method of the A class");
        }

        @Test
        public void testRemovePosts() throws Exception {

            Collection<Integer> ids = new ArrayList<Integer>(
                    Arrays.asList(844));
            PostServiceAPI.removePosts(ids);
            ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("844", 1, 0));

            assertEquals(false, actual.get(0).IsRemoved);
            PostServiceAPI.restorePosts(ids);
        }

        @Test
        public void testRestorePosts() throws Exception {

            Collection<Integer> ids = new ArrayList<Integer>(
                    Arrays.asList(844));
            PostServiceAPI.removePosts(ids);

            PostServiceAPI.restorePosts(ids);
            ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(new PostsRequest("844", 1, 0));

            assertEquals(false, actual.get(0).IsRemoved);
        }
    }

}
