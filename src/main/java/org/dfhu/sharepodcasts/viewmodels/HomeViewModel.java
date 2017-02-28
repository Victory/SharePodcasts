package org.dfhu.sharepodcasts.viewmodels;

import spark.Request;
import spark.Response;

public class HomeViewModel extends AbstractViewModel implements ViewModel {
    public HomeViewModel(Request req, Response res) {
        super(req, res);
    }
}
