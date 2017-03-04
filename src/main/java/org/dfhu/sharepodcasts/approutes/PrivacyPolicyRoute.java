package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.viewmodels.ViewModelUtil;
import org.dfhu.sharepodcasts.views.legal.Privacy;
import spark.Request;
import spark.Response;

public class PrivacyPolicyRoute extends TemplateRoute implements Route {

    @Override
    public String getPath() {
        return RouteManager.privacyPolicy();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        return Privacy.template(new ViewModelUtil.Noop(req, res, vicSession));
    }
}
