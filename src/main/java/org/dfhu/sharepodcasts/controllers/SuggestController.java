package org.dfhu.sharepodcasts.controllers;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.RouteManager;

import java.util.Collection;
import java.util.LinkedList;

import static spark.Spark.post;

public class SuggestController extends BaseController implements Controller{

    @Override
    public void setupRoutes() {
        post(RouteManager.suggest(), (req, res) -> {
            req.queryParams("q");

            return jsonStub();
        });
    }

    /** Stub */
    private String jsonStub() {
        Gson gson = new Gson();

        Collection<SuggestResponse> stuff = new LinkedList<>();

        stuff.add(new SuggestResponse("A Way with Words", "show"));
        stuff.add(new SuggestResponse("Giant Pool of Money", "episode"));
        stuff.add(new SuggestResponse("What we know", "episode"));

        return gson.toJson(stuff);
    }

    public static class SuggestResponse {
        // Name of the episode or show
        private String name;
        // if it is a episode or show
        private String type;

        SuggestResponse(String name, String type) {
            this.name = name;
            this.type = type;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
