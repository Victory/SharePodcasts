package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateModel;
import org.dfhu.sharepodcasts.viewmodels.HomeViewModel;
import org.dfhu.sharepodcasts.views.home.Home;

public class HomeController extends BaseController implements Controller {

    @Override
    public void setupRoutes() {
        doGet(RouteManager.getHomeRoute(), (req, res) -> {
            HomeViewModel vm = new HomeViewModel();
            Home template = Home.template("Share a Podcast", vm);
            return new RockerTemplateModel(template);
        });
    }
}
