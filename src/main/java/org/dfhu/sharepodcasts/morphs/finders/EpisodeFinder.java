package org.dfhu.sharepodcasts.morphs.finders;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.mongodb.morphia.Datastore;

import java.util.Optional;

public class EpisodeFinder extends BaseFinder {
    private final Datastore datastore;

    public EpisodeFinder(Datastore datastore) {
        this.datastore = datastore;
    }

    public Optional<EpisodeMorph> byId(String id) {
        EpisodeMorph episode = datastore.createQuery(EpisodeMorph.class)
                .filter("_id = ", toObjectId(id))
                .get();
        return Optional.ofNullable(episode);
    }
}
