package org.dfhu.sharepodcasts.approutes;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.ContactMorph;
import org.dfhu.sharepodcasts.routeing.JsonResponse;
import org.dfhu.sharepodcasts.routeing.JsonRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import spark.Request;
import spark.Response;

public class ContactFormSaveRoute extends JsonRoute implements Route {

    private final AnalyticsStore analyticsStore;

    public ContactFormSaveRoute(AnalyticsStore analyticsStore) {
        this.analyticsStore = analyticsStore;
    }

    @Override
    public String getPath() {
        return RouteManager.contactFormSave();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.POST;
    }

    @Override
    public JsonResponse getJsonResponse(Request req, Response res) {
        String email = req.queryParams("email");
        String msg = req.queryParams("msg");
        String name = req.queryParams("name");

        ContactMorph contactMorph = new ContactMorph();
        contactMorph.populateCommon(req, req.pathInfo());
        contactMorph.msg = msg;
        contactMorph.email = email;
        contactMorph.name = name;
        analyticsStore.submit(contactMorph);

        return new JsonResponse(true, "submited", null);
    }
}
