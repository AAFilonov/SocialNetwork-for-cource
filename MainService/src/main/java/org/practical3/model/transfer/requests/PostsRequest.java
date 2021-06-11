package org.practical3.model.transfer.requests;

import org.practical3.utils.Commons;


import java.util.Collection;

public class PostsRequest {

    public Collection<Integer> ids ;

    public int Count  ;
    public int Offset ;

    public PostsRequest(String IdsString, int count, int offset ) {

        ids = Commons.parseIds(IdsString);
        Count = count;
        Offset = offset;

    }




}
