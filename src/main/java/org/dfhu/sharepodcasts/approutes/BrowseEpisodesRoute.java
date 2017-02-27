package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.viewmodels.BrowseEpisodesViewModel;
import org.dfhu.sharepodcasts.views.browse.BrowseEpisodes;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseEpisodesRoute extends TemplateRoute implements Route {
    private final EpisodeQuery episodeQuery;

    public BrowseEpisodesRoute(EpisodeQuery episodeQuery) {
        this.episodeQuery = episodeQuery;
    }

    @Override
    public String getPath() {
        return RouteManager.browseEpisodes();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.GET;
    }

    @Override
    public RockerModel getRockerModel(Request req, Response res) {
        String showId = req.params("id");
        List<EpisodeMorph> episodes = episodeQuery.byShowId(showId);
        BrowseEpisodesViewModel vm = new BrowseEpisodesViewModel(episodes);
        return BrowseEpisodes.template(vm);
    }
}
