package org.practical3.handlers;


import com.google.common.reflect.TypeToken;
import org.practical3.api.PostServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.Answer;
import org.practical3.model.transfer.requests.PostsRequest;
import org.practical3.utils.Commons;
import org.practical3.utils.http.HttpClientManager;
import org.practical3.utils.http.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class PostsServlet extends HttpServlet {
    public PostsServlet() {

    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[INFO]: Get request "+request.getMethod() +request.getRequestURI());
        super.service(req, res);
    }

    //TODO реализовать свои классы исключений

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Commons.executeAndCatchExceptions(req, resp, this::getPosts);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, this::insertPosts);
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, this::updatePosts);
    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commons.executeAndCatchExceptions(req, resp, this::deletePosts);
    }


    private void insertPosts(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {

        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = RequestReader.getRequestBodyAsCollection(httpServletRequest, userListType);
        Integer affectedRows = PostServiceAPI.insertPosts(posts);
        HttpClientManager.sendOk(new Answer("OK",null,affectedRows), response);

    }
    private void getPosts(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String,String[]> args =  req.getParameterMap();
        PostsRequest request= new PostsRequest(
                args.get("post_ids")[0],
                new Integer(args.get("count")[0]),
                new Integer(args.get("offset")[0])
        );
        Collection<Post> posts  = PostServiceAPI.getPosts(request);

        HttpClientManager.sendOk(posts, resp);
    }

    private void updatePosts(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        Collection<Post> posts = RequestReader.getRequestBodyAsCollection(httpServletRequest, userListType);
        Integer affectedRows = PostServiceAPI.updatePosts(posts);
        HttpClientManager.sendOk(new Answer("OK",null,affectedRows), response);
    }

    private void deletePosts(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        Collection<Integer> ids = RequestReader.getRequestBodyAsCollection(httpServletRequest, userListType);
        Integer affectedRows = PostServiceAPI.deletePosts(ids);
        HttpClientManager.sendOk(new Answer("OK",null,affectedRows), response);
    }



}