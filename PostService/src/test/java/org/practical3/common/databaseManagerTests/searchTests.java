package org.practical3.common.databaseManagerTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.transfer.SearchPostRequest;
import org.practical3.utils.testing.DBTestsUtils;
import org.practical3.model.data.Post;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class searchTests {


    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(601,602,603,604,605,606));
    @BeforeAll
    public static void init() {
        DBTestsUtils.init();
        DBTestsUtils.insertData(Arrays.asList(
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
        DBTestsUtils.cleanData(postsToClean);
    }

    @Test
    void search_WhenOwnerNull_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("утка", null));
        assertEquals(2, actual.size());
    }

    @Test
    void search_WhenOwnerReal_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("утка", 440));
        assertEquals(1, actual.size());
    }
    @Test
    void search_WhenOwnerNotReal_ShouldReturnEmpty() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("Иван", 444));
        assertEquals(0, actual.size());
    }

    @Test
    void search_ContentExist_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("идет", null));
        assertEquals(4, actual.size());
    }
    @Test
    void search_ContentExistAndOwnerTrue_ShouldReturnPosts() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("идет", 441));
        assertEquals(2, actual.size());
    }

    @Test
    void search_ContentNotExist_ShouldReturnEmpty() throws SQLException, ClassNotFoundException {
        ArrayList<Post> actual = (ArrayList<Post>) PostsDataBaseManager.search(new SearchPostRequest("Петр", null));
        assertEquals(0, actual.size());
    }



}
