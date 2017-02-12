package org.dfhu.sharepodcasts.morphs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

@Entity("shows")
@Indexes({
        @Index(fields = @Field(value = "title", type = IndexType.TEXT)),
})
public class ShowMorph {
    @Id
    public ObjectId id;
    public String title;
    @Indexed(options = @IndexOptions(unique = true))
    public String url;
    public String description;
}
