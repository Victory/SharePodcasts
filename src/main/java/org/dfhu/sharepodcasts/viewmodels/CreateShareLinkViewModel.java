package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;

public class CreateShareLinkViewModel extends ListenViewModel {

    public CreateShareLinkViewModel(ShowMorph show, EpisodeMorph episode) {
        super(show, episode, null);
    }

}
