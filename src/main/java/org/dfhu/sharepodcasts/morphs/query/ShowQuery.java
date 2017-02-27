package org.dfhu.sharepodcasts.morphs.query;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.util.StreamUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mongodb.morphia.aggregation.Accumulator.accumulator;
import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Group.id;
import static org.mongodb.morphia.aggregation.Projection.projection;
import static org.mongodb.morphia.query.Sort.descending;

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

    public List<ShowLettersMorph> getShowLetters() {
        AggregationPipeline pipe = datastore.createAggregation(ShowMorph.class)
                .project(projection("id"), projection("title"))
                .group(grouping("_id", accumulator("$substr", Arrays.asList("$title", 0, 1))),
                        grouping("count", accumulator("$sum", 1)),
                        grouping("shows", accumulator("$push", "title")))
                .sort(descending("id"));
        Iterator<ShowLettersMorph> aggregate = pipe.aggregate(ShowLettersMorph.class);
        return StreamUtils.toStream(aggregate).collect(Collectors.toList());
    }
}
