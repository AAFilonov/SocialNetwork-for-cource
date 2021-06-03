package org.practical3.common;

import org.junit.jupiter.api.*;
import org.practical3.model.PostField;
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
        PropertyManager.load("./main.props");
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
        void insertPosts() throws SQLException {
            ArrayList<Post> inserted = new ArrayList<>(
                    Arrays.asList(
                            new Post(1, 0, "Post 3", new Date(System.currentTimeMillis())),
                            new Post(2, 0, "Post 4", new Date(System.currentTimeMillis())))
            );
            int affectedRows = postsDataBaseManager.insertPosts(inserted);
            assertEquals(affectedRows, 2);
        }

    }


    @Test
    void getPosts() throws SQLException, ClassNotFoundException {

        ArrayList<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(7, 8));

        ArrayList<PostField> postFields = new ArrayList<>(
                Arrays.asList(PostField.POST_ID, PostField.OWNER_ID, PostField.CONTENT));

        ArrayList<Post> expected = new ArrayList<>(
                Arrays.asList(new Post(7, 0, "First post"), new Post(8, 0, "Second post"))
        );

        ArrayList<Post> actual = (ArrayList<Post>) postsDataBaseManager.getPosts(ids, postFields, 10, 0);

        assertEquals(expected.toString(), actual.toString());

    }


    @Test
    void removePosts() {
    }

    @Test
    void deletePosts() {
    }

    @Test
    void getWall() throws SQLException {
        RequestWall requestWall =  TestUtils.createRequestWall();

        ArrayList<Post> expected = new ArrayList<>(
                Arrays.asList(new Post(null, null, "First post"), new Post(null, null, "Second post"))
        );

        ArrayList<Post>  actual = (ArrayList<Post>) postsDataBaseManager.getWall(requestWall);
        assertEquals(expected.toString(), actual.toString());

    }




}