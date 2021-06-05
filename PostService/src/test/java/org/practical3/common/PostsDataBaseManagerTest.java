package org.practical3.common;

import org.junit.jupiter.api.*;
import org.practical3.model.Post;
import org.practical3.model.RequestWall;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.TestUtils;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PostsDataBaseManagerTest {
    PostsDataBaseManager postsDataBaseManager;

    @BeforeEach
    public void init() {
        PropertyManager.load("./Post.props");
        String DB_URL = PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
        String DB_Name = PropertyManager.getPropertyAsString("database.database", "JavaPractice");
        String User = PropertyManager.getPropertyAsString("database.user", "postgres");
        String Password = PropertyManager.getPropertyAsString("database.password", "1");


        try {
            postsDataBaseManager = new PostsDataBaseManager(DB_URL, DB_Name, User, Password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @Nested
    class insertTests {
        @Test
        void insertTest() throws SQLException {

            Collection<Post> inserted = TestUtils.getTestPosts();
            int actualRowsAffected = postsDataBaseManager.insertPosts(inserted);
            assertEquals(2, actualRowsAffected);
            clearTestData();
        }


    }


    @Test
    void getPostsTest() throws SQLException, ClassNotFoundException {
        insertTestData();
        Collection<Integer> ids = TestUtils.getTestPostsIds();


        Collection<Post> actual = postsDataBaseManager.getPosts(ids, 10, 0);

        assertEquals(2, actual.size());
        clearTestData();
    }


    @Test
    void removePostsTest() {
    }

    @Test
    void deletePostsTest() throws SQLException {
        insertTestData();

        int rowsDeleted = postsDataBaseManager.deletePosts(TestUtils.getTestPostsIds());
        assertEquals(2, rowsDeleted);


    }

    @Test
    void getWallTest() throws SQLException {
        insertTestData();

        RequestWall requestWall = TestUtils.createRequestWall();
        Collection<Post> inserted = TestUtils.getTestPosts();

        ArrayList<Post> actual = (ArrayList<Post>) postsDataBaseManager.getWall(requestWall);

        assertEquals(2, actual.size());

        clearTestData();
    }


    void insertTestData() throws SQLException {
        try {
            Collection<Post> inserted = TestUtils.getTestPosts();
            postsDataBaseManager.insertPosts(inserted);
        }
        catch (Exception e) {
        //ничего не едлать они уже там
        }

    }

    void clearTestData() throws SQLException {

        postsDataBaseManager.deletePosts(TestUtils.getTestPostsIds());
    }


}