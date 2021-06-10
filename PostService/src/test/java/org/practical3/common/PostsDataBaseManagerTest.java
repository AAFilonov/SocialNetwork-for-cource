package org.practical3.common;

import org.junit.jupiter.api.*;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.utils.PropertyManager;
import org.practical3.utils.testing.TestUtils;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PostsDataBaseManagerTest {


    @BeforeAll
    public  static void init() {
        PropertyManager.load("./post.props");
        String DB_URL = PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
        String DB_Name = "javaPracticeTest";
        String User = PropertyManager.getPropertyAsString("database.user", "postgres");
        String Password = PropertyManager.getPropertyAsString("database.password", "1");


        try {
            Commons.dataBaseManager= new PostsDataBaseManager(DB_URL, DB_Name, User, Password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Nested
    class insertTests {
        @Test
        void insertTest() throws SQLException {

            Collection<Post> inserted = TestUtils.getTestPosts(888,889);
            int actualRowsAffected =  Commons.dataBaseManager.insertPosts(inserted);
            assertEquals(2, actualRowsAffected);
            TestUtils.clearTestData();
        }


    }


    @Test
    void getPostsTest() throws SQLException {

        Collection<Post> actual = Commons.dataBaseManager.getPosts(Arrays.asList(988, 989), 10, 0);

        assertEquals(2, actual.size());

    }


    @Test
    void removePostsTest() {
    }

    @Test
    void deletePostsTest() throws SQLException {
        TestUtils.insertTestData();
        int rowsDeleted = Commons.dataBaseManager.deletePosts(TestUtils.getTestPostsIds());
        assertEquals(2, rowsDeleted);
    }

    @Test
    void getWallTest() throws SQLException {
        WallRequest wallRequest = TestUtils.createRequestWall(405);
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.getWall(wallRequest);
        assertEquals(2, actual.size());
    }

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