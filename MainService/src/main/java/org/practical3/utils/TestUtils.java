package org.practical3.utils;

import org.practical3.model.postService.PostField;
import org.practical3.model.postService.RequestWall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TestUtils {


    public static RequestWall createRequestWall() {
        ArrayList<Integer> ownerIds = new ArrayList<Integer>(
                Arrays.asList(0, 2));
        ArrayList<PostField> postFields = new ArrayList<>(
                Arrays.asList(PostField.CONTENT));
        Date dateBegin = new Date(System.currentTimeMillis());
        Date dateEnd = new Date(System.currentTimeMillis());
        dateBegin.setMonth(dateBegin.getMonth() - 1);
        dateEnd.setMonth(dateBegin.getMonth() + 2);
        Integer Offset = 0;
        Integer Count = 10;

        return new RequestWall(ownerIds, postFields, dateBegin, dateEnd, Count, Offset);
    }

}
