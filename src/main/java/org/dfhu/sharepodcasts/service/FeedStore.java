package org.dfhu.sharepodcasts.service;

import com.mongodb.DuplicateKeyException;
import org.dfhu.sharepodcasts.JsoupFeed;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FeedStore {

    private final Datastore datastore;
    private final ShowQuery showQuery;

    public FeedStore(Datastore datastore, ShowQuery showQuery) {
        this.datastore = datastore;
        this.showQuery = showQuery;
    }

    /**
     * Stores the the rss feed in the Datastore. This is a blocking operation.
     * @param url - full your of the rss feed
     * @param usersIp - the ip from the Request
     * @return - title of the show added
     */
    public ShowMorph submit(String url, String usersIp) throws IOException {
        InputStream inputStream = new URL(url).openConnection().getInputStream();
        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());
        JsoupFeed feed = new JsoupFeed(url, doc);

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
}
