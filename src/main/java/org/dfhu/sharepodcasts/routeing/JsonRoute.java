package org.dfhu.sharepodcasts.routeing;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;

public abstract class JsonRoute extends RouteAdder<JsonRoute> implements Route{

    /** Get an object that you can Gson.toJson() */
    public abstract JsonResponse getJsonResponse(Request req, Response res);

    @Override
    public void doPost(RouteAdder<JsonRoute> routeAdder) {
        Spark.post(getPath(),
                (req, res) -> new Gson().toJson(getJsonResponse(req, res)));

    }
}
