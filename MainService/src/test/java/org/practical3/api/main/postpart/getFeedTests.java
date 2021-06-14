package org.practical3.api.main.postpart;

import org.junit.jupiter.api.BeforeAll;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;

public class getFeedTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        prepareTestPosts();
    }
    public static void prepareTestPosts() {
        try {
            Collection<Post> posts = Arrays.asList(
                    new Post(821, 450, "Post1 to get as feed"),
                    new Post(822, 450, "Post2 to get as feed"),
                    new Post(823, 450, "Post3 to get as feed", Instant.now().minus(50, ChronoUnit.DAYS))
            );
            MainServiceAPI.insertPosts(posts);



        } catch (IOException e) {
            //уже вставлены
        }
    }

}
