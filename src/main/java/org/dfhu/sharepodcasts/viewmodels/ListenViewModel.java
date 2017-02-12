package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;

public class ListenViewModel extends AbstractViewModel implements ViewModel {

    private final EpisodeMorph episode;

    public ListenViewModel(EpisodeMorph episode) {
        this.episode = episode;
    }

    public EpisodeMorph getEpisode() {
        return episode;
    }
}
