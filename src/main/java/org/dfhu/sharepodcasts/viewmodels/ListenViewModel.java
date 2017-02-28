package org.dfhu.sharepodcasts.viewmodels;

import com.google.gson.Gson;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.jsoup.Jsoup;
import spark.Request;
import spark.Response;

public class ListenViewModel extends AbstractViewModel implements ViewModel {

    private final EpisodeMorph episode;
    private final ShowMorph show;
    private final ShareMorph share;

    /**
     *
     * @param show - The show for this episode
     * @param episode - The episode
     * @param share - optional share for comments
     */
    public ListenViewModel(Request req, Response res, ShowMorph show, EpisodeMorph episode, ShareMorph share) {
        super(req, res);

        this.show = show;
        this.episode = episode;
        this.share = share;
    }

    public EpisodeMorph getEpisode() {
        return episode;
    }

    public ShowMorph getShow() {
        return show;
    }

    public boolean hasShareComment() {
        return share != null && share.comment != null && !share.comment.isEmpty();
    }

    /**
     * Clean up the share comment, remove html, add <br>, limit to 500 chars
     * @return
     */
    public String getShareComment() {
        if (share == null || share.comment == null) {
            return "No comment";
        }

        String comment = share.comment;

        comment = comment.replace("\n", "~#~");
        comment = Jsoup.parse(comment).text();
        if (comment.length() > 500) {
            comment = comment.substring(0, 2500);
        }
        return comment.replace("~#~", "<br>");
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
