package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShowMorph;

import java.util.List;

public class BrowseLetterViewModel implements ViewModel {
    private final List<ShowMorph> shows;

    public BrowseLetterViewModel(List<ShowMorph> shows) {
        this.shows = shows;
    }

    public List<ShowMorph> getShows() {
        return shows;
    }
}
