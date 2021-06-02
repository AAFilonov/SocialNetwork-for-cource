package org.practical3.model;

import java.util.Collection;

public class PostsAnswer {
    public Collection<Post> Items;
    public String Status;
    public PostsAnswer(Collection<Post> items, String status) {


        Items = items;
        Status = status;



    }
}
