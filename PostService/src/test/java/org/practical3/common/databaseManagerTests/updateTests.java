package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.Test;
import org.practical3.model.data.Post;
import org.practical3.utils.Commons;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class updateTests {


    @Test
    void updateTest() throws SQLException {
        TestUtils.insertTestData();

        Post post1 = new Post(TestUtils.FirstPostId, null, "Some string");
        Post post2 = new Post(TestUtils.SecondPostId, null, "Some other string");

        Commons.dataBaseManager.updatePosts(Arrays.asList(post1, post2));

        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.getPosts(Arrays.asList(TestUtils.FirstPostId, TestUtils.SecondPostId), 10, 0);
        assertEquals(post1.Content, actual.get(0).Content);
        assertEquals(post2.Content, actual.get(1).Content);
        TestUtils.clearTestData();
    }
}
