package org.dfhu.sharepodcasts.morphs.query;

import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.mongodb.morphia.Datastore;

public class ShareQuery extends BaseQuery {

    public ShareQuery(Datastore datastore) {
        super(datastore);
    }

    public void save(ShareMorph shareMorph) {
        datastore.save(shareMorph);
    }

    public boolean shortLinkExists(String key) {
        ShareMorph shareMorph = datastore.createQuery(ShareMorph.class)
                .filter("shortLink = ", key)
                .get();

        return shareMorph != null;
    }
}
