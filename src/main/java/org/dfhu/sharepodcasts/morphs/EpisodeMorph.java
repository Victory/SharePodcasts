package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

@Entity("episodes")
public class EpisodeMorph {
    @Id
    public ObjectId id;
    public String title;
    @Indexed(options = @IndexOptions(unique = true))
    public String url;
    public String description;
    public long pubDate;
    public String showTitle;
    public ObjectId showId;
}
