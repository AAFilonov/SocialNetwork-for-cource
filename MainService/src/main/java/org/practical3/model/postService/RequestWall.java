package org.practical3.model.postService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class RequestWall {
    public Collection<Integer> OwnerIds;
    public Collection<PostField> Fields;
    public Date DateAfter;
    public Date DateBefore;
    public Integer Count;
    public Integer Offset;



    public RequestWall(Collection<Integer> ownerIds, Collection<PostField> postFields, Date dateAfter, Date dateBefore, Integer count, Integer offset){
        OwnerIds = ownerIds;
        Fields =postFields;
        DateAfter = dateAfter;
        DateBefore = dateBefore;
        Count = count;
        Offset = offset;
    }



    public static   Collection<PostField> parseFields(Map<String,String[]> args ){
        String fields = args.get("fields")[0];
        String[] fieldsArray = fields.split(",");
        Collection<PostField> finalPostFields = new ArrayList<>();


        for (String fieldString:fieldsArray) {
            finalPostFields.add(PostField.parse(fieldString));
        }
        return finalPostFields;
    }

}



