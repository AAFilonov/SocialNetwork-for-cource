package org.practical3.utils;






import org.practical3.api.PostServiceAPI;
import org.practical3.api.UserServiceAPI;
import org.practical3.model.data.Post;
import org.practical3.model.data.User;
import org.practical3.model.transfer.requests.WallRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class TestUtils {



    public static WallRequest createRequestWall(int ownerId) {
        ArrayList<Integer> ownerIds = new ArrayList<Integer>(
                Arrays.asList(ownerId));
        Instant dateTimeBegin =Instant.now().minus(30, ChronoUnit.DAYS);
        Instant dateTimeEnd =Instant.now().plus(30, ChronoUnit.DAYS);

        Integer Offset = 0;
        Integer Count = 10;

        return new WallRequest(ownerIds, dateTimeBegin, dateTimeEnd, Count, Offset);
    }


    public static Collection<Post> getTestPosts(int id1, int id2, int ownerID) {
        return  new ArrayList<>(
                Arrays.asList(new Post(id1, ownerID, "First post",Instant.now()), new Post(id2, ownerID, "Second post",Instant.now()))
        );
    }

    public static void insertTestData()  {
        try {

            Collection<Post> inserted = TestUtils.getTestPosts(888, 889, 400);
            PostServiceAPI.insertPosts(inserted);
        } catch (Exception e) {
            //уже вставлены
        }
    }

    public static void clearTestData()  {
        try {
        PostServiceAPI.deletePosts(Arrays.asList(888, 889));
        } catch (Exception e) {
            //уже удалены
        }
    }

    public static void createPosts(Collection<Post> data){
        try {
            PostServiceAPI.insertPosts(data);
        } catch (Exception e) {
            //уже вставлен
        }
    }

    public static void cleanPosts(Collection<Integer> data){
        try {
            PostServiceAPI.deletePosts(data);
        } catch (Exception e) {
            //уже удален
        }
    }public static void createUsers(Collection<User> data){
        try {
            UserServiceAPI.register((User[]) data.toArray());
        } catch (Exception e) {
            //уже вставлен
        }
    }

    public static void cleanUsers(String data){
        try {
            UserServiceAPI.delete(data);
        } catch (Exception e) {
            //уже удален
        }
    }

}
