package org.practical3.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.practical3.model.postService.AnswerPostService;

import org.practical3.model.postService.RequestPosts;
import org.practical3.model.postService.RequestWall;
import org.practical3.model.userService.AnswerUserService;

import java.io.IOException;

public class Commons {
    public HttpClient httpClient ;
    public Gson gson;
    public Commons(){
        httpClient = HttpClientBuilder.create().build();
        gson = new Gson();
    }

    public AnswerPostService httpGetPosts(RequestPosts requestPosts) throws IOException {
        String url = String.format("%s/posts?action=getPosts",
                PropertyManager.getPropertyAsString("service.posts.addr","http://localhost:8027"));
        HttpResponse response =executePost(url,requestPosts) ;
        return getResponceBodyPost(response);
    }



    public AnswerPostService httpGetWall(  RequestWall request ) throws IOException {
        String url = String.format("%s/posts?action=getWall",
                PropertyManager.getPropertyAsString("service.posts.addr","http://localhost:8027"));
        HttpResponse response = executePost(url,request) ;
        return getResponceBodyPost(response);
    }

    HttpResponse executePost(String url, Object body) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity( gson.toJson(body));
        request.setEntity(entity);
        return httpClient.execute(request);

    }
    AnswerPostService getResponceBodyPost(HttpResponse response) throws IOException {
        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);

        return gson.fromJson(respStr, AnswerPostService.class);
    }


    public AnswerUserService httpGetSubscriptions(Integer userId) throws IOException {
        String url = String.format("%s/subscribes?action=getWall",
                PropertyManager.getPropertyAsString("service.posts.addr","http://localhost:8080"));
        HttpResponse response =executePost(url,userId) ;
        return getResponceBodyUser(response);

    }
    AnswerUserService getResponceBodyUser(HttpResponse response) throws IOException {
        HttpEntity resp  = response.getEntity();
        String respStr = EntityUtils.toString(resp);

        return gson.fromJson(respStr, AnswerUserService.class);
    }
}
