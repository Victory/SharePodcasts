package org.dfhu.sharepodcasts.routeing;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.approutes.ListenRoute;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.testutil.BaseMorphs;
import org.junit.Ignore;
import org.junit.Test;
import spark.HaltException;
import spark.Request;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ListenRouteTest {

    @Test(expected = HaltException.class)
    public void testBadIdGives404() {
        String target = "missingId";

        Request req = mock(Request.class);
        when(req.params(":rowId")).thenReturn(target);

        VicSession vicSession = new VicSession(req, null);
        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        when(episodeQuery.byId(target)).thenReturn(Optional.empty());

        ListenRoute listenRoute = new ListenRoute(null, episodeQuery, null);
        listenRoute.getRockerModel(req, null, vicSession);
    }

    @Test
    public void testFullPage() throws Exception {

        String targetEpisodeUrl = "http://example.com/epsidoe.mp3";
        String targetRowId = "goodId";
        ObjectId showId = new ObjectId();

        Request req = mock(Request.class);
        when(req.params(":rowId")).thenReturn(targetRowId);
        VicSession vicSession = new VicSession(req, null);

        ListenRoute listenRoute = getListenRoute(targetEpisodeUrl, targetRowId, showId);
        String actual = listenRoute.getRockerModel(req, null, vicSession).render().toString();

        assertTrue(actual.contains(targetEpisodeUrl));
    }

    @Test
    @Ignore
    public void UsesShareLinkWhenShortLink() {

    }

    private ListenRoute getListenRoute(String targetEpisodeUrl, String targetRowId, ObjectId showId) {

        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        EpisodeMorph episodeMorph = BaseMorphs.getEpisodeMorph1();
        episodeMorph.showId = showId;
        episodeMorph.url = targetEpisodeUrl;
        Optional<EpisodeMorph> episode = Optional.of(episodeMorph);
        when(episodeQuery.byId(targetRowId)).thenReturn(episode);

        ShowQuery showQuery = mock(ShowQuery.class);
        ShowMorph showMorph = BaseMorphs.getShowMorph1();

        Optional<ShowMorph> show = Optional.of(showMorph);
        when(showQuery.byId(showId)).thenReturn(show);

        return new ListenRoute(showQuery, episodeQuery, null);
    }
}
