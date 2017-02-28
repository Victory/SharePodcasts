package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseHomeViewModel extends AbstractViewModel implements ViewModel {

    private final List<ShowLettersMorph> showLetters;

    public BrowseHomeViewModel(Request req, Response res, List<ShowLettersMorph> showLetters) {
        super(req, res);
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
