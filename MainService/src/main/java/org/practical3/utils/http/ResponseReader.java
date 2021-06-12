package org.practical3.utils.http;

import com.google.common.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.Answer;
import org.practical3.utils.StaticGson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class ResponseReader {

    public static Answer getResponseBody(HttpResponse response) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return StaticGson.fromJson(respStr, Answer.class);
    }

    public static<T> Collection<T> getResponseBodyAsCollection(HttpResponse response, Type userListType) throws IOException {
        HttpEntity resp = response.getEntity();
        String respStr = EntityUtils.toString(resp);
        return  StaticGson.fromJson(respStr, userListType);
    }


    public static Collection<Post> getPostsCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<Post>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }
    public static Collection<User> getUsersCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }

    public static Collection<Post>  getIntegerCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return getResponseBodyAsCollection(response, userListType);
    }
    //возвращает хэшмап
    public static <T> Collection<T> getCollection(HttpResponse response) throws IOException {
        Type userListType = new TypeToken<ArrayList<T>>() {}.getType();
        return getResponseBodyAsCollection(response, userListType);
    }


}
