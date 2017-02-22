package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.approutes.AddFeedRoute;
import org.dfhu.sharepodcasts.service.FeedStore;
import org.junit.Test;
import org.slf4j.Logger;
import spark.Request;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class AddFeedRouteTest {

    @Test
    public void submitsUrl() throws IOException {
        String url = "http://example.com/feed/";

        FeedStore feedStore = mock(FeedStore.class);
        Logger logger = mock(Logger.class);
        AddFeedRoute addFeedRoute = new AddFeedRoute(feedStore, logger);
        Request req = mock(Request.class);
        when(req.queryParams("url")).thenReturn(url);

        addFeedRoute.getGsonable(req, null);
        verify(feedStore, times(1)).submit(url);
    }

    @Test
    public void isPOST() throws IOException {
        FeedStore feedStore = mock(FeedStore.class);
        Logger logger = mock(Logger.class);
        AddFeedRoute addFeedRoute = new AddFeedRoute(feedStore, logger);
        assertTrue(addFeedRoute.getMethod() == Route.METHOD.POST);
    }
}
