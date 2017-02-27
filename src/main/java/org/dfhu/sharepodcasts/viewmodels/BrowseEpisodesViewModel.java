package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;

import java.util.List;

public class BrowseEpisodesViewModel implements ViewModel {
    private final List<EpisodeMorph> episodes;

    public BrowseEpisodesViewModel(List<EpisodeMorph> episodes) {
        this.episodes = episodes;
    }

    public List<EpisodeMorph> getEpisodes() {
        return episodes;
    }
}
