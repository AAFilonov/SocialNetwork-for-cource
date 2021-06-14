package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.WallRequest;

import org.practical3.utils.testing.DBTestsUtils;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getWallTests {
    @BeforeAll
    public static void init() {
        DBTestsUtils.init();
        DBTestsUtils.insertData(Arrays.asList(
                new Post(751, 422, "Post to test getWall"),
                new Post(752, 422, "Post to test getWall")
        ));
    }

    @AfterAll
    public static void cleanup() {
        DBTestsUtils.cleanData(Arrays.asList(751,752));
    }

    @Test
    void getWallTest() throws SQLException {
        WallRequest wallRequest = TestUtils.createRequestWall(422);
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.getWall(wallRequest);
        assertEquals(2, actual.size());
    }

}
