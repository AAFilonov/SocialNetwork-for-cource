package org.practical3;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.PropertyManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainServiceAPITest {
    @BeforeAll
    public static void init() {
        PropertyManager.load("./main.props");

        Thread newThread = new Thread(() -> {
            try {
                Main.runServer();

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Main.stopServer();

                    }
                }, "Stop Jetty Hook"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        newThread.start();
    }


    @Nested
    class  UserPart{
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
    @Nested
    class  PostPart {
        @Test
        public void getPostReturnNotHashMap() throws IOException {

            ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
            assertEquals(405, posts.get(0).OwnerId);

        }

        @Test
        public void getPostReturnElements() throws IOException {

            ArrayList<Post> posts = (ArrayList<Post>) MainServiceAPI.getPosts("988,989", 10, 0);
            assertEquals(2, posts.size());

        }
    }



}