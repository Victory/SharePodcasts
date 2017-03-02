package org.dfhu.sharepodcasts;

import spark.Request;
import spark.Response;

public class VicSession {

    private final Response res;
    private final Request req;

    public VicSession(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    public boolean isAjax() {
        String ajax = req.queryParams("ajax");
        return ajax != null && ajax.equals("1");
    }
}
