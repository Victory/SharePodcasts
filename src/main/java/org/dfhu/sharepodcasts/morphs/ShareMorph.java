package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

@Entity("shares")
public class ShareMorph {
    @Id
    public ObjectId id;
    public ObjectId episodeId;
    @Indexed(options = @IndexOptions(unique = true))
    public String shortLink;
    public String comment;
    public String ip;
    public long timeStamp;
}
