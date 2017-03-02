package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseLetterViewModel extends AbstractViewModel implements ViewModel {
    private final List<ShowMorph> shows;

    public BrowseLetterViewModel(VicSession vicSession, List<ShowMorph> shows) {
        super(vicSession);
        this.shows = shows;
    }

    public List<ShowMorph> getShows() {
        return shows;
    }
}
