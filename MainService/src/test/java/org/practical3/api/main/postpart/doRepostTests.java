package org.practical3.api.main.postpart;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.practical3.api.MainServiceAPI;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.TestUtils;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.ResponseReader;
import org.practical3.utils.http.StaticServerForTests;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class doRepostTests {
    static ArrayList<Integer> postsToClean = new ArrayList<>(Arrays.asList(211,212,213));

    @BeforeAll
    public static void init() {
        StaticServerForTests.start();
        TestUtils.createPosts(Arrays.asList(
                new Post(211,701,"Post to check repost from MainAPI"),
                new Post(212,701,"Post to check repost from MainAPI"),
                new Post(213,701,"Post to check repost from MainAPI")
        ));
        TestUtils.createUsers(Arrays.asList(new User(702,"User702","Pass702")));
    }
    @AfterAll
    public static void cleanup() {

        TestUtils.cleanPosts(postsToClean);
        TestUtils.cleanUsers("702");
    }

    @Test
    public void doRepost_WhenPostExist_ShouldReturnCreatedPost() throws IOException {
        HttpResponse response =  MainServiceAPI.doRepost("User702",211);
        Type userListType = new TypeToken<Collection<Post>>() {
        }.getType();

        Collection<Post>  returned = ResponseReader.getResponseBodyAsCollection(response, userListType);
        postsToClean.add(((ArrayList<Post>) returned).get(0).PostId);
        assertEquals(1,returned.size());

    }

    @Test
    public void doRepost_WhenPostNotExist_ShouldReturn404() throws IOException {
        HttpResponse response =  MainServiceAPI.doRepost("User702",215);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doRepost_ShouldUpdateCountRepost() throws Exception {

        HttpResponse response =   MainServiceAPI.doRepost("User702",212);
        Type userListType = new TypeToken<Collection<Post>>() {
        }.getType();

        Collection<Post>  returned = ResponseReader.getResponseBodyAsCollection(response, userListType);
        postsToClean.add(((ArrayList<Post>) returned).get(0).PostId);

        ArrayList<Post> actual = (ArrayList<Post>) PostServiceAPI.getPosts(
                new PostsRequest("212"));

        assertEquals(1, actual.get(0).CountReposts);


    }
    @Test
    public void doRepost_ShouldCreateNewPost() throws Exception {
        HttpResponse response =     MainServiceAPI.doRepost("User702",213);
        Type userListType = new TypeToken<Collection<Post>>() {
        }.getType();

        Collection<Post>  returned = ResponseReader.getResponseBodyAsCollection(response, userListType);
        postsToClean.add(((ArrayList<Post>) returned).get(0).PostId);

        ArrayList<Post> actual = PostServiceAPI.getPosts(  new PostsRequest(((ArrayList<Post>) returned).get(0).PostId.toString()));


        assertNotNull(((ArrayList<Post>) returned).get(0).Content,actual.get(0).Content );


    }

}
