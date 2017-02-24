package org.dfhu.sharepodcasts.approutes;

import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.dfhu.sharepodcasts.routeing.BodylessRoute;
import org.dfhu.sharepodcasts.routeing.Route;
import org.dfhu.sharepodcasts.service.ShortLinkCreator;
import org.slf4j.Logger;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class SaveShareLinkRoute extends BodylessRoute implements Route {

    private static final int SAVE_TRIALS = 10;
    private final ShortLinkCreator shortLinkCreator;
    private final ShareQuery shareQuery;
    private final Logger logger;

    public SaveShareLinkRoute(ShortLinkCreator shortLinkCreator, ShareQuery shareQuery, Logger logger) {
        this.shortLinkCreator = shortLinkCreator;
        this.shareQuery = shareQuery;
        this.logger = logger;
    }

    @Override
    public String getPath() {
        return RouteManager.saveShareLink();
    }

    @Override
    public METHOD getMethod() {
        return METHOD.POST;
    }

    @Override
    public void updateResponse(Request req, Response res) {
        ShareMorph shareMorph = new ShareMorph();
        String comment = req.queryParams("comment");
        ObjectId episodeId = new ObjectId(req.queryParams("episodeId"));

        shareMorph.comment = Optional.ofNullable(comment).orElse("");
        shareMorph.episodeId = episodeId;
        shareMorph.timeStamp = System.currentTimeMillis();
        shareMorph.ip = req.ip();

        String key = save(shareMorph);
        res.redirect("/l/" + key);
    }

    private String save(ShareMorph shareMorph) {
        String key = "";
        int ii;
        for (ii = 0; ii < SAVE_TRIALS; ii++) {
            try {
                key = shortLinkCreator.create();
                shareMorph.shortLink = key;
                shareQuery.save(shareMorph);
                break;
            } catch (DuplicateKeyException e) {
                logger.error("Save shareMorph: " + key + " " + shareMorph);
            }
        }

        if (ii == SAVE_TRIALS) {
            return "";
        }

        return key;
    }
}
