package org.dfhu.sharepodcasts.morphs.query;

import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.mongodb.morphia.Datastore;

import java.util.Optional;

public class ShareQuery extends BaseQuery {

    public ShareQuery(Datastore datastore) {
        super(datastore);
    }

    public void save(ShareMorph shareMorph) {
        datastore.save(shareMorph);
    }

    public boolean shortLinkExists(String key) {
        return byShortLink(key).isPresent();
    }

    public Optional<ShareMorph> byShortLink(String key) {
        ShareMorph shareMorph = datastore.createQuery(ShareMorph.class)
                .filter("shortLink = ", key)
                .get();

        return Optional.ofNullable(shareMorph);
    }

}
