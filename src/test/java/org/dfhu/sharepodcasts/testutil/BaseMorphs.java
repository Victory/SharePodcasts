package org.dfhu.sharepodcasts.testutil;

import org.bson.types.ObjectId;
import org.dfhu.sharepodcasts.morphs.EpisodeMorph;
import org.dfhu.sharepodcasts.morphs.ShowMorph;

public class BaseMorphs {

    public static final ObjectId show1Id = new ObjectId("343434343434343434343434");
    public static final String show1Copyright = "show1Copyright";
    public static final String show1Author = "show1Author";
    public final static String show1Description = "show1 Description";
    public final static String show1ShowUrl = "http://show1ShowUrl";
    public final static String show1Url = "http://show1Url";
    public final static String show1Title = "show1 Title";

    public static ShowMorph getShowMorph1() {
        ShowMorph showMorph = new ShowMorph();
        showMorph.id = show1Id;
        showMorph.copyright = show1Copyright;
        showMorph.author = show1Author;
        showMorph.description = show1Description;
        showMorph.showUrl = show1ShowUrl;
        showMorph.url = show1Url;
        showMorph.title = show1Title;
        return showMorph;
    }

    public static final ObjectId episode1Id = new ObjectId("121212121212121212121212");
    public static final String episode1Description = "episode1 Description";
    public static final long episode1PubDate = 123;
    public static final String episode1Title = "episode1 Title";
    public static final String episode1Url = "http://episode1Url";

    public static EpisodeMorph getEpisodeMorph1() {
        EpisodeMorph episodeMorph = new EpisodeMorph();
        episodeMorph.id = episode1Id;
        episodeMorph.showId = show1Id;
        episodeMorph.description = episode1Description;
        episodeMorph.showTitle = show1Title;
        episodeMorph.pubDate = episode1PubDate;
        episodeMorph.title = episode1Title;
        episodeMorph.url = episode1Url;

        return episodeMorph;
    }
}
