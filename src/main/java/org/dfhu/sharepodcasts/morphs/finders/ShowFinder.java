package org.dfhu.sharepodcasts.morphs.finders;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.mongodb.morphia.Datastore;

import java.util.Optional;

public class ShowFinder extends BaseFinder {
    private final Datastore datastore;

    public ShowFinder(Datastore datastore) {
        this.datastore = datastore;
    }

    public Optional<ShowMorph> byId(ObjectId id) {
        ShowMorph show = datastore.createQuery(ShowMorph.class)
                .filter("_id = ", id)
                .get();

        return Optional.ofNullable(show);
    }
}
