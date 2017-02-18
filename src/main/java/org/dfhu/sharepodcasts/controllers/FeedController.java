package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import com.mongodb.DuplicateKeyException;
import org.dfhu.sharepodcasts.JsoupFeed;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.viewmodels.JsonResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.post;

public class FeedController extends BaseController implements Controller {
    private static final Logger LOG = LoggerFactory.getLogger(FeedController.class);

    @Override
    public void setupRoutes() {
        post(RouteManager.addFeed(), (req, res) -> {
            String url = req.queryParams("url");
            boolean success = false;
            String msg;

            LOG.info("Adding feed: " + url);
            try {
                String title = parseFeed(url);
                msg = "The Podcast \"" + title + "\" has been added. Try searching for an episode title now";
                success = true;
            } catch (Throwable t) {
                success = false;
                msg = "There was an error parsing feed. Please check the URL and try again.";
                LOG.error("Error parsing feed: " + url + " " + t.getMessage());
            }
            LOG.info("Feed added: " + url);

            JsonResponse jsonResponse = new JsonResponse(success, msg);
            return new Gson().toJson(jsonResponse);
        });
    }

    private static String parseFeed(String url) throws IOException {
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

        return showMorph.title;
    }
}
