package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import spark.Request;
import spark.Response;

public class CreateShareLinkViewModel extends ListenViewModel {

    public CreateShareLinkViewModel(Request req, Response res, ShowMorph show, EpisodeMorph episode) {
        super(req, res, show, episode, null);
    }

}
