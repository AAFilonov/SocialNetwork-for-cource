package org.practical3.model;

import java.util.ArrayList;
import java.util.Map;

public class RequestPosts {

    public ArrayList<Integer> ids ;
    public ArrayList<PostField> postFields;
    public Integer count  ;
    public Integer offset ;

    public RequestPosts(Map<String, String[]> args){

        ids = parseIds(args);
       postFields = parseFields(args);
        count = new Integer(args.get("count")[0]);
        offset = new Integer(args.get("offset")[0]);

    }


    private ArrayList<Integer> parseIds( Map<String,String[]> args ){
        String post_idsStr = args.get("post_ids")[0];
        String[] post_ids_s = post_idsStr.split(",");
        ArrayList<Integer> ids= new ArrayList<>();
        for (String id:post_ids_s) {
            ids.add(new Integer(id));
        }
        return ids;
    }

    private  ArrayList<PostField> parseFields(Map<String,String[]> args ){
        String fields = args.get("fields")[0];
        String[] fieldsArray = fields.split(",");
        ArrayList<PostField> finalPostFields = new ArrayList<>();


        for (String fieldString:fieldsArray) {
            finalPostFields.add(PostField.parse(fieldString));
        }
        return finalPostFields;
    }
}
