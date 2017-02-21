package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.viewmodels.ListenViewModel;
import org.dfhu.sharepodcasts.views.listen.Listen;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.dfhu.sharepodcasts.routeing.Halting.haltNotFound;

public class ListenRoute extends TemplateRoute implements Route {

    private final ShowQuery showQuery;
    private final EpisodeQuery episodeQuery;

    public ListenRoute(ShowQuery showQuery, EpisodeQuery episodeQuery) {
        this.showQuery = showQuery;
        this.episodeQuery = episodeQuery;
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public String getPath() {
        return RouteManager.listen();
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res) {
        String id = req.params(":rowId");

        Optional<EpisodeMorph> episode = episodeQuery.byId(id);
        if (!episode.isPresent()) haltNotFound();

        Optional<ShowMorph> show = showQuery.byId(episode.get().showId);
        ListenViewModel vm = new ListenViewModel(show.get(), episode.get());
        return Listen.template(vm);
    }
}
