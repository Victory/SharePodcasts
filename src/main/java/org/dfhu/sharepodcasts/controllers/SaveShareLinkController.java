package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.dfhu.sharepodcasts.morphs.query.EpisodeQuery;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;

import java.util.Optional;

import static spark.Spark.post;

public class SaveShareLinkController extends BaseController implements Controller {

    private final EpisodeQuery episodeQuery;
    private final ShareQuery shareQuery;

    public SaveShareLinkController(EpisodeQuery episodeQuery, ShareQuery shareQuery) {
        this.episodeQuery = episodeQuery;
        this.shareQuery = shareQuery;
    }

    @Override
    public void setupRoutes() {
        post(RouteManager.saveShareLink(), (req, res) -> {

            String episodeId = req.queryParams("episodeId");
//            if (episodeId == null) haltNotFound();

            Optional<EpisodeMorph> episode = episodeQuery.byId(episodeId);
//            if (!episode.isPresent()) haltNotFound();


            String comment = req.queryParams("comment");
//            if (comment == null) haltNotFound();

            ShareMorph shareMorph = new ShareMorph();
            shareMorph.ip = req.ip();
            shareMorph.timeStamp = System.currentTimeMillis();
            shareMorph.episodeId = episode.get().id;
            shareMorph.comment = comment;


            shareQuery.save(shareMorph);
            return "";
        });
    }
}
