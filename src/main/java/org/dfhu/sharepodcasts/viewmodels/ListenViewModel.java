package org.dfhu.sharepodcasts.viewmodels;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.jsoup.Jsoup;

public class ListenViewModel extends AbstractViewModel implements ViewModel {

    private final EpisodeMorph episode;

    public ListenViewModel(EpisodeMorph episode) {
        this.episode = episode;
    }

    public EpisodeMorph getEpisode() {
        return episode;
    }

    @Override
    public String getTopScript() {
        Gson gson = new Gson();

        EpisodeJson episodeJson = new EpisodeJson();
        episodeJson.url = episode.url;

        return "window.episode = " + gson.toJson(episodeJson) + ";";
    }

    public String cleanDescription() {
       return Jsoup.parse(episode.description).text();
    }

    public static final class EpisodeJson {
        public String url;
    }
}
