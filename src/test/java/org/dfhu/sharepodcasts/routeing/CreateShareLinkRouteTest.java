package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.approutes.CreateShareLinkRoute;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.junit.Test;
import org.dfhu.sharepodcasts.approutes.AnalyticsRoute;
import org.dfhu.sharepodcasts.morphs.RequestLogAnalytics;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.mockito.ArgumentCaptor;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.dfhu.sharepodcasts.testutil.BaseMorphs.episode1Id;
import static org.dfhu.sharepodcasts.testutil.BaseMorphs.getEpisodeMorph1;
import static org.dfhu.sharepodcasts.testutil.BaseMorphs.getShowMorph1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class CreateShareLinkRouteTest {

    @Test(expected = HaltException.class)
    public void halt404OnBadEpisodeId() {

        Request req = mock(Request.class);
        when(req.queryParams("rowId")).thenReturn(episode1Id.toString());

        Optional<EpisodeMorph> episode = Optional.empty();
        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        when(episodeQuery.byId(episode1Id.toString())).thenReturn(episode);

        CreateShareLinkRoute createShareLinkRoute
                = new CreateShareLinkRoute(null, episodeQuery);

        createShareLinkRoute.getRockerModel(req, null);
    }

    @Test
    public void canRender() {

        Request req = mock(Request.class);
        when(req.queryParams("rowId")).thenReturn(episode1Id.toString());

        EpisodeMorph episodeMorph = getEpisodeMorph1();
        Optional<EpisodeMorph> episode = Optional.of(episodeMorph);
        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        when(episodeQuery.byId(episode1Id.toString())).thenReturn(episode);

        ShowMorph showMorph = getShowMorph1();
        Optional<ShowMorph> show = Optional.of(showMorph);
        ShowQuery showQuery = mock(ShowQuery.class);
        when(showQuery.byId(show.get().id)).thenReturn(show);

        CreateShareLinkRoute createShareLinkRoute
                = new CreateShareLinkRoute(showQuery, episodeQuery);

        assertTrue(createShareLinkRoute.getMethod() == Route.METHOD.POST);

        RockerModel model = createShareLinkRoute.getRockerModel(req, null);
        String body = model.render().toString();
        assertTrue(body.contains(episode1Id.toString()));

    }
}
