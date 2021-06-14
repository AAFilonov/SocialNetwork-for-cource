package org.practical3.api.main.postpart;

import org.junit.jupiter.api.*;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class deleteTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        prepareTestPosts();
    }
    public static void prepareTestPosts() {
        try {
            Collection<Post> posts = Arrays.asList(
                    new Post(821, 450, "Post1 to delete"),
                    new Post(822, 450, "Post2 to delete"),
                    new Post(823, 450, "Post3 to delete")
            );
            MainServiceAPI.insertPosts(posts);
        } catch (IOException e) {
            //уже вставлены
        }
    }

    @AfterAll
    public static void cleanTestPosts() {
        Collection<Integer> ids = Arrays.asList(821, 822, 823);
        try {
            MainServiceAPI.deletePosts(ids);
        } catch (IOException e) {
            //уже удалены
        }
    }


    @Test
    public void deletePostReturnNotZero() throws IOException {
        Collection<Integer> ids = Arrays.asList(821, 822);
        int affectedRows = MainServiceAPI.deletePosts(ids);
        assertEquals(2, affectedRows);

    }

    @Test
    public void deletePostActuallyDelete() throws IOException {
        Collection<Integer> ids = Collections.singletonList(823);
        MainServiceAPI.deletePosts(ids);
        ArrayList<Post> shouldBeEmptyArray = ((ArrayList<Post>) MainServiceAPI.getPosts("823", 10, 0));
        assertTrue(shouldBeEmptyArray.isEmpty());

    }
}
