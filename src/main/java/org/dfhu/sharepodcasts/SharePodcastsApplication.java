package org.dfhu.sharepodcasts;

import org.apache.commons.logging.LogFactory;
import org.dfhu.sharepodcasts.approutes.*;
import org.dfhu.sharepodcasts.controllers.*;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.service.FeedStore;
import org.mongodb.morphia.Datastore;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SharePodcastsApplication {

    public static void init(Datastore datastore) {

        List<Controller> controllerList = new ArrayList<>();

        ShowQuery showQuery = new ShowQuery(datastore);
        EpisodeQuery episodeQuery = new EpisodeQuery(datastore);
        ShareQuery shareQuery = new ShareQuery(datastore);
        AnalyticsStore analyticsStore = new AnalyticsStore(
                datastore, LoggerFactory.getLogger(AnalyticsStore.class));
        FeedStore feedStore = new FeedStore(datastore);

        addRoute(new AnalyticsRoute(analyticsStore));

        addRoute(new CreateShareLinkRoute(showQuery, episodeQuery));
        addRoute(new AddFeedRoute(feedStore, LoggerFactory.getLogger(AddFeedRoute.class)));
        addRoute(new HomeRoute());

        controllerList.add(new LegalController());
        addRoute(new ListenRoute(showQuery, episodeQuery));
        controllerList.add(new SaveShareLinkController(episodeQuery, shareQuery));
        controllerList.add(new SuggestController());

        SharePodcastsApplication.setupRoutes(controllerList);
    }

    private static void addRoute(Route route) {
        route.addRoute();
    }

    private static void setupRoutes(Collection<Controller> controllers) {
        controllers.stream().forEach(c -> c.setupRoutes());
    }

}
