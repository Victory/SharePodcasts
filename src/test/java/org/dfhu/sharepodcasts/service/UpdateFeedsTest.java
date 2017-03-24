package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UpdateFeedsTest {
    @Test
    public void updateNextShow() throws IOException {
        ShowMorph showMorph = mock(ShowMorph.class);
        FeedStore feedStore = mock(FeedStore.class);
        Datastore datastore = mock(Datastore.class);
        UpdateFeeds.UpdateNextShow updateNextShow =
                new UpdateFeeds.UpdateNextShow(showMorph, feedStore, datastore);
        updateNextShow.run();
        verify(feedStore, times(1)).updateFeed(showMorph);
    }
}