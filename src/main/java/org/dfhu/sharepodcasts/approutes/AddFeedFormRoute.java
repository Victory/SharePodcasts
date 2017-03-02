package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import spark.Request;
import spark.Response;

public class AddFeedFormRoute extends TemplateRoute implements Route {
    @Override
    public String getPath() {
        return RouteManager.addFeedForm();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        return null;
    }
}
