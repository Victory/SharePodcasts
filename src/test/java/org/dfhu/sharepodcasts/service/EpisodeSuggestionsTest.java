package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.testutil.BaseMorphs;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class EpisodeSuggestionsTest {

    @Test
    public void returnsSameNumberOfSuggestionsAsEpisodeMorphs(){
        int targetRows = 5;
        String keyword = "keyword";

        List<EpisodeMorph> episodes = Stream.generate(() -> BaseMorphs.getEpisodeMorph1())
                .limit(targetRows)
                .collect(Collectors.toList());

        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        when(episodeQuery.textSearch(keyword)).thenReturn(episodes);

        EpisodeSuggestions episodeSuggestions = new EpisodeSuggestions(episodeQuery);
        List<EpisodeSuggestions.SuggestResponse> suggest = episodeSuggestions.suggest(keyword);
        assertEquals(targetRows, suggest.size());
    }
}
