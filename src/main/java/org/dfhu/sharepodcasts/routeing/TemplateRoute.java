package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateEngine;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * A TemplateRoute take uses morphs, templates, requests to perform business logic
 * and return a template modifying the response if needed.
 */
public abstract class TemplateRoute extends RouteAdder<TemplateRoute> implements Route {

    public abstract RockerModel getRockerModel(Request request, Response response);

    @Override
    public void doGet(RouteAdder<TemplateRoute> route) {
        Spark.get(
                getPath(),
                new RockerTemplateViewRoute(this),
                RockerTemplateEngine.getInstance());
    }

}
