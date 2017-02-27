package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.approutes.BrowseEpisodesRoute;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.testutil.BaseMorphs;
import org.dfhu.sharepodcasts.viewmodels.BrowseEpisodesViewModel;
import org.dfhu.sharepodcasts.views.browse.BrowseEpisodes;
import org.junit.Test;
import spark.Request;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class BrowseEpisodesRouteTest {
    @Test
    public void hasAllEpisodes() {
        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);

        Request req = mock(Request.class);
        when(req.params("id")).thenReturn(BaseMorphs.show1Id.toString());

        List<EpisodeMorph> episodes = IntStream.range(0, 3)
                .mapToObj(ii -> {
                    EpisodeMorph episodeMorph = new EpisodeMorph();
                    episodeMorph.title = "Episode #" + ii;
                    return episodeMorph;
                }).collect(Collectors.toList());

        when(episodeQuery.byShowId(BaseMorphs.show1Id.toString())).thenReturn(episodes);

        BrowseEpisodesRoute browseEpisodesRoute =
                new BrowseEpisodesRoute(episodeQuery);
        BrowseEpisodes browseEpisodes = (BrowseEpisodes) browseEpisodesRoute.getRockerModel(req, null);
        verify(req, times(1)).params("id");

        assertEquals(3, browseEpisodes.vm().getEpisodes().size());
        assertEquals("Episode #0", browseEpisodes.vm().getEpisodes().get(0).title);
    }
}
