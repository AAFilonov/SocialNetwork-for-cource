package org.practical3.model;

import java.util.Collection;

public class AnswerPosts {
    public Collection<Post> Items;
    public String Status;
    public AnswerPosts(Collection<Post> items, String status) {


        Items = items;
        Status = status;



    }
}
