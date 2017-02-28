package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShowMorph;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseLetterViewModel extends AbstractViewModel implements ViewModel {
    private final List<ShowMorph> shows;

    public BrowseLetterViewModel(Request req, Response res, List<ShowMorph> shows) {
        super(req, res);
        this.shows = shows;
    }

    public List<ShowMorph> getShows() {
        return shows;
    }
}
