package org.dfhu.sharepodcasts.controllers;


import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.views.createsharelink.CreateShareLink;

public class CreateShareLinkController extends BaseController implements Controller {

    @Override
    public void setupRoutes() {
        /*
        doPost(RouteManager.createShareLink(), (req, res) -> {
            String rowId = req.queryParams("rowId");
            //CreateShareLink.template();

            return "";
        });
        */
    }
}
