package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;

import org.practical3.utils.testing.DBTestsUtils;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class deleteTests {

    @BeforeAll
    public static void init() {
        DBTestsUtils.init();
        DBTestsUtils.insertData(Arrays.asList(
                new Post(741, 420, "Post to test delete"),
                new Post(742, 420, "Post to test like")
        ));
    }

    @AfterAll
    public static void cleanup() {
        DBTestsUtils.cleanData(Arrays.asList(741,742));
    }

    @Test
    void deletePostsTest() throws SQLException {

        int rowsDeleted = PostsDataBaseManager.deletePosts(Arrays.asList(741,742));
        assertEquals(2, rowsDeleted);
    }
}
