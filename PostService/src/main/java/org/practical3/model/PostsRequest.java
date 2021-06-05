package org.practical3.model;

import java.util.ArrayList;
import java.util.Map;

public class PostsRequest {

    public ArrayList<Integer> ids ;

    public int Count  ;
    public int Offset ;

    public PostsRequest(String IdsString, int count, int offset ) {

        ids = parseIds(IdsString);
        Count = count;
        Offset = offset;

    }


    private ArrayList<Integer> parseIds(String IdsString){
        String[] post_ids_s = IdsString.split(",");
        ArrayList<Integer> ids= new ArrayList<>();
        for (String id:post_ids_s) {
            ids.add(new Integer(id));
        }
        return ids;
    }

}
