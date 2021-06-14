package org.practical3.logic;

import com.google.common.reflect.TypeToken;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.PostsRequest;
import org.practical3.model.transfer.SearchPostRequest;
import org.practical3.model.transfer.WallRequest;

import org.practical3.utils.RequestReader;
import org.practical3.utils.ResponseReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


import static org.practical3.utils.StaticGson.toJson;

public class Actions {

    public static void sendOk(HttpServletResponse resp, Object data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(toJson(data));
    }


    public static void getWall(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ClassNotFoundException {
        WallRequest wallRequest = RequestReader.getRequestBodyAsObject(req, WallRequest.class);
        Collection<Post> posts = PostsDataBaseManager.getWall(wallRequest);
        if (posts.isEmpty()) throw  new ClassNotFoundException();
        sendOk(resp, posts);

    }

    public static void getPosts(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
        PostsRequest request = RequestReader.getRequestBodyAsObject(req, PostsRequest.class);
        Collection<Post> posts = PostsDataBaseManager.getPosts(request.ids, request.Count, request.Offset);
        if (posts.isEmpty()) throw  new ClassNotFoundException();
        sendOk(resp, posts);
    }

    public static void insertPosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = RequestReader.getRequestBodyAsCollection(req, userListType);
        Integer rowsAffected = PostsDataBaseManager.insertPosts(posts);
        sendOk(resp, new Answer("OK", null, rowsAffected));
    }

    public static void deletePosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        Collection<Integer> ids = RequestReader.getRequestBodyAsCollection(req, userListType);
        Integer rowsAffected = PostsDataBaseManager.deletePosts(ids);
        sendOk(resp, new Answer("OK", null, rowsAffected));
    }
    
    public static void updatePosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = RequestReader.getRequestBodyAsCollection(req, userListType);
        int affectedRows =  PostsDataBaseManager.updatePosts(posts);
        sendOk(resp, new Answer("OK", null, affectedRows));
    }

    public static void searchPosts(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, SQLException, IOException, ClassNotFoundException {
        SearchPostRequest searchPostRequest = RequestReader.getRequestBodyAsObject(req, SearchPostRequest.class);

        Collection<Post> posts =  PostsDataBaseManager.search(searchPostRequest);
        if(posts.isEmpty() )throw new ClassNotFoundException();
        sendOk(resp, posts);
    }

    public static void doLike(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, NumberFormatException {
        Integer post_id = new Integer( req.getParameter("post_id"));
        PostsDataBaseManager.doLike(post_id);
        sendOk(resp, new Answer("OK", null, null));
    }

    public static void doRepost(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, IOException, SQLException, ClassNotFoundException {
        Integer post_id = new Integer( req.getParameter("post_id"));
        Integer user_id = new Integer( req.getParameter("user_id"));
        Post post = PostsDataBaseManager.doRepost(user_id, post_id );
        sendOk(resp, Arrays.asList(post));
    }
}
