package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.mongodb.morphia.query.Query;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static spark.Spark.post;

public class SuggestController extends BaseController implements Controller {

    @Override
    public void setupRoutes() {
        post(RouteManager.suggest(), (req, res) -> {
            String q = req.queryParams("q");
            return jsonStub(q);
        });
    }

    /**
     * Stub
     * @param q
     */
    private String jsonStub(String q) {
        // DEBUG
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        Query<EpisodeMorph> query = DataProvider.get().createQuery(EpisodeMorph.class)
                .search(q)
                .order("pubDate");
        List<EpisodeMorph> episodes = query.asList();


        final Collection<SuggestResponse> results = new LinkedList<>();
        episodes.stream().forEach(e -> {
            results.add(new SuggestResponse(e.title, e.showTitle, "show", e.id.toString()));
        });

        return gson.toJson(results);
    }

    public static class SuggestResponse {
        // Name of the listen or show
        private final String name;
        // show Title
        private final String showTitle;
        // if it is a listen or show
        private final String mediaType;
        // rowId
        private final String rowId;


        SuggestResponse(String name, String showTitle, String type, String rowId) {
            this.name = name;
            this.showTitle = showTitle;
            this.mediaType = type;
            this.rowId = rowId;
        }
    }
}
