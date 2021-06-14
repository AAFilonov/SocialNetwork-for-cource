package org.practical3.utils.testing;

import org.practical3.logic.PostsDataBaseManager;
import org.practical3.model.data.Post;
import org.practical3.model.transfer.WallRequest;


import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TestUtils {
    public static final int TestOwnerId = 400;

    public static final int FirstPostId = 888;
    public static final int SecondPostId = 889;


    public static Instant currentPoint = Instant.now();

    public static WallRequest createRequestWall(int ownerId) {
        ArrayList<Integer> ownerIds = new ArrayList<Integer>(
                Arrays.asList(ownerId));
        Instant dateTimeBegin = currentPoint.minus(30, ChronoUnit.DAYS);
        Instant dateTimeEnd = currentPoint.plus(30, ChronoUnit.DAYS);

        Integer Offset = 0;
        Integer Count = 10;

        return new WallRequest(ownerIds, dateTimeBegin, dateTimeEnd, Count, Offset);
    }

    public static Collection<Integer> getTestPostsIds() {
        return Arrays.asList(TestUtils.FirstPostId, TestUtils.SecondPostId);
    }


    public static Collection<Post> getTestPosts(int id1, int id2) {
        return new ArrayList<>(
                Arrays.asList(new Post(id1, TestOwnerId, "First post", Instant.now()), new Post(id2, TestOwnerId, "Second post", Instant.now()))
        );
    }


    public static void insertTestData() throws SQLException {
        try {

            Collection<Post> inserted = TestUtils.getTestPosts(888, 889);
            PostsDataBaseManager.insertPosts(inserted);
        } catch (Exception e) {
            //уже вставлены
        }
    }

    public static void clearTestData() throws SQLException {

       PostsDataBaseManager.deletePosts(Arrays.asList(888, 889));
    }


}
