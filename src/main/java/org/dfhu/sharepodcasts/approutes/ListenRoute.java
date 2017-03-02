package org.dfhu.sharepodcasts.approutes;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.routeing.TemplateRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.ShortLinkCreator;
import org.dfhu.sharepodcasts.viewmodels.ListenViewModel;
import org.dfhu.sharepodcasts.views.listen.Listen;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.dfhu.sharepodcasts.routeing.Halting.haltNotFound;

public class ListenRoute extends TemplateRoute implements Route {

    private final ShowQuery showQuery;
    private final EpisodeQuery episodeQuery;
    private final ShareQuery shareQuery;

    public ListenRoute(ShowQuery showQuery, EpisodeQuery episodeQuery, ShareQuery shareQuery) {
        this.showQuery = showQuery;
        this.episodeQuery = episodeQuery;
        this.shareQuery = shareQuery;
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
    public RockerModel getRockerModel(Request req, Response res, VicSession vicSession) {
        String id = req.params(":rowId");
        if (id == null) haltNotFound();

        // Convert short link to episodeId if short
        String comment = "";
        ShareMorph share = null;
        if (id.length() == ShortLinkCreator.SHORT_LINK_LENGTH) {
            Optional<ShareMorph> shareMorph = shareQuery.byShortLink(id);
            if (!shareMorph.isPresent()) haltNotFound();
            share = shareMorph.get();
            id = share.episodeId.toString();
        }


        Optional<EpisodeMorph> episode = episodeQuery.byId(id);

        if (!episode.isPresent()) haltNotFound();

        Optional<ShowMorph> show = showQuery.byId(episode.get().showId);
        ListenViewModel vm = new ListenViewModel(vicSession, show.get(), episode.get(), share);
        return Listen.template(vm);
    }
}
