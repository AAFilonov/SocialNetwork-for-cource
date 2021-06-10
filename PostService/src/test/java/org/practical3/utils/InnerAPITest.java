package org.practical3.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.Main;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.PostsRequest;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class InnerAPITest {

    @BeforeAll
    public static void init() throws SQLException {
        PropertyManager.load("./post.props");
        String DB_URL = PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
        String DB_Name = "javaPracticeTest";
        String User = PropertyManager.getPropertyAsString("database.user", "postgres");
        String Password = PropertyManager.getPropertyAsString("database.password", "1");


        Commons.dataBaseManager = new PostsDataBaseManager(DB_URL, DB_Name, User, Password);

        Thread newThread = new Thread(() -> {
            try {
                Main.runServer();

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Main.stopServer();

                    }
                }, "Stop Jetty Hook"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newThread.start();
    }

    @Test
    public void testGetPosts() throws Exception {
        PostsRequest postsRequest = new PostsRequest("988,989", 10, 0);

        Collection<Post> posts = InnerAPI.getPosts(postsRequest);

        assertEquals(2, posts.size());
    }


    @Test
    public void testGetWall() throws Exception {
        WallRequest wallRequest = TestUtils.createRequestWall(405);

        ArrayList<Post> posts = ( ArrayList<Post> )InnerAPI.getWall(wallRequest);

        assertEquals(2, posts.size());
        assertEquals(405, posts.get(0).OwnerId);
    }

    @Test
    public void testInsertPosts() throws Exception {
        Collection<Post> posts = TestUtils.getTestPosts(888, 889);

        int affectedRows = InnerAPI.insertPosts(posts);

        assertEquals(2, affectedRows);
        TestUtils.clearTestData();
    }

    @Test
    public void testUpdatePosts() throws Exception {
        TestUtils.insertTestData();

        ArrayList<Post> posts = new ArrayList<>(
                Arrays.asList(
                        new Post(888, null, null, Instant.now()),
                        new Post(889, null, null, Instant.now()))
        );
        int affectedRows = InnerAPI.updatePost(posts);

        assertEquals(2, affectedRows);
        TestUtils.clearTestData();

    }

    @Test
    public void testDeletePosts() throws Exception {
        TestUtils.insertTestData();

        Collection<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(888, 889));
        int affectedRows = InnerAPI.deletePosts(ids);

        assertEquals(2, affectedRows);

    }
}

