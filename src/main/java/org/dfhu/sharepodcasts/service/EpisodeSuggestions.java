package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EpisodeSuggestions {

    private final EpisodeQuery episodeQuery;

    public EpisodeSuggestions(EpisodeQuery episodeQuery) {
        this.episodeQuery = episodeQuery;
    }

    /**
     * Get a list of suggestions. This is a blocking process.
     * @param keyword
     * @return
     */
    public List<SuggestResponse> suggest(String keyword) {
        List<EpisodeMorph> episodes = episodeQuery.textSearch(keyword);

        List<SuggestResponse> results = episodes.stream().map(e ->
                new SuggestResponse(e.title, e.showTitle, e.id.toString()))
                .collect(Collectors.toList());

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
