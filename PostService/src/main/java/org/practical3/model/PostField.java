package org.practical3.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public enum PostField {
    POST_ID("post_id"),
    OWNER_ID("owner_id"),
    CONTENT("content"),
    TIMESTAMP("post_timestamp"),
    IS_REMOVED("isRemoved"),
    IS_REDACTED("isRedacted"),
    IS_COMMENTABLE("isCommentable"),
    COUNT_LIKES("CountLikes"),
    COUNT_REPOSTS("CountReposts");


    private final String val;

    public  String getVal(){
        return  val;
    }
    PostField(String val) {
        this.val = val;
    }


}
