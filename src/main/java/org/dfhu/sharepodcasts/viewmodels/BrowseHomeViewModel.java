package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseHomeViewModel extends AbstractViewModel implements ViewModel {

    private final List<ShowLettersMorph> showLetters;

    public BrowseHomeViewModel(VicSession vicSession, List<ShowLettersMorph> showLetters) {
        super(vicSession);
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
