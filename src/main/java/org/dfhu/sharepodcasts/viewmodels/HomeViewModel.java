package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
import spark.Request;
import spark.Response;

public class HomeViewModel extends AbstractViewModel implements ViewModel {
    public HomeViewModel(VicSession vicSession) {
        super(vicSession);
    }
}
