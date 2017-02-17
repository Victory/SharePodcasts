package org.dfhu.sharepodcasts.controllers;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateModel;
import org.dfhu.sharepodcasts.viewmodels.ListenViewModel;
import org.dfhu.sharepodcasts.views.listen.Listen;
import spark.Spark;

public class ListenController extends BaseController implements Controller {
    @Override
    public void setupRoutes() {

        // Play episode
        doGet(RouteManager.listen(), (req, res) -> {

            ObjectId objectId = new ObjectId(req.params(":rowId"));

            EpisodeMorph episode = DataProvider.get().createQuery(EpisodeMorph.class)
                    .filter("_id = ", objectId)
                    .get();

            ShowMorph show = DataProvider.get().createQuery(ShowMorph.class)
                    .filter("_id = ", episode.showId)
                    .get();

            ListenViewModel vm = new ListenViewModel(show, episode);
            Listen template = Listen.template(vm);
            return new RockerTemplateModel(template);
        });

        // Create Share Link
        // TODO: just redirects for now should be able to customize
        Spark.post(RouteManager.createShareLink(), (req, res) -> {
            String rowId = req.queryParams("rowId");
            res.redirect(RouteManager.listen(rowId));
            return "";
        });
    }
}
