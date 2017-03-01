package org.dfhu.sharepodcasts.viewmodels;

import spark.Request;
import spark.Response;

public class ViewModelUtil {
    public static class Noop extends AbstractViewModel implements ViewModel {
        public Noop(Request req, Response res) {
            super(req, res);
        }
    }
}
