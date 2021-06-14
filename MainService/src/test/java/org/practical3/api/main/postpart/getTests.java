package org.practical3.api.main.postpart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();


    }

    @Test
    public void getPostReturnNotHashMap() throws IOException {

        ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
        assertEquals(405, posts.get(0).OwnerId);

    }

    @Test
    public void getPostReturnElements() throws IOException {

        ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
        assertEquals(2, posts.size());

    }


}
