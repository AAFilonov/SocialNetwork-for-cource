package org.practical3.logic;

import com.google.common.reflect.TypeToken;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.PostsRequest;
import org.practical3.model.transfer.WallRequest;
import org.practical3.utils.Commons;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Actions {

    public static void sendOk(HttpServletResponse resp, Object data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(Commons.gson.toJson(data));
    }


    public static void getWall(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        WallRequest wallRequest = Commons.getRequestBodyAsObject(req, WallRequest.class);
        Collection<Post> posts = Commons.dataBaseManager.getWall(wallRequest);

        sendOk(resp, posts);

    }

    public static void getPosts(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
        PostsRequest request = Commons.getRequestBodyAsObject(req, PostsRequest.class);
        Collection<Post> posts = Commons.dataBaseManager.getPosts(request.ids, request.Count, request.Offset);
        sendOk(resp, posts);
    }

    public static void insertPosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = Commons.getRequestBodyAsCollection(req, userListType);
        Integer rowsAffected = Commons.dataBaseManager.insertPosts(posts);
        sendOk(resp, new Answer("OK", null, rowsAffected));
    }

    public static void deletePosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        Collection<Integer> ids = Commons.getRequestBodyAsCollection(req, userListType);
        Integer rowsAffected = Commons.dataBaseManager.deletePosts(ids);
        sendOk(resp, new Answer("OK", null, rowsAffected));
    }

    public static void removePosts(HttpServletRequest req, HttpServletResponse resp) {
        throw new NotImplementedException();
    }

    public static void updatePosts(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = Commons.getRequestBodyAsCollection(req, userListType);
        int affectedRows =  Commons.dataBaseManager.updatePosts(posts);
        sendOk(resp, new Answer("OK", null, affectedRows));
    }

    public static void searchPosts(HttpServletRequest req, HttpServletResponse resp) {
        throw new NotImplementedException();
    }

}