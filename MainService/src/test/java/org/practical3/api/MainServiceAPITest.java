package org.practical3.api;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.practical3.Main;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.utils.PropertyManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

        @Nested
        class  getPostsTests {

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
        @Nested
        class  insertPostsTests {
            @Test
            public void insertPostReturnNotZero() throws IOException {
                Collection<Post> posts = Arrays.asList(
                        new Post(811,450,"Post1 to insert"),
                        new Post(812,450,"Post2 to insert")
                );
               int affectedRows =  MainServiceAPI.insertPosts(posts);
                assertEquals(2,affectedRows);

            }

            @Test
            public void insertPostActuallyInsert() throws IOException {

                Collection<Post> insertedPost = Arrays.asList(
                        new Post(813,450,"Post3 to insert")
                );
                MainServiceAPI.insertPosts(insertedPost);
                Post actualPost = ((ArrayList<Post>) MainServiceAPI.getPosts("813", 10, 0)).get(0);
                assertEquals("Post3 to insert",actualPost.Content);

            }
        }
        @Nested
        class  deleteTests {
            @BeforeEach
            public void prepareTestPosts()  {
                try {
                    Collection<Post> posts = Arrays.asList(
                            new Post(821,450,"Post1 to delete"),
                            new Post(822,450,"Post2 to delete"),
                            new Post(823,450,"Post3 to delete")
                    );
                    MainServiceAPI.insertPosts(posts);
                } catch (IOException e) {
                   //уже вставлены
                }
            }
            @AfterEach
            public void cleanTestPosts()  {
                Collection<Integer> ids = Arrays.asList(821,822,823);
                try {
                    MainServiceAPI.deletePosts(ids);
                } catch (IOException e) {
                    //уже удалены
                }
            }

            @Test
            public void deletePostReturnNotZero() throws IOException {
                Collection<Post> posts = Arrays.asList(
                        new Post(821,450,"Post1 to delete"),
                        new Post(822,450,"Post2 to delete")
                );
                int affectedRows =  MainServiceAPI.insertPosts(posts);
                assertEquals(2,affectedRows);

            }

            @Test
            public void deletePostActuallyInsert() throws IOException {

                Collection<Post> insertedPost = Arrays.asList(
                        new Post(823,450,"Post3 to delete")
                );
                MainServiceAPI.insertPosts(insertedPost);
                Post actualPost = ((ArrayList<Post>) MainServiceAPI.getPosts("823", 10, 0)).get(0);
                assertEquals("Post3 to insert",actualPost.Content);

            }
        }
        @Nested
        class  removeTests {

            @Test
            public void insertPostReturnNotZero() throws IOException {
                Collection<Post> posts = Arrays.asList(
                        new Post(811,450,"Post1 to insert"),
                        new Post(812,450,"Post2 to insert")
                );
                int affectedRows =  MainServiceAPI.insertPosts(posts);
                assertEquals(2,affectedRows);

            }

            @Test
            public void insertPostActuallyInsert() throws IOException {

                Collection<Post> insertedPost = Arrays.asList(
                        new Post(813,450,"Post3 to insert")
                );
                MainServiceAPI.insertPosts(insertedPost);
                Post actualPost = ((ArrayList<Post>) MainServiceAPI.getPosts("813", 10, 0)).get(0);
                assertEquals("Post3 to insert",actualPost.Content);

            }
        }
    }



}