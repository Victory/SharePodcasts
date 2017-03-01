package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import spark.Request;
import spark.Response;

import java.util.List;

public class BrowseEpisodesViewModel extends AbstractViewModel implements ViewModel {

    private final List<EpisodeMorph> episodes;

    public BrowseEpisodesViewModel(Request req, Response res, List<EpisodeMorph> episodes) {
        super(req, res);

        this.episodes = episodes;
    }

    public String getShowTitle() {
        if (episodes.size() < 1) {
            return "";
        }
        return episodes.get(0).showTitle;
    }

    public List<EpisodeMorph> getEpisodes() {
        return episodes;
    }
}
