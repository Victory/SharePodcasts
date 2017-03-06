package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.AbstractLog;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AnalyticsStore {
    private final Logger logger;
    private final Datastore datastore;

    public AnalyticsStore(Datastore datastore, Logger logger) {
        this.logger = logger;
        this.datastore = datastore;
    }

    private static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(
                    3,
                    10,
                    5,
                    TimeUnit.SECONDS,
                    new BlockingArrayQueue<>(100, 3));


    /**
     * Add the entry to be logged
     */
    public void submit(AbstractLog entry) {
        pool.execute(new SaveLog(entry, datastore, logger));
    }

    /**
     * Wait for all threads to finish
     * @param timeoutInSeconds - how long to wait before exception
     * @throws InterruptedException
     */
    public void awaitTermination(long timeoutInSeconds) throws InterruptedException {
        pool.awaitTermination(timeoutInSeconds, TimeUnit.SECONDS);
    }

    private static class SaveLog implements Runnable {
        private final AbstractLog entry;
        private final Logger logger;
        private final Datastore datastore;


        private SaveLog(AbstractLog entry, Datastore datastore, Logger logger) {
            this.entry = entry;
            this.datastore = datastore;
            this.logger = logger;
        }

        @Override
        public void run() {

            try {
                datastore.save(entry);
            } catch (RuntimeException t) {
                logger.error(t.getMessage());
                throw t;
            }
        }
    }
}
