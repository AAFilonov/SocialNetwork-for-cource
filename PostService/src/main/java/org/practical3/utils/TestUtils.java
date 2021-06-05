package org.practical3.utils;

import org.practical3.model.Post;
import org.practical3.model.WallRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TestUtils {
    public static final int TestOwnerId = 400;
    public static final int FirstPostId = 888;
    public static final int SecondPostId = 889;
    public static  Instant currentPoint = Instant.now();

    public static WallRequest createRequestWall() {
        ArrayList<Integer> ownerIds = new ArrayList<Integer>(
                Arrays.asList(TestOwnerId));
        Instant dateTimeBegin = currentPoint.minus(2, ChronoUnit.DAYS);
        Instant dateTimeEnd =currentPoint.plus(2, ChronoUnit.DAYS);

        Integer Offset = 0;
        Integer Count = 10;

        return new WallRequest(ownerIds, dateTimeBegin, dateTimeEnd, Count, Offset);
    }
    public static Collection<Integer> getTestPostsIds() {
        return  Arrays.asList(TestUtils.FirstPostId, TestUtils.SecondPostId);
    }


    public static Collection<Post> getTestPosts() {
        return  new ArrayList<>(
                Arrays.asList(new Post(FirstPostId, TestOwnerId, "First post",currentPoint), new Post(SecondPostId, TestOwnerId, "Second post",currentPoint))
        );
    }
}
