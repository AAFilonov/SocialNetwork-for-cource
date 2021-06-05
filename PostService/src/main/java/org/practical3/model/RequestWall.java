package org.practical3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDate;


public class RequestWall {
    public Collection<Integer> OwnerIds;
    public Collection<PostField> Fields;
    public LocalDate DateAfter;
    public LocalDate DateBefore;
    public Integer Count;
    public Integer Offset;

    public RequestWall(ArrayList<Integer> ownerIds, ArrayList<PostField> postFields, LocalDate dateAfter, LocalDate dateBefore, Integer count, Integer offset){
        OwnerIds = ownerIds;
        Fields =postFields;
        DateAfter = dateAfter;
        DateBefore = dateBefore;
        Count = count;
        Offset = offset;
    }

}
