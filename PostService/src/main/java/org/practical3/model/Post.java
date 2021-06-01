package org.practical3.model;

public class Post {
    public Integer PostId=null;
    public Integer OwnerId=null;
    public String Content =null;
    public Boolean isRemoved = null;
    public Boolean isRedacted = null;
    public Boolean isCommentable = null;
    public Integer CountLikes =null;
    public Integer CountReposts=null;

    public Post( ){
    }
    public Post( Integer postId, Integer ownerId, String content){
        PostId = postId;
        OwnerId = ownerId;
        Content = content;

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
      return String.format("[%s,%s,%s]",PostId.toString(),OwnerId.toString(),Content);
    }

    @Override
    public boolean equals(Object obj) {
        Post other = (Post)obj;
        boolean isEqual = true;
        if(PostId!=other.PostId ) return false;
        if(OwnerId!=other.OwnerId ) return false;
        if(Content!=other.Content ) return false;

        return  true;


    }
}
