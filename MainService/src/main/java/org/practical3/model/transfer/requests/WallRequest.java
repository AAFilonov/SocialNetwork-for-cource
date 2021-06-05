package org.practical3.model.transfer.requests;

import org.practical3.utils.Commons;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;


public class WallRequest {
    public Collection<Integer> OwnerIds;
    public Instant After;
    public Instant Before;
    public Integer Count;
    public Integer Offset;


    public WallRequest(Collection<Integer> ownerIds, Instant after, Instant before, Integer count, Integer offset){
        OwnerIds = ownerIds;
        After = after;
        Before = before;
        Count = count;
        Offset = offset;
    }
    public WallRequest(String ownerIds, Instant after, Instant before, Integer count, Integer offset){
        OwnerIds = Commons.parseIds(ownerIds);
        After = after;
        Before = before;
        Count = count;
        Offset = offset;
    }

}
