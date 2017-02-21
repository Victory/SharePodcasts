package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.junit.Test;
import spark.Spark;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ShareLinkControllerTest extends StartSparkControllerTest {

    @Test
    public void badEpisodeIdGives404() throws Exception {
        EpisodeQuery episodeQuery = mock(EpisodeQuery.class);
        when(episodeQuery.byId(any())).thenReturn(Optional.empty());

        SaveShareLinkController c = new SaveShareLinkController(episodeQuery, null);
        c.setupRoutes();

        SparkTestUtil.UrlResponse post =
                sparkTestUtil.doMethod("POST", RouteManager.saveShareLink(), "episodId=1");
    }
}
