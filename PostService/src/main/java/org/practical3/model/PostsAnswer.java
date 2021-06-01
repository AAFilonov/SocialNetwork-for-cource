package org.practical3.model;

import java.util.Collection;

public class PostsAnswer {
    Collection<Post> Items;
    String Status;
    public PostsAnswer(Collection<Post> items, String status){
        Items = items;
        Status = status;
    }
}
