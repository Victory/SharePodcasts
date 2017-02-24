package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.viewmodels.CreateShareLinkViewModel;
import org.dfhu.sharepodcasts.views.createsharelink.CreateShareLink;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.dfhu.sharepodcasts.routeing.Halting.haltNotFound;

public class CreateShareLinkRoute extends TemplateRoute implements Route {

    private final ShowQuery showQuery;
    private final EpisodeQuery episodeQuery;

    public CreateShareLinkRoute(ShowQuery showQuery, EpisodeQuery episodeQuery) {
        this.showQuery = showQuery;
        this.episodeQuery = episodeQuery;
    }

    @Override
    public String getPath() {
        return RouteManager.createShareLink();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.POST;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res) {
        String id = req.queryParams("rowId");
        Optional<EpisodeMorph> episode = episodeQuery.byId(id);
        if (!episode.isPresent()) haltNotFound();

        Optional<ShowMorph> show = showQuery.byId(episode.get().showId);
        CreateShareLinkViewModel vm =
                new CreateShareLinkViewModel(show.get(), episode.get());
        return CreateShareLink.template(vm);
    }
}
