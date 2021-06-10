package com.github.michael_sharko;

import com.github.michael_sharko.models.data.User;
import com.github.michael_sharko.utils.PropertyManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

class UserServiceAPITest {

    private static User user = new User();

    @BeforeAll
    static void start() throws ClassNotFoundException {
        PropertyManager.load("./main.props");
        Main.runServer();

        user.userid = 7;
        user.username = "java_username";
        user.password = "java_password";
    }

    @AfterAll
    static void finish() {
        Main.stopServer();
    }

    @Test
    void register() throws IOException {
        UserServiceAPI.register(user);
    }

    @Test
    void update() throws IOException {
        user.password = "java_from_update";
        UserServiceAPI.update(user);
    }

    @Test
    void getUsers() throws IOException {
        Collection<User> users = UserServiceAPI.getUsers("user_ids=red,white,4,java_username");

        //System.out.println("id: " + users.get(0).userid);
        //System.out.println("name: " + users.get(0).userid);

        // todo: solve it:
        // com.google.gson.internal.LinkedTreeMap cannot be cast to com.github.michael_sharko.models.data.User
    }

    @Test
    void delete() throws IOException {
        UserServiceAPI.delete(7);
    }

    @Test
    void getSubscriptions() throws IOException {
        System.out.println(UserServiceAPI.getSubscriptions(2));
    }

    @Test
    void getFollowers() throws IOException {
        System.out.println(UserServiceAPI.getFollowers(1));
    }
}