package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
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
            ShowMorph show = feedStore.submit(url, req.ip());
            msg = "The Podcast \"" + show.title + "\" has been added. Try searching for an episode title now";
            success = true;
            ShowData showData = new ShowData();
            showData.id = show.id.toString();
            showData.title = show.title;

            return new JsonResponse(success, msg, showData);
        } catch (Throwable t) {
            success = false;
            msg = "There was an error parsing feed. Please check the URL and try again.";
            logger.error("Error parsing feed: " + url + " " + t.getMessage());
            return new JsonResponse(success, msg);
        }
    }

    public static class ShowData {
        String id;
        String title;
    }
}
