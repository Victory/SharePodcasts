package org.dfhu.sharepodcasts.controllers;


import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateModel;
import org.dfhu.sharepodcasts.viewmodels.ListenViewModel;
import org.dfhu.sharepodcasts.views.createsharelink.CreateShareLink;
import spark.Request;

import java.util.Optional;


public class CreateShareLinkController extends BaseController implements Controller {

    private final ShowQuery showQuery;
    private final EpisodeQuery episodeQuery;

    public CreateShareLinkController(ShowQuery showQuery, EpisodeQuery episodeQuery) {
        this.showQuery = showQuery;
        this.episodeQuery = episodeQuery;
    }

    @Override
    public void setupRoutes() {
        doPost(RouteManager.createShareLink(),
                (req, res) -> new RockerTemplateModel(listenTemplate(req)));

    }

    private CreateShareLink listenTemplate(Request req) {
        String id = req.queryParams("rowId");
        Optional<EpisodeMorph> episode = episodeQuery.byId(id);
        //if (!episode.isPresent()) haltNotFound();

        Optional<ShowMorph> show = showQuery.byId(episode.get().showId);
        ListenViewModel vm = new ListenViewModel(show.get(), episode.get());
        return CreateShareLink.template(vm);
    }
}
