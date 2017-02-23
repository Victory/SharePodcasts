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
    public void callsEpisodeSuggetionsWithQuery() {
        String expected = "some query";
        Request request = mock(Request.class);
        when(request.queryParams("q")).thenReturn(expected);
        EpisodeSuggestions episodeSuggestions = mock(EpisodeSuggestions.class);
        when(episodeSuggestions.suggest(expected)).thenReturn(EMPTY_LIST);

        EpisodeSuggestRoute episodeSuggestRoute =
                new EpisodeSuggestRoute(episodeSuggestions);
        episodeSuggestions.suggest(expected);

        verify(episodeSuggestions, times(1)).suggest(expected);
    }

}
