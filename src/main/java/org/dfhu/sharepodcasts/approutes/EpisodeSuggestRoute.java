package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.routeing.JsonResponse;
import org.dfhu.sharepodcasts.routeing.JsonRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.EpisodeSuggestions;
import spark.Request;
import spark.Response;

import java.util.List;

public class EpisodeSuggestRoute extends JsonRoute implements Route {

    private final EpisodeSuggestions episodeSuggestions;

    public EpisodeSuggestRoute(EpisodeSuggestions episodeSuggestions) {
        this.episodeSuggestions = episodeSuggestions;
    }

    @Override
    public String getPath() {
        return RouteManager.suggest();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.POST;
    }

    @Override
    public JsonResponse getGsonable(Request req, Response res) {
        String keyword = req.queryParams("q");
        List<EpisodeSuggestions.SuggestResponse> suggestions
                = episodeSuggestions.suggest(keyword);

        return new JsonResponse(true, "", suggestions);
    }
}
