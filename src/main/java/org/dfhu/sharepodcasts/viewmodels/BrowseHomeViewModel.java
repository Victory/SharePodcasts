package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;

import java.util.List;

public class BrowseHomeViewModel implements ViewModel {

    private final List<ShowLettersMorph> showLetters;

    public BrowseHomeViewModel(List<ShowLettersMorph> showLetters) {
        this.showLetters = showLetters;
    }

    /**
     * Get the letters that have Shows
     * @return
     */
    public List<ShowLettersMorph> getShowLetters() {
        return showLetters;
    }
}
