package org.practical3.api.main.userpart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class registerTests {

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        Collection<Integer> ids = Arrays.asList(450);

        ids.iterator().forEachRemaining((e) -> {
            try {
                UserServiceAPI.delete(e);
            } catch (IOException ioException) {
                //уже удален
            }
        });
    }

    @Test
    public void registeredUserMatch() throws IOException {
        User userToRegister = new User();
        userToRegister.userid = 450;
        userToRegister.username = "User450";
        userToRegister.password = "Pass450";
        UserServiceAPI.register(userToRegister);

        ArrayList<User> actualUser = (ArrayList<User>) UserServiceAPI.getUsers("450");
        assertEquals(userToRegister.username, actualUser.get(0).username);

    }

    @Test
    public void registerActuallyRegister() throws IOException {
        User userToRegister = new User();
        userToRegister.userid = 450;
        userToRegister.username = "User450";
        userToRegister.password = "Pass450";
        MainServiceAPI.registerUser(userToRegister);

        ArrayList<User> actualUser = (ArrayList<User>) UserServiceAPI.getUsers("450");
        assertEquals(1, actualUser.size());

    }


    @AfterAll
    public static void cleanTestUsers() {
        Collection<Integer> ids = Arrays.asList(450);

        ids.iterator().forEachRemaining((e) -> {
            try {
                UserServiceAPI.delete(e);
            } catch (IOException ioException) {
                //уже удален
            }
        });

    }


}
