package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.Test;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getTetst {

    @Test
    void getPostsTest() throws SQLException {

        Collection<Post> actual = PostsDataBaseManager.getPosts(Arrays.asList(988, 989), 10, 0);

        assertEquals(2, actual.size());

    }
}
