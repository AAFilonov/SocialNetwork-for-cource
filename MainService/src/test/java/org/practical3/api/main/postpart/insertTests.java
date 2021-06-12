package org.practical3.api.main.postpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class insertTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
    }

    @AfterAll
    public static void cleanTestPosts() {
        Collection<Integer> ids = Arrays.asList(811, 812, 813);
        try {
            MainServiceAPI.deletePosts(ids);
        } catch (IOException e) {
            //уже удалены
        }
    }


    @Test
    public void insertPostReturnNotZero() throws IOException {
        Collection<Post> posts = Arrays.asList(
                new Post(811, 450, "Post1 to insert"),
                new Post(812, 450, "Post2 to insert")
        );
        int affectedRows = MainServiceAPI.insertPosts(posts);
        assertEquals(2, affectedRows);

    }

    @Test
    public void insertPostActuallyInsert() throws IOException {

        Collection<Post> insertedPost = Arrays.asList(
                new Post(813, 450, "Post3 to insert")
        );
        MainServiceAPI.insertPosts(insertedPost);
        Post actualPost = ((ArrayList<Post>) MainServiceAPI.getPosts("813", 10, 0)).get(0);
        assertEquals("Post3 to insert", actualPost.Content);

    }
}
