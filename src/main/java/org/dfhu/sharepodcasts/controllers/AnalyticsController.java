package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.RequestLogAnalytics;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AnalyticsController extends BaseController implements Controller {
    private static final String oneByOne64 =
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";

    private static byte[] img;

    private static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(
                    3,
                    10,
                    5,
                    TimeUnit.SECONDS,
                    new BlockingArrayQueue<>(100, 3));


    public AnalyticsController() {
        img = Base64.getDecoder().decode(oneByOne64.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void setupRoutes() {
        Spark.get("/onebyone", (req, res) -> {
            res.header("Content-type", "image/png");
            res.header("Content-Length", "" + img.length);
            res.header("Cache-control", "no-cache, no-store, must-revalidate");
            res.header("Pragma", "no-cache");
            res.header("Expires", "0");

            RequestLogAnalytics log = new RequestLogAnalytics();
            log.ip = req.ip();
            log.timeStamp = System.currentTimeMillis();
            log.userAgent = req.userAgent();
            log.headers = new Gson().toJson(req.headers());
            pool.execute(new SaveLog(log));

            return img;
        });
    }

    private static class SaveLog implements Runnable {
        private static final Logger LOG = LoggerFactory.getLogger(SaveLog.class);
        private final RequestLogAnalytics entry;

        private SaveLog(RequestLogAnalytics entry) {
            this.entry = entry;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                DataProvider.get().save(entry);
            } catch (Throwable t) {
                LOG.error(t.getMessage());
                throw t;
            }
        }
    }
}
