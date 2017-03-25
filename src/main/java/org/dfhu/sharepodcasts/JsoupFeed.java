package org.dfhu.sharepodcasts;


import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JsoupFeed {

    private final Document doc;
    private final String url;

    public JsoupFeed(String url, Document doc) {
        this.url = url;
        this.doc = doc;
    }

    /**
     * The feed of the url
     */
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return fromChannel("title");
    }

    public String getDescription() {
        return fromChannel("description");
    }

    /**
     * The Human readable feed
     */
    public String getShowUrl() {
        return fromChannel("link");
    }

    public String getCopyright() {
        return fromChannel("copyright");
    }

    public String getAuthor() {
        return fromChannel("itunes:author");
    }

    /**
     * Get episode info, the consumer is expected to set the showId before inserting
     * @return - episodes, without the showId
     */
    public List<EpisodeMorph> getEpisodes() {
        Elements elms = doc.select("rss > channel item");
        final List<EpisodeMorph> items = new ArrayList<>();

        elms.forEach(elm -> {
            JsoupFeedItem item = new JsoupFeedItem(elm);
            EpisodeMorph episode = new EpisodeMorph();
            episode.url = item.getUrl();
            episode.title = item.getTitle();
            episode.pubDate = item.getPubDate();
            episode.description = item.getDescription();
            episode.uniqueId = item.getUniqueId();
            items.add(episode);
        });

        return items;
    }

    private String fromChannel(String what) {
        Elements found = doc.select("rss > channel");
        if (found.size() < 1) {
            return "";
        }
        found = found.get(0).getElementsByTag(what);
        if (found.size() < 1) {
            return "";
        }
        return found.get(0).text();
    }

}
