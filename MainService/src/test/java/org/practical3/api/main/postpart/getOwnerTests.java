package org.practical3.api.main.postpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.SearchPostRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class getOwnerTests {


    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(1500, 101, "Post to test getowner")));

        TestUtils.createUser(new User(101,"User101","Pass101"));
    }

    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(Collections.singleton(1500));
        TestUtils.cleanUser(101);
    }

    @Test
    void getOwner_WhenOwnerExiist_ShouldReturnUser() throws IOException {
        User actual = MainServiceAPI.getOwner(1500);
        assertNotNull(actual);
    }
    @Test
    void getOwner_WhenOwnerExiist_ReturnedShouldMatch() throws IOException {
        User actual = MainServiceAPI.getOwner(1500);
        assertEquals("User101", actual.username);
    }

    @Test
    void getOwner_WhenPostNotExist_ShouldReturnNull() throws IOException {
        User actual = MainServiceAPI.getOwner(1501);
        assertNull(actual);
    }
    //если пост существует  а юзер нет - консистентность данных нарушена



}
