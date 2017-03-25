package org.dfhu.sharepodcasts.service;

import com.mongodb.DuplicateKeyException;
import org.dfhu.sharepodcasts.JsoupFeed;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class FeedStore {

    private final Datastore datastore;
    private final ShowQuery showQuery;
    private final EpisodeQuery episodeQuery;

    public FeedStore(Datastore datastore, ShowQuery showQuery, EpisodeQuery episodeQuery) {
        this.datastore = datastore;
        this.showQuery = showQuery;
        this.episodeQuery = episodeQuery;
    }

    /**
     * Stores the the rss feed in the Datastore. This is a blocking operation.
     * @param showUrl - full your of the rss feed
     * @param usersIp - the ip from the Request
     * @return - title of the show added
     */
    public ShowMorph submit(String showUrl, String usersIp) throws IOException {
        JsoupFeed feed = getJsoupFeed(showUrl);

        final ShowMorph showMorph = new ShowMorph();
        showMorph.title = feed.getTitle();
        showMorph.url = feed.getUrl();
        showMorph.description = feed.getDescription();
        showMorph.copyright = feed.getCopyright();
        showMorph.showUrl = feed.getShowUrl();
        showMorph.author = feed.getAuthor();
        showMorph.timeStamp = System.currentTimeMillis();
        showMorph.ip = usersIp;

        try {
            datastore.save(showMorph);
        } catch(DuplicateKeyException e) {
        }

        feed.getEpisodes().stream().forEach(episode -> {
            episode.showId = showMorph.id;
            episode.showTitle = showMorph.title;
            try {
                datastore.save(episode);
            } catch (DuplicateKeyException e) {}
        });

        return showQuery.byUrl(showMorph.url).get();
    }

    /**
     * Update an existing feed
     * @param show - feed to update
     * @return
     */
    public List<String> updateFeed(final ShowMorph show) throws IOException {
        JsoupFeed feed = getJsoupFeed(show.url);
        final List<String> newEpisodes = new LinkedList<>();

        feed.getEpisodes().forEach(episode -> {
            episode.showId = show.id;
            episode.showTitle = show.title;
            try {
                datastore.save(episode);
                newEpisodes.add(episode.title);
            } catch (DuplicateKeyException e) {
                episodeQuery.updateFeedUniqueId(episode);
            }
        });

        return newEpisodes;
    }

    /**
     * Get the feed
     * @param showUrl - url of the feed
     * @return
     * @throws IOException
     */
    private JsoupFeed getJsoupFeed(String showUrl) throws IOException {
        InputStream inputStream = new URL(showUrl).openConnection().getInputStream();
        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());
        return new JsoupFeed(showUrl, doc);
    }

}
