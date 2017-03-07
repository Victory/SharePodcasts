package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;

public abstract class AbstractLog {
    @Id
    public ObjectId id;
    public String ip;
    public String userAgent;
    public Map<String, String> headers;
    public long timeStamp;
    public String pathname;

    public AbstractLog() {}
}
