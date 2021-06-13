package org.practical3.model.transfer.requests;

import org.practical3.utils.Commons;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;


public class WallRequest {
    public Collection<Integer> OwnerIds;
    public Instant After= Instant.now().plus(30, ChronoUnit.DAYS);;
    public Instant Before=Instant.now().minus(30, ChronoUnit.DAYS);
    public Integer Count=10;
    public Integer Offset=0;

    public WallRequest(String ownerIds){
        OwnerIds = Commons.parseIds(ownerIds);
    }
    public WallRequest(String ownerIds, Instant after, Instant before){
        OwnerIds = Commons.parseIds(ownerIds);
        After = after;
        Before = before;
    }


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
