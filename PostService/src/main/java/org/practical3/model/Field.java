package org.practical3.model;

public enum Field {
    POST_ID("post_id"),
    OWNER_ID("owner_id"),
    CONTENT("content"),
    IS_REMOVED("isRemoved"),
    IS_REDACTED("isRedated"),
    IS_COMMENTABLE("isCommentable"),
    COUNT_LIKES("CountLikes"),
    COUNT_REPOSTS("CountReposts");

    private final String val;

    public  String getVal(){
        return  val;
    }
    Field(String val) {
        this.val = val;
    }

    public static Field parse(String s) throws IllegalArgumentException {

        for (Field field:Field.values()) {
            String fieldVal=field.getVal();
            if(s.equals(fieldVal))
                return  field;
        }

       throw new IllegalArgumentException();

    }
}
