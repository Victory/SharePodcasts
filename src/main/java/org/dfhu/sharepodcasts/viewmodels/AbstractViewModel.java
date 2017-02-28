package org.dfhu.sharepodcasts.viewmodels;

import spark.Request;
import spark.Response;

abstract class AbstractViewModel {
    private final Response res;
    private final Request req;

    public AbstractViewModel(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    public boolean isAjax() {
        String ajax = req.queryParams("ajax");
        return ajax != null && ajax.equals("1");
    }

}
