package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.RequestLogMorph;
import org.dfhu.sharepodcasts.routeing.BytesRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.util.StandardRequestLog;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AnalyticsRoute extends BytesRoute implements Route {

    private static final String oneByOne64 =
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
    private static byte[] img =
            Base64.getDecoder().decode(oneByOne64.getBytes(StandardCharsets.UTF_8));

    private final AnalyticsStore analyticsStore;

    public AnalyticsRoute(AnalyticsStore analyticsStore) {
        this.analyticsStore = analyticsStore;
    }

    @Override
    public String getPath() {
        return RouteManager.analyticsPixel();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public byte[] getBytes(Request req, Response res) {
        res.header("Content-type", "image/png");
        res.header("Content-Length", "" + img.length);
        res.header("Cache-control", "no-cache, no-store, must-revalidate");
        res.header("Pragma", "no-cache");
        res.header("Expires", "0");

        String pathname = req.queryParams("pathname");

        RequestLogMorph log = StandardRequestLog.build(
                RequestLogMorph.class,
                req,
                pathname);
        log.referrer = req.queryParams("referrer");
        analyticsStore.submit(log);

        return img;
    }
}
