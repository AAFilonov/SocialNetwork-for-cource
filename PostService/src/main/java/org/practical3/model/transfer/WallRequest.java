package org.practical3.model.transfer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;


public class WallRequest {
    public Collection<Integer> OwnerIds;
    public Instant After;
    public Instant Before;
    public Integer Count;
    public Integer Offset;

    public WallRequest(ArrayList<Integer> ownerIds, Instant after, Instant before, Integer count, Integer offset){
        OwnerIds = ownerIds;
        After = after;
        Before = before;
        Count = count;
        Offset = offset;
    }

}
