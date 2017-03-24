package org.dfhu.sharepodcasts.morphs.query;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.util.StreamUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mongodb.morphia.aggregation.Accumulator.accumulator;
import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Projection.projection;
import static org.mongodb.morphia.query.Sort.ascending;

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

    public List<ShowMorph> all() {
        return datastore.find(ShowMorph.class)
                .asList();
    }

    public Optional<ShowMorph> byUrl(String url) {
        ShowMorph show = datastore.createQuery(ShowMorph.class)
                .filter("url = ", url)
                .get();

        return Optional.ofNullable(show);
    }

    public List<ShowLettersMorph> getShowLetters() {
        AggregationPipeline pipe = datastore.createAggregation(ShowMorph.class)
                .project(projection("id"), projection("title"))
                .group(grouping("_id",
                        accumulator("$toUpper",
                                accumulator("$substr", Arrays.asList("$title", 0, 1)))),
                        grouping("count", accumulator("$sum", 1)),
                        grouping("shows", accumulator("$push", "title")))
                .sort(ascending("_id"));
        Iterator<ShowLettersMorph> aggregate = pipe.aggregate(ShowLettersMorph.class);
        return StreamUtils.toStream(aggregate).collect(Collectors.toList());
    }

    public List<ShowMorph> getShowsByLetter(String letter) {
        Pattern r = Pattern.compile("^" + letter + ".*", Pattern.CASE_INSENSITIVE);
        return datastore.find(ShowMorph.class)
                .filter("title", r)
                .asList();
    }

}
