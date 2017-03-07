package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.ClientSideErrorMorph;
import org.dfhu.sharepodcasts.routeing.BytesRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.util.StandardRequestLog;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.dfhu.sharepodcasts.routeing.Route.METHOD.GET;

public class JsErrorRoute extends BytesRoute implements Route {

    private static final String oneByOne64 =
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
    private static byte[] img =
            Base64.getDecoder().decode(oneByOne64.getBytes(StandardCharsets.UTF_8));

    private final AnalyticsStore analyticsStore;

    public JsErrorRoute(AnalyticsStore analyticsStore) {
        this.analyticsStore = analyticsStore;
    }

    @Override
    public String getPath() {
        return RouteManager.jsErrorPixel();
    }

    @Override
    public Route.METHOD getMethod() {
        return GET;
    }

    @Override
    public byte[] getBytes(Request req, Response res) {
        res.header("Content-type", "image/png");
        res.header("Content-Length", "" + img.length);
        res.header("Cache-control", "no-cache, no-store, must-revalidate");
        res.header("Pragma", "no-cache");
        res.header("Expires", "0");

        String pathname = req.queryParams("pathname");
        String msg = req.queryParams("msg");

        ClientSideErrorMorph log = StandardRequestLog.build(
                ClientSideErrorMorph.class,
                req,
                pathname);
        log.errorMessage = msg;
        analyticsStore.submit(log);

        return img;
    }
}
