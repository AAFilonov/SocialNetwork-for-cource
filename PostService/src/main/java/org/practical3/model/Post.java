package org.practical3.model;

import java.time.Instant;
import java.util.Date;

public class Post {
    public Integer PostId=null;
    public Integer OwnerId=null;
    public String Content =null;
    public Instant Timestamp = null;
    public Boolean IsRemoved = null;
    public Boolean IsRedacted = null;
    public Boolean IsCommentable = null;
    public Integer CountLikes =null;
    public Integer CountReposts=null;

    public Post( ){
    }

    public Post( Integer postId,
                 Integer ownerId,
                 String content
               ){
        PostId = postId;
        OwnerId = ownerId;
        Content = content;


    }
    public Post( Integer postId,
                 Integer ownerId,
                 String content,
                 Instant timestamp){
        PostId = postId;
        OwnerId = ownerId;
        Content = content;
        Timestamp = timestamp;

    }
    public Post( Integer postId,
                 Integer ownerId,
                 String content,
                 Instant timestamp,
                 Boolean isRemoved,
                 Boolean isRedacted,
                 Boolean isCommentable ,
                 Integer countLikes,
                 Integer countReposts){
        PostId = postId;
        OwnerId = ownerId;
        Content = content;
        Timestamp = timestamp;
        IsRemoved = isRemoved;
        IsRedacted = isRedacted;
        IsCommentable=isCommentable;
        CountLikes = countLikes;
        CountReposts = countReposts;
    }

    @Override
    public int hashCode() {
       if (PostId!=null)
           return (int)PostId;
       else
           return  0;
    }

    @Override
    public String toString() {
        //TODO заменить на рефлексию
      return String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
              ,(PostId!=null)? PostId.toString():"NULL"
              ,(OwnerId!=null)?OwnerId.toString():"NULL"
              ,(Content!=null)?Content:"NULL"
              ,(Timestamp!=null)?Timestamp.toString():"NULL"
              ,(IsRemoved!=null)?IsRemoved.toString():"NULL"
              ,(IsRedacted!=null)?IsRedacted.toString():"NULL"
              ,(IsCommentable!=null)?IsCommentable.toString():"NULL"
              ,(CountLikes!=null)?CountLikes.toString():"NULL"
              ,(CountReposts!=null)?CountReposts.toString():"NULL"
      );
    }

    public String toSqlValues() {
        return String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                ,(PostId!=null)? "'"+PostId.toString()+"'":"DEFAULT"
                ,(OwnerId!=null)?"'"+OwnerId.toString()+"'":"DEFAULT"
                ,(Content!=null)?"'"+Content+"'":"DEFAULT"
                ,(Timestamp!=null)?"'"+Timestamp.toString()+"'":"DEFAULT"
                ,(IsRemoved!=null)?"'"+IsRemoved.toString()+"'":"DEFAULT"
                ,(IsRedacted!=null)?"'"+IsRedacted.toString()+"'":"DEFAULT"
                ,(IsCommentable!=null)?"'"+IsCommentable.toString()+"'":"DEFAULT"
                ,(CountLikes!=null)?"'"+CountLikes.toString()+"'":"DEFAULT"
                ,(CountReposts!=null)?"'"+CountReposts.toString()+"'":"DEFAULT"
        );
    }


    @Override
    public boolean equals(Object obj) {
        Post other = (Post)obj;
        boolean isEqual = true;
        if(PostId!=other.PostId ) return false;
        if(OwnerId!=other.OwnerId ) return false;
        if(Content!=other.Content ) return false;
        if(Timestamp!=other.Timestamp ) return false;

        return  true;


    }
}
