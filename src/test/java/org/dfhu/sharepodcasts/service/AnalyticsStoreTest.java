package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.RequestLogMorph;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class AnalyticsStoreTest {

    @Test
    public void entryIsSaved() throws InterruptedException {

        Logger logger = mock(Logger.class);
        Datastore datastore = mock(Datastore.class);
        RequestLogMorph entity = new RequestLogMorph();
        entity.ip = "1.2.3.4";

        AnalyticsStore analyticsStore = new AnalyticsStore(datastore, logger);
        analyticsStore.submit(entity);
        analyticsStore.awaitTermination(1);

        verifyZeroInteractions(logger);
        verify(datastore, times(1)).save(entity);
    }

    @Test
    public void datastoreExceptionLogs() throws InterruptedException {

        Logger logger = mock(Logger.class);
        RequestLogMorph entity = new RequestLogMorph();
        entity.ip = "1.2.3.4";

        Datastore datastore = mock(Datastore.class);
        when(datastore.save(entity)).thenThrow(new RuntimeException("Error message"));
        AnalyticsStore analyticsStore = new AnalyticsStore(datastore, logger);
        analyticsStore.submit(entity);
        analyticsStore.awaitTermination(1);

        verify(datastore, times(1)).save(entity);
        verify(logger, times(1)).error("Error message");
    }
}
