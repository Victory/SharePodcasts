package org.dfhu.sharepodcasts.controllers;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.finders.EpisodeFinder;
import org.dfhu.sharepodcasts.morphs.finders.ShowFinder;
import org.dfhu.sharepodcasts.views.listen.Listen;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.HaltException;
import spark.Request;
import spark.Spark;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ListenControllerTest extends StartSparkControllerTest {

    @Test(expected = HaltException.class)
    public void testBadIdGives404() {
        String target = "missingId";

        Request req = mock(Request.class);
        when(req.params(":rowId")).thenReturn(target);

        EpisodeFinder episodeFinder = mock(EpisodeFinder.class);
        when(episodeFinder.byId(target)).thenReturn(Optional.ofNullable(null));

        ListenController c = new ListenController(null, episodeFinder);
        c.listenTemplate(req);
    }

    @Test
    public void testGoodIdGivesListenTemplate() {
        String target = "goodId";
        ObjectId showId = new ObjectId();

        Request req = mock(Request.class);
        when(req.params(":rowId")).thenReturn(target);

        EpisodeFinder episodeFinder = mock(EpisodeFinder.class);
        EpisodeMorph episodeMorph = new EpisodeMorph();
        episodeMorph.showId = showId;
        Optional<EpisodeMorph> episode = Optional.of(episodeMorph);
        when(episodeFinder.byId(target)).thenReturn(episode);

        ShowFinder showFinder = mock(ShowFinder.class);
        Optional<ShowMorph> show = Optional.of(new ShowMorph());
        when(showFinder.byId(showId)).thenReturn(show);

        ListenController c = new ListenController(showFinder, episodeFinder);
        Listen listen = c.listenTemplate(req);
        assertNotNull(listen);
        assertThat(listen, instanceOf(Listen.class));
    }

    @Test
    public void testFullPage() throws Exception {

        String targetEpisodeUrl = "http://example.com/epsidoe.mp3";
        String targetRowId = "goodId";
        ObjectId showId = new ObjectId();

        ListenController c = getListenController(targetEpisodeUrl, targetRowId, showId);

        c.setupRoutes();

        Spark.awaitInitialization();
        SparkTestUtil.UrlResponse get = sparkTestUtil.doMethod("GET", "/l/" + targetRowId, null);
        assertTrue(get.body.contains(targetEpisodeUrl));
    }

    @Test
    public void testScriptTagIsClearedFromDescription() throws Exception {

        String targetEpisodeUrl = "http://example.com/epsidoe.mp3";
        String targetRowId = "goodId";
        ObjectId showId = new ObjectId();

        ListenController c = getListenController(targetEpisodeUrl, targetRowId, showId);
        c.setupRoutes();

        Spark.awaitInitialization();
        SparkTestUtil.UrlResponse get = sparkTestUtil.doMethod("GET", "/l/" + targetRowId, null);
        assertTrue(get.body.contains("Good Code"));
        assertTrue(!get.body.contains("<b>Good Code</b>"));
        assertTrue(!get.body.contains("alert(1)"));
    }

    private ListenController getListenController(String targetEpisodeUrl, String targetRowId, ObjectId showId) {
        Request req = mock(Request.class);
        when(req.params(":rowId")).thenReturn(targetRowId);

        EpisodeFinder episodeFinder = mock(EpisodeFinder.class);
        EpisodeMorph episodeMorph = new EpisodeMorph();
        episodeMorph.showId = showId;
        episodeMorph.title = "Episode Title";
        episodeMorph.showTitle = "The show";
        episodeMorph.url = targetEpisodeUrl;
        episodeMorph.description = "<script>alert(1)</script><b>Good Code</b>";
        Optional<EpisodeMorph> episode = Optional.of(episodeMorph);
        when(episodeFinder.byId(targetRowId)).thenReturn(episode);

        ShowFinder showFinder = mock(ShowFinder.class);
        ShowMorph showMorph = new ShowMorph();

        showMorph.url = "http://example.com/feed";
        showMorph.showUrl = "http://example.com";
        showMorph.author = "";
        showMorph.copyright = "";

        Optional<ShowMorph> show = Optional.of(showMorph);
        when(showFinder.byId(showId)).thenReturn(show);

        return new ListenController(showFinder, episodeFinder);
    }
}
