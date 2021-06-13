package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.common.PostsDataBaseManagerTestBase;
import org.practical3.model.data.Post;
import org.practical3.utils.Commons;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class doRepostTests {

    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(730));
    @BeforeAll
    public static void init() {
        PostsDataBaseManagerTestBase.init();
        PostsDataBaseManagerTestBase.insertData(Arrays.asList(new Post(730, 430, "Some content")));
    }

    @AfterAll
    public static void cleanup() {
        PostsDataBaseManagerTestBase.cleanData(postsToClean);
    }

    @Test
    void doRepost_ShouldCreatePost() throws SQLException, ClassNotFoundException {
        int insertedPostID = Commons.dataBaseManager.doRepost(431, 730);
        postsToClean.add(insertedPostID);
        ArrayList<Post> actual = (ArrayList<Post>)
                Commons.dataBaseManager.getPosts(Arrays.asList(insertedPostID), 10, 0);

        assertEquals(1, actual.size());


    }

    @Test
    void doRepost_CreatedPostShouldMatch() throws SQLException, ClassNotFoundException {
        int insertedPostID = Commons.dataBaseManager.doRepost(432, 730);
        postsToClean.add(insertedPostID);

        ArrayList<Post> actual = (ArrayList<Post>)
                Commons.dataBaseManager.getPosts(Arrays.asList(insertedPostID), 10, 0);

        assertEquals(1, actual.size());
    }

    @Test
    void doRepost_WhenPostNotFound_ShouldThrowException() {
        assertThrows(ClassNotFoundException.class, () -> Commons.dataBaseManager.doRepost(431, 732));
    }

}
