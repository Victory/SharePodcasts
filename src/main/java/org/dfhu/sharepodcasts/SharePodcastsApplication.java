package org.dfhu.sharepodcasts;

import org.dfhu.sharepodcasts.approutes.*;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.service.EpisodeSuggestions;
import org.dfhu.sharepodcasts.service.FeedStore;
import org.dfhu.sharepodcasts.service.ShortLinkCreator;
import org.mongodb.morphia.Datastore;
import org.slf4j.LoggerFactory;

public class SharePodcastsApplication {

    public static void init(Datastore datastore) {

        ShowQuery showQuery = new ShowQuery(datastore);
        EpisodeQuery episodeQuery = new EpisodeQuery(datastore);
        ShareQuery shareQuery = new ShareQuery(datastore);

        AnalyticsStore analyticsStore = new AnalyticsStore(
                datastore, LoggerFactory.getLogger(AnalyticsStore.class));
        FeedStore feedStore = new FeedStore(datastore);
        EpisodeSuggestions episodeSuggestions = new EpisodeSuggestions(episodeQuery);
        ShortLinkCreator shortLinkCreator =
                new ShortLinkCreator(shareQuery, LoggerFactory.getLogger(ShortLinkCreator.class));

        // Setup all the routes
        addRoute(new HomeRoute());
        addRoute(new AnalyticsRoute(analyticsStore));
        addRoute(new BrowseHomeRoute(showQuery));

        addRoute(new CreateShareLinkRoute(showQuery, episodeQuery));
        addRoute(new AddFeedRoute(feedStore, LoggerFactory.getLogger(AddFeedRoute.class)));
        addRoute(new PrivacyPolicyRoute());

        addRoute(new ListenRoute(showQuery, episodeQuery, shareQuery));

        addRoute(new SaveShareLinkRoute(
                shortLinkCreator,
                shareQuery,
                LoggerFactory.getLogger(SaveShareLinkRoute.class)));

        addRoute(new EpisodeSuggestRoute(episodeSuggestions));

    }

    private static void addRoute(Route route) {
        route.addRoute();
    }

}
