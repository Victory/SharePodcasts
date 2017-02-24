package org.dfhu.sharepodcasts.morphs.query;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.Optional;

public class EpisodeQuery extends BaseQuery {

    public EpisodeQuery(Datastore datastore) {
        super(datastore);
    }

    public Optional<EpisodeMorph> byId(String id) {
        EpisodeMorph episode = datastore.createQuery(EpisodeMorph.class)
                .filter("_id = ", toObjectId(id))
                .get();
        return Optional.ofNullable(episode);
    }

    public List<EpisodeMorph> textSearch(String keyword) {
        return datastore.createQuery(EpisodeMorph.class)
                .search(keyword)
                .order("pubDate")
                .asList();
    }
}
