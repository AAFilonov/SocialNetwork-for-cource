package org.practical3.utils.http;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.ServiceException;
import org.practical3.utils.StaticGson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class ResponseReader {

    public static Answer getAnswer(HttpResponse response)  {
        try {
            HttpEntity resp = response.getEntity();
            String respStr = EntityUtils.toString(resp);
            return StaticGson.fromJson(respStr, Answer.class);

        } catch (Exception e) {
            System.out.println("Error while read request: " + e.getMessage());
            return null;
        }
    }
    public static <T>T  getBodyAsObject(HttpResponse response, Class<T> tClass) {
        try {
            HttpEntity resp = response.getEntity();
            String respStr = EntityUtils.toString(resp);
            return StaticGson.fromJson(respStr,tClass );

        } catch (Exception e) {
            System.out.println("Error while read request: " + e.getMessage());
            return null;
        }
    }


    public static <T> Collection<T> getResponseBodyAsCollection(HttpResponse response, Type userListType) {
        try {
            HttpEntity resp = response.getEntity();
            String respStr = EntityUtils.toString(resp);
            return StaticGson.fromJson(respStr, userListType);
        } catch (Exception e) {
            System.out.println("Error while read request: " + e.getMessage());
            return null;
        }

    }


    public static Collection<Post> getPostsCollection(HttpResponse response) {

        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }

    public static Collection<User> getUsersCollection(HttpResponse response) {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }

    public static Collection<Integer> getIntegerCollection(HttpResponse response) {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }




}
