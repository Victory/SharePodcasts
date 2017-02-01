package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.RouteManager;

import java.util.Collection;
import java.util.LinkedList;

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

        Collection<SuggestResponse> stuff = new LinkedList<>();

        if (!q.startsWith("n")) {
            stuff.add(new SuggestResponse("A Way with Words", "show", 1));
            stuff.add(new SuggestResponse("Giant Pool of Money", "episode", 3));
            stuff.add(new SuggestResponse("What we know", "episode", 2));
        }

        return gson.toJson(stuff);
    }

    public static class SuggestResponse {
        // Name of the episode or show
        private String name;
        // if it is a episode or show
        private String mediaType;
        // rowId
        private int rowId;

        SuggestResponse(String name, String type, int rowId) {
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
