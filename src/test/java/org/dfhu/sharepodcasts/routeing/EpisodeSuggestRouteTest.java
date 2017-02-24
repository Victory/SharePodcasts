package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.approutes.EpisodeSuggestRoute;
import org.dfhu.sharepodcasts.service.EpisodeSuggestions;
import org.junit.Test;
import spark.Request;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class EpisodeSuggestRouteTest {

    @Test
    public void callsEpisodeSuggestionsWithQuery() {
        String expected = "some query";
        Request req = mock(Request.class);
        when(req.queryParams("q")).thenReturn(expected);
        EpisodeSuggestions episodeSuggestions = mock(EpisodeSuggestions.class);
        when(episodeSuggestions.suggest(expected)).thenReturn(EMPTY_LIST);

        EpisodeSuggestRoute episodeSuggestRoute =
                new EpisodeSuggestRoute(episodeSuggestions);
        episodeSuggestRoute.getGsonable(req, null);

        verify(episodeSuggestions, times(1)).suggest(expected);
    }

}
