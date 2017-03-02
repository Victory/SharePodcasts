package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.viewmodels.HomeViewModel;
import org.dfhu.sharepodcasts.views.home.Home;
import spark.Request;
import spark.Response;

public class HomeRoute extends TemplateRoute implements Route {

    @Override
    public String getPath() {
        return RouteManager.home();
    }

    @Override
    public Route.METHOD getMethod(){
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        HomeViewModel vm = new HomeViewModel(vicSession);
        return Home.template(vm);
    }
}
