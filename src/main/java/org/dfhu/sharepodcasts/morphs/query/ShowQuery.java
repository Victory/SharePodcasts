package org.dfhu.sharepodcasts.morphs.query;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.mongodb.morphia.Datastore;

import java.util.Optional;

public class ShowQuery extends BaseQuery {

    public ShowQuery(Datastore datastore) {
        super(datastore);
    }

    public Optional<ShowMorph> byId(ObjectId id) {
        ShowMorph show = datastore.createQuery(ShowMorph.class)
                .filter("_id = ", id)
                .get();

        return Optional.ofNullable(show);
    }
}
