package org.dfhu.sharepodcasts.morphs.finders;

import org.bson.types.ObjectId;

public class BaseFinder {
    protected ObjectId toObjectId(String id) {
        return new ObjectId(id);
    }
}
