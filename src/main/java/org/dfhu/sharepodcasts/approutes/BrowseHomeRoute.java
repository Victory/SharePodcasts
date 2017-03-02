package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.viewmodels.BrowseHomeViewModel;
import org.dfhu.sharepodcasts.views.browse.Browse;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseHomeRoute extends TemplateRoute implements Route {
    private final ShowQuery showQuery;

    public BrowseHomeRoute(ShowQuery showQuery) {
        this.showQuery = showQuery;
    }

    @Override
    public String getPath() {
        return RouteManager.browse();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        List<ShowLettersMorph> showLetters = showQuery.getShowLetters();
        BrowseHomeViewModel vm = new BrowseHomeViewModel(vicSession, showLetters);
        return Browse.template(vm);
    }
}
