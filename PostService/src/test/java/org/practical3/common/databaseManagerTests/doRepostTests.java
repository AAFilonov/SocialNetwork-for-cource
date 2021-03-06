package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.utils.testing.DBTestsUtils;
import org.practical3.model.data.Post;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class doRepostTests {

    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(730));
    @BeforeAll
    public static void init() {
        DBTestsUtils.init();
        DBTestsUtils.insertData(Arrays.asList(new Post(730, 430, "Some content")));
    }

    @AfterAll
    public static void cleanup() {
        DBTestsUtils.cleanData(postsToClean);
    }

    @Test
    void doRepost_ShouldCreatePost() throws SQLException, ClassNotFoundException {
        Post post  = PostsDataBaseManager.doRepost(431, 730);
        postsToClean.add(post.PostId);
        assertNotNull(post);


    }

    @Test
    void doRepost_CreatedPostShouldMatch() throws SQLException, ClassNotFoundException {
        Post post  = PostsDataBaseManager.doRepost(432, 730);
        postsToClean.add(post.PostId);


        assertEquals("Some content", post.Content);
    }

    @Test
    void doRepost_WhenPostNotFound_ShouldThrowException() {
        assertThrows(ClassNotFoundException.class, () -> PostsDataBaseManager.doRepost(431, 732));
    }

}
