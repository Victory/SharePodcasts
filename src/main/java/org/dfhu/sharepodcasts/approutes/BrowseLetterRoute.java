package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.viewmodels.BrowseLetterViewModel;
import org.dfhu.sharepodcasts.views.browse.BrowseLetter;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseLetterRoute extends TemplateRoute implements Route {

    private final ShowQuery showQuery;

    public BrowseLetterRoute(ShowQuery showQuery) {
        this.showQuery = showQuery;
    }

    @Override
    public String getPath() {
        return RouteManager.browseLetter();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        String letter = req.params("letter");
        List<ShowMorph> showsByLetter =
                showQuery.getShowsByLetter(letter);
        BrowseLetterViewModel vm = new BrowseLetterViewModel(vicSession, showsByLetter);
        return BrowseLetter.template(vm);
    }
}
