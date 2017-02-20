package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.finders.EpisodeFinder;
import org.dfhu.sharepodcasts.morphs.finders.ShowFinder;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateModel;
import org.dfhu.sharepodcasts.viewmodels.ListenViewModel;
import org.dfhu.sharepodcasts.views.listen.Listen;
import spark.Request;

import java.util.Optional;


public class ListenController extends BaseController implements Controller {

    private final ShowFinder showFinder;
    private final EpisodeFinder episodeFinder;

    public ListenController(ShowFinder showFinder, EpisodeFinder episodeFinder) {
        this.showFinder = showFinder;
        this.episodeFinder = episodeFinder;
    }

    @Override
    public void setupRoutes() {
        // Play episode
        doGet(RouteManager.listen(),
                (req, res) -> new RockerTemplateModel(listenTemplate(req)));
    }

    Listen listenTemplate(Request req) {
        String id = req.params(":rowId");

        Optional<EpisodeMorph> episode = episodeFinder.byId(id);
        if (!episode.isPresent()) haltNotFound();

        Optional<ShowMorph> show = showFinder.byId(episode.get().showId);
        ListenViewModel vm = new ListenViewModel(show.get(), episode.get());
        return Listen.template(vm);
    }
}
