package org.dfhu.sharepodcasts.routeing;

import spark.Request;
import spark.Response;
import spark.Spark;


public abstract class DirectRoute extends RouteAdder<DirectRoute> implements Route {
    public abstract byte[] getBody(Request req, Response res);

    @Override
    public void doGet(RouteAdder<DirectRoute> routeAdder) {
        Spark.get(getPath(), this::getBody);
    }
}
