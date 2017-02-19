package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("requests")
public class RequestLogAnalytics {
    @Id
    public ObjectId id;
    public String ip;
    public String userAgent;
    public String headers;
    public long timeStamp;
}
