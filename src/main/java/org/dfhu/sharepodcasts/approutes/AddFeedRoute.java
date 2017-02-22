package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.routeing.JsonResponse;
import org.dfhu.sharepodcasts.routeing.JsonRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.FeedStore;
import org.slf4j.Logger;
import spark.Request;
import spark.Response;

public class AddFeedRoute extends JsonRoute implements Route {

    private final FeedStore feedStore;
    private final Logger logger;

    public AddFeedRoute(FeedStore feedStore, Logger logger) {
        this.feedStore = feedStore;
        this.logger = logger;
    }


    @Override
    public String getPath() {
        return RouteManager.addFeed();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.POST;
    }

    @Override
    public JsonResponse getGsonable(Request req, Response res) {
        String url = req.queryParams("url");
        boolean success = false;

        String msg;
        logger.info("Adding feed: " + url);
        try {
            String title = feedStore.submit(url);
            msg = "The Podcast \"" + title + "\" has been added. Try searching for an episode title now";
            success = true;
        } catch (Throwable t) {
            success = false;
            msg = "There was an error parsing feed. Please check the URL and try again.";
            logger.error("Error parsing feed: " + url + " " + t.getMessage());
        }
        logger.info("Feed added: " + url);

        return new JsonResponse(success, msg);
    }
}
