package org.dfhu.sharepodcasts.routeing;

import com.mongodb.DuplicateKeyException;
import org.bson.BsonDocument;
import org.dfhu.sharepodcasts.approutes.SaveShareLinkRoute;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.service.ShortLinkCreator;
import org.dfhu.sharepodcasts.testutil.BaseMorphs;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SaveShareLinkRouteTest {

    @Test
    public void testRedirects() {
        String expected = "abcd1234";

        EpisodeMorph episodeMorph1 = BaseMorphs.getEpisodeMorph1();

        Request req = mock(Request.class);
        when(req.queryParams("comment")).thenReturn("my comment");
        when(req.queryParams("episodeId")).thenReturn(episodeMorph1.id.toString());

        ShareQuery shareQuery = mock(ShareQuery.class);

        ShortLinkCreator shortLinkCreator = mock(ShortLinkCreator.class);
        when(shortLinkCreator.create()).thenReturn(expected);

        Response res = mock(Response.class);

        SaveShareLinkRoute saveShareLinkRoute =
                new SaveShareLinkRoute(shortLinkCreator, shareQuery, null);
        saveShareLinkRoute.updateResponse(req, res);

        verify(res, times(1)).redirect("/l/" + expected);
    }

    @Test
    public void savesOnceOnNoThrow() {
        String expected = "abcd1234";

        EpisodeMorph episodeMorph1 = BaseMorphs.getEpisodeMorph1();

        Request req = mock(Request.class);
        when(req.queryParams("comment")).thenReturn("my comment");
        when(req.queryParams("episodeId")).thenReturn(episodeMorph1.id.toString());

        ShareQuery shareQuery = mock(ShareQuery.class);

        ShortLinkCreator shortLinkCreator = mock(ShortLinkCreator.class);
        when(shortLinkCreator.create()).thenReturn(expected);

        Response res = mock(Response.class);

        SaveShareLinkRoute saveShareLinkRoute =
                new SaveShareLinkRoute(shortLinkCreator, shareQuery, null);
        saveShareLinkRoute.updateResponse(req, res);

        ArgumentCaptor<ShareMorph> captor =
                ArgumentCaptor.forClass(ShareMorph.class);

        verify(shareQuery, times(1)).save(captor.capture());
        assertEquals(episodeMorph1.id, captor.getValue().episodeId);
    }


    @Test
    public void savesOnceOnWithThrow() {
        final String expected = "abcd1234";

        Logger logger = mock(Logger.class);

        EpisodeMorph episodeMorph1 = BaseMorphs.getEpisodeMorph1();

        Request req = mock(Request.class);
        when(req.queryParams("comment")).thenReturn("my comment");
        when(req.queryParams("episodeId")).thenReturn(episodeMorph1.id.toString());

        ShareQuery shareQuery = mock(ShareQuery.class);

        ShortLinkCreator shortLinkCreator = mock(ShortLinkCreator.class);
        when(shortLinkCreator.create()).thenAnswer(new Answer<String>() {
            int count = 0;

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                if (count == 0) {
                    count += 1;
                    throw new DuplicateKeyException(new BsonDocument(), null, null);
                }
                return expected;
            }
        });

        Response res = mock(Response.class);

        SaveShareLinkRoute saveShareLinkRoute =
                new SaveShareLinkRoute(shortLinkCreator, shareQuery, logger);
        saveShareLinkRoute.updateResponse(req, res);

        ArgumentCaptor<ShareMorph> captor =
                ArgumentCaptor.forClass(ShareMorph.class);

        verify(shareQuery, times(1)).save(captor.capture());
        assertEquals(episodeMorph1.id, captor.getValue().episodeId);
        verify(logger, times(1)).error(any());
    }
}
