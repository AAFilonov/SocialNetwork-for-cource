package org.practical3.api.main.userpart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.model.data.User;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class getTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();

    }

    @Test
    public void getUserReturnNotHashMap() throws IOException {

        ArrayList<User> users = (ArrayList<User>) MainServiceAPI.getUsers("7");
        assertEquals("java_username", users.get(0).username);

    }

    @Test
    public void getUserReturnElements() throws IOException {

        ArrayList<User> users = (ArrayList<User>) MainServiceAPI.getUsers("7");
        assertEquals(1, users.size());

    }
}
