package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

@Entity("logfeedupdates")
public class FeedUpdateMorph {
    @Id
    public ObjectId id;
    public String showTitle;
    public String action;
    public List<String> episodes;
    public long timeStamp;
    public String humanDate;
    public String error;
    public int status;
}
