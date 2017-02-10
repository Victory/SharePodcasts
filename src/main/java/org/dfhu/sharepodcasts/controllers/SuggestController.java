package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import org.bson.types.ObjectId;
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
        Gson gson = new Gson();

        Query<EpisodeMorph> query = DataProvider.get().createQuery(EpisodeMorph.class)
                .search(q)
                .order("pubDate");
        List<EpisodeMorph> episodes = query.asList();


        final Collection<SuggestResponse> results = new LinkedList<>();
        episodes.stream().forEach(e -> {
            results.add(new SuggestResponse(e.title, "show", e.id));
        });

        return gson.toJson(results);
    }

    public static class SuggestResponse {
        // Name of the episode or show
        private String name;
        // if it is a episode or show
        private String mediaType;
        // rowId
        private ObjectId rowId;

        SuggestResponse(String name, String type, ObjectId rowId) {
            this.name = name;
            this.mediaType = type;
            this.rowId = rowId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String type) {
            this.mediaType = type;
        }
    }
}
