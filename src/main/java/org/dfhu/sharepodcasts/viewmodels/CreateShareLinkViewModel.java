package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import spark.Request;
import spark.Response;

public class CreateShareLinkViewModel extends ListenViewModel {

    public CreateShareLinkViewModel(VicSession vicSession, ShowMorph show, EpisodeMorph episode) {
        super(vicSession, show, episode, null);
    }

}
