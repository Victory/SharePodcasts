package org.dfhu.sharepodcasts;

import org.dfhu.sharepodcasts.approutes.*;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Halting;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.*;
import org.mongodb.morphia.Datastore;
import org.slf4j.LoggerFactory;


public class SharePodcastsApplication {

    public static void init(Datastore datastore) {

        ShowQuery showQuery = new ShowQuery(datastore);
        EpisodeQuery episodeQuery = new EpisodeQuery(datastore);
        ShareQuery shareQuery = new ShareQuery(datastore);

        AnalyticsStore analyticsStore = new AnalyticsStore(
                datastore, LoggerFactory.getLogger(AnalyticsStore.class));
        FeedStore feedStore = new FeedStore(datastore, showQuery, episodeQuery);
        EpisodeSuggestions episodeSuggestions = new EpisodeSuggestions(episodeQuery);
        ShortLinkCreator shortLinkCreator =
                new ShortLinkCreator(shareQuery, LoggerFactory.getLogger(ShortLinkCreator.class));

        Halting.bindInternalServerError(analyticsStore);

        // Setup all the routes
        addRoute(new ContactFormRoute());
        addRoute(new ContactFormSaveRoute(analyticsStore));

        addRoute(new AddFeedFormRoute());
        addRoute(new AnalyticsRoute(analyticsStore));
        addRoute(new BrowseHomeRoute(showQuery));
        addRoute(new BrowseLetterRoute(showQuery));
        addRoute(new BrowseEpisodesRoute(episodeQuery));
        addRoute(new CreateShareLinkRoute(showQuery, episodeQuery));
        addRoute(new AddFeedRoute(feedStore, LoggerFactory.getLogger(AddFeedRoute.class)));
        addRoute(new HomeRoute());
        addRoute(new PrivacyPolicyRoute());
        addRoute(new JsErrorRoute(analyticsStore));
        addRoute(new ListenRoute(showQuery, episodeQuery, shareQuery));

        addRoute(new SaveShareLinkRoute(
                shortLinkCreator,
                shareQuery,
                LoggerFactory.getLogger(SaveShareLinkRoute.class)));

        addRoute(new EpisodeSuggestRoute(episodeSuggestions));

        // Start UpdateFeeds Service
        UpdateFeeds updateFeeds = new UpdateFeeds(showQuery, feedStore, datastore);
        updateFeeds.init();
    }

    private static void addRoute(Route route) {
        route.addRoute();
    }

}
