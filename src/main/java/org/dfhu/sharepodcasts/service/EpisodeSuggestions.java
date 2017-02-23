package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.LinkedList;
import java.util.List;

public class EpisodeSuggestions {

    private final Datastore datastore;

    public EpisodeSuggestions(Datastore datastore) {
        this.datastore = datastore;
    }

    /**
     * Get a list of suggestions. This is a blocking process.
     * @param keyword
     * @return
     */
    public List<SuggestResponse> suggest(String keyword) {
        Query<EpisodeMorph> query = datastore.createQuery(EpisodeMorph.class)
                .search(keyword)
                .order("pubDate");
        List<EpisodeMorph> episodes = query.asList();


        final List<SuggestResponse> results = new LinkedList<>();
        episodes.stream().forEach(e -> {
            results.add(new SuggestResponse(e.title, e.showTitle, e.id.toString()));
        });

        return results;
    }

    public static class SuggestResponse {
        // Name of the listen or show
        private final String name;
        // show Title
        private final String showTitle;
        // rowId
        private final String rowId;


        SuggestResponse(String name, String showTitle, String rowId) {
            this.name = name;
            this.showTitle = showTitle;
            this.rowId = rowId;
        }
    }
}
