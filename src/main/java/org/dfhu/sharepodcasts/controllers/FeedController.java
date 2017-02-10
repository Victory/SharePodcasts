package org.dfhu.sharepodcasts.controllers;

import com.mongodb.DuplicateKeyException;
import org.dfhu.sharepodcasts.JsoupFeed;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static spark.Spark.post;

public class FeedController extends BaseController implements Controller {
    @Override
    public void setupRoutes() {
        post(RouteManager.addFeed(), (req, res) -> {
            String url = req.queryParams("url");
            parseFeed(url);
            return "{\"success\": true, \"msg\": \"Feed added try searching for your episode now\"}";
        });
    }

    private static void parseFeed(String url) throws IOException {
        InputStream inputStream = new URL(url).openConnection().getInputStream();
        Document doc = Jsoup.parse(inputStream, "UTF-8", "", Parser.xmlParser());
        JsoupFeed feed = new JsoupFeed(url, doc);

        final ShowMorph showMorph = new ShowMorph();
        showMorph.title = feed.getTitle();
        showMorph.url = feed.getUrl();
        showMorph.description = feed.getDescription();

        try {
            DataProvider.get().save(showMorph);
        } catch(DuplicateKeyException e) {
        }

        feed.getEpisodes().stream().forEach(episode -> {
            episode.showId = showMorph.id;
            episode.showTitle = showMorph.title;
            try {
                DataProvider.get().save(episode);
            } catch (DuplicateKeyException e) {}
        });
    }
}
