package org.practical3.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;


public class RequestWall {
    public Collection<Integer> OwnerIds;
    public Instant DateTimeAfter;
    public Instant DateTimeBefore;
    public Integer Count;
    public Integer Offset;

    public RequestWall(ArrayList<Integer> ownerIds, Instant dateTimeAfter, Instant dateTimeBefore, Integer count, Integer offset){
        OwnerIds = ownerIds;

        DateTimeAfter = dateTimeAfter;
        DateTimeBefore = dateTimeBefore;
        Count = count;
        Offset = offset;
    }

}
