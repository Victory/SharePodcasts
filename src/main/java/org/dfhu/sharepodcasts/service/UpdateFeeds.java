package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.FeedUpdateMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.mongodb.morphia.Datastore;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UpdateFeeds {
    private static final int STATUS_OK = 0x01;
    private static final int STATUS_FAIL = 0x10;

    private static final String SHOW_START = "show_start";
    private static final String SHOW_END = "show_end";
    private static final String LOOP_START = "loop_start";
    private static final String LOOP_END = "loop_end";

    private final ShowQuery showQuery;
    private final FeedStore feedStore;
    private final Datastore datastore;

    private ExecutorService pool = new ThreadPoolExecutor(
            1,
            10,
            30,
            TimeUnit.SECONDS,
            new BlockingArrayQueue<>(100));

    public UpdateFeeds(ShowQuery showQuery, FeedStore feedStore, Datastore datastore) {
        this.showQuery = showQuery;
        this.feedStore = feedStore;
        this.datastore = datastore;
    }

    public void init() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                start();
            }
        };

        Timer timer = new Timer(true);

        // don't hammer server when restarting in dev mode
        boolean dev = System.getProperty("dev").equals("true");
        int delay = (dev) ? 1000 * 60 * 6 : 1000 * 120;
        timer.scheduleAtFixedRate(timerTask, delay, 1000 * 60 * 120);
    }

    private void start() {
        List<ShowMorph> allShows = showQuery.all();

        // Log start of loop
        FeedUpdateMorph feedUpdateMorph = new FeedUpdateMorph();
        feedUpdateMorph.timeStamp = System.currentTimeMillis();
        feedUpdateMorph.humanDate = new Date().toString();
        feedUpdateMorph.action = LOOP_START;
        datastore.save(feedUpdateMorph);

        allShows.forEach(showMorph -> {
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                return;
            }

            UpdateNextShow updateNextShow = new UpdateNextShow(showMorph, feedStore, datastore);
            pool.execute(updateNextShow);
        });

        // Log end of loop
        feedUpdateMorph = new FeedUpdateMorph();
        feedUpdateMorph.timeStamp = System.currentTimeMillis();
        feedUpdateMorph.humanDate = new Date().toString();
        feedUpdateMorph.action = LOOP_END;
        datastore.save(feedUpdateMorph);
    }


    private class UpdateNextShow implements Runnable {
        private final ShowMorph show;
        private final FeedStore feedStore;
        private final Datastore datastore;

        UpdateNextShow(ShowMorph showMorph, FeedStore feedStore, Datastore datastore) {
            this.feedStore = feedStore;
            this.show = showMorph;
            this.datastore = datastore;
        }

        @Override
        public void run() {
            FeedUpdateMorph feedUpdateMorph = new FeedUpdateMorph();
            feedUpdateMorph.showTitle = show.title;
            feedUpdateMorph.action = SHOW_START;
            feedUpdateMorph.timeStamp = System.currentTimeMillis();
            feedUpdateMorph.humanDate = new Date().toString();
            datastore.save(feedUpdateMorph);

            try {
                feedUpdateMorph.episodes =
                        feedStore.updateFeed(show);
                feedUpdateMorph.status = STATUS_OK;
            } catch (IOException e) {
                feedUpdateMorph.status = STATUS_FAIL;
            } finally {
                feedUpdateMorph.action = SHOW_END;
                feedUpdateMorph.timeStamp = System.currentTimeMillis();
                feedUpdateMorph.humanDate = new Date().toString();
                datastore.save(feedUpdateMorph);
            }
        }
    }
}
