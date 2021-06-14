package org.practical3.api.main.postpart;

import org.junit.jupiter.api.AfterAll;
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

public class updateTests {
    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        prepareTestPosts();
    }
    public static void prepareTestPosts() {
        try {
            Collection<Post> posts = Arrays.asList(
                    new Post(831, 450, "Post1 to update"),
                    new Post(832, 450, "Post2 to update"),
                    new Post(833, 450, "Post3 to update")
            );
            MainServiceAPI.insertPosts(posts);
        } catch (IOException e) {
            //уже вставлены
        }
    }
    @AfterAll
    public static void cleanTestPosts() {
        Collection<Integer> ids = Arrays.asList(831, 832, 833);
        try {
            MainServiceAPI.deletePosts(ids);
        } catch (IOException e) {
            //уже удалены
        }
    }


    @Test
    public void  updatePostReturnNotZero() throws IOException {
        Collection<Post> posts = Arrays.asList(
                new Post(831, 450, "Post1 to update"),
                new Post(832, 450, "Post2 to update")
        );
        int affectedRows = MainServiceAPI.updatePosts(posts);
        assertEquals(2, affectedRows);

    }

    @Test
    public void updatePostActuallyUpdate() throws IOException {

        Collection<Post> insertedPost = Arrays.asList(
                new Post(833, 450, "Updated Content")
        );
        MainServiceAPI.updatePosts(insertedPost);
        Post actualPost = ((ArrayList<Post>) MainServiceAPI.getPosts("833", 10, 0)).get(0);
        assertEquals("Updated Content", actualPost.Content);

    }

}
