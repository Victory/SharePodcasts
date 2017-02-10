package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("shows")
public class ShowMorph {
    @Id
    public ObjectId id;
    public String title;
    @Indexed(options = @IndexOptions(unique = true))
    public String url;
    public String description;
}
