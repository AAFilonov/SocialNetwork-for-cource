package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.utils.testing.DBTestsUtils;
import org.practical3.model.data.Post;
import org.practical3.utils.Commons;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class doLikeTests {

    @BeforeAll
    public static void init() {
        DBTestsUtils.init();
        DBTestsUtils.insertData(Arrays.asList(
                new Post(720, 420, "Post to test like")
        ));
    }

    @AfterAll
    public static void cleanup() {
        DBTestsUtils.cleanData(Arrays.asList(720));
    }

    @Test
    void doLikeTest_ShouldIncreaseLikeCount() throws SQLException, ClassNotFoundException {

        Commons.dataBaseManager.doLike(720);
        ArrayList<Post> actual = (ArrayList<Post>)
                Commons.dataBaseManager.getPosts(Arrays.asList(720), 10, 0);

        assertEquals(1, actual.get(0).CountLikes);

    }
    @Test
    void doLikeTest_WhenNoSuchPost_ShouldThrowIllegalArgumentException()  {
        assertThrows(IllegalArgumentException.class, () ->  Commons.dataBaseManager.doLike(722));

    }
}
