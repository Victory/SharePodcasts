package org.dfhu.sharepodcasts.morphs.query;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

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

    public List<EpisodeMorph> byShowId(String showId) {
        return datastore.createQuery(EpisodeMorph.class)
                .filter("showId = ", toObjectId(showId))
                .order("-pubDate")
                .asList();
    }

    public List<EpisodeMorph> textSearch(String keyword) {
        return datastore.createQuery(EpisodeMorph.class)
                .search(keyword)
                .order("pubDate")
                .asList();
    }

    public void updateFeedUniqueId(EpisodeMorph episode) {
        Query<EpisodeMorph> q = datastore.createQuery(EpisodeMorph.class);
        q.or(
                q.criteria("url").equal(episode.url),
                q.criteria("uniqueId").equal(episode.uniqueId));

        UpdateOperations<EpisodeMorph> ops =
                datastore.createUpdateOperations(EpisodeMorph.class)
                        .set("url", episode.url)
                        .set("uniqueId", episode.uniqueId);
        datastore.update(q, ops);
    }
}
