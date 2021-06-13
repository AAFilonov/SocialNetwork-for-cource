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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class searchTests {


    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(601,602,603,604,605,606));
    @BeforeAll
    public static void init() {
        PostsDataBaseManagerTestBase.init();
        PostsDataBaseManagerTestBase.insertData(Arrays.asList(
                new Post(601, 441, "Саня вставил сюда текст"),
                new Post(602, 441, "Сюда идет утка"),
                new Post(603, 440, "утка вставит текст"),
                new Post(604, 441, "Текст идет"),
                new Post(605, 440, "саня идет"),
                new Post(606, 440, "СЮДА идет Иван")

        ));
    }

    @AfterAll
    public static void cleanup() {
        PostsDataBaseManagerTestBase.cleanData(postsToClean);
    }

    @Test
    void search_WhenOwnerNull_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("утка", null);
        assertEquals(2, actual.size());
    }

    @Test
    void search_WhenOwnerReal_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("утка", 440);
        assertEquals(1, actual.size());
    }
    @Test
    void search_WhenOwnerNotReal_ShouldReturnEmpty() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("Иван", 444);
        assertEquals(0, actual.size());
    }

    @Test
    void search_ContentExist_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("идет", null);
        assertEquals(4, actual.size());
    }
    @Test
    void search_ContentExistAndOwnerTrue_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("идет", 441);
        assertEquals(2, actual.size());
    }

    @Test
    void search_ContentNotExist_ShouldReturnEmpty() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) Commons.dataBaseManager.search("Петр", null);
        assertEquals(0, actual.size());
    }



}
