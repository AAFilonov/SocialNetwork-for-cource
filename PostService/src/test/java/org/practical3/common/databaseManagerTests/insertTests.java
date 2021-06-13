package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.common.PostsDataBaseManagerTestBase;
import org.practical3.model.data.Post;
import org.practical3.utils.Commons;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class insertTests {


    @BeforeAll
    public static void init() {
        PostsDataBaseManagerTestBase.init();

    }

    @AfterAll
    public static void cleanup() {
        PostsDataBaseManagerTestBase.cleanData(Arrays.asList(887,888,889,890));
    }

    @Test
    void insertTest_ShouldReturnInsertedCount() throws SQLException {

        Collection<Post> inserted = Arrays.asList(
                new Post(887, 400, "Some content"),
                new Post(888, 400, "Some content")
        );
        int actualRowsAffected = Commons.dataBaseManager.insertPosts(inserted);
        assertEquals(2, actualRowsAffected);


    }
    @Test
    void insertTest_ShouldCreatePosts() throws SQLException {

        Collection<Post> inserted = Arrays.asList(
                new Post(889, 400, "Content to check")
        );
        Commons.dataBaseManager.insertPosts(inserted);

        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.getPosts(Arrays.asList(889), 10,0);

        assertEquals("Content to check", actual.get(0).Content);

    }
    @Test
    void insertTest_WhenAlreadyExist_ShouldThrowIllegalArgumentException() throws SQLException {
        Collection<Post> inserted = Arrays.asList(
                new Post(890, 400, "Content")
        );
        Commons.dataBaseManager.insertPosts(inserted);

        assertThrows(IllegalArgumentException.class, () ->   Commons.dataBaseManager.insertPosts(inserted));
    }
}
