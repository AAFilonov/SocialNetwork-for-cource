package org.practical3.common;

import org.junit.jupiter.api.*;
import org.practical3.model.Field;
import org.practical3.model.Post;
import org.practical3.utils.PostsDataBaseManager;
import org.practical3.utils.PropertyManager;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PostsDataBaseManagerTest {
    PostsDataBaseManager db;

    @BeforeEach
    public void init() {
        PropertyManager.load("./main.props");
        String DB_URL = PropertyManager.getPropertyAsString("database.server", "jdbc:postgresql://127.0.0.1:5432/");
        String DB_Name = PropertyManager.getPropertyAsString("database.database", "JavaPractice");
        String User = PropertyManager.getPropertyAsString("database.user", "postgres");
        String Password = PropertyManager.getPropertyAsString("database.password", "1");


        try {
            db = new PostsDataBaseManager(DB_URL, DB_Name, User, Password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @Nested
    class insertTests
    {
        @Test
        void insertPosts() throws SQLException {
            ArrayList<Post> inserted = new ArrayList<>(
                    Arrays.asList(
                            new Post(1, 0, "Post 3", new Date(System.currentTimeMillis())),
                            new Post(2, 0, "Post 4", new Date(System.currentTimeMillis())))
            );
            int affectedRows = db.insertPosts(inserted);
            assertEquals(affectedRows, 2);
        }

    }



    @Test
    void getPosts() throws SQLException, ClassNotFoundException {

        ArrayList<Integer> ids = new ArrayList<Integer>(
                Arrays.asList(1, 2));

        ArrayList<Field> fields = new ArrayList<>(
                Arrays.asList(Field.POST_ID, Field.OWNER_ID, Field.CONTENT));

        ArrayList<Post> expected = new ArrayList<>(
                Arrays.asList(new Post(1, 0, "First post", new Date(System.currentTimeMillis())), new Post(2, 0, "Second post", new Date(System.currentTimeMillis())))
        );

        ArrayList<Post> actual = (ArrayList<Post>) db.getPosts(ids, fields, 10, 0);

        assertEquals(expected.toString(), actual.toString());

    }


    @Test
    void removePosts() {
    }

    @Test
    void deletePosts() {
    }


}