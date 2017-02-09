package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.JsoupFeed;
import org.dfhu.sharepodcasts.RouteManager;
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
        JsoupFeed jsoupFeed = new JsoupFeed(url, doc);

        jsoupFeed.getEpisodes().stream().forEach(episode -> {
            String description = episode.description;
        });
    }
}
