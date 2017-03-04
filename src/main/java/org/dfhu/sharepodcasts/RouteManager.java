package org.dfhu.sharepodcasts;

public class RouteManager {
    private static RouteManager INSTANCE = new RouteManager();

    /** Use getInstance */
    private RouteManager() {}

    public static RouteManager getInstance() {
        return INSTANCE;
    }

    public static String home() {
        return "/";
    }

    public static String suggest() {
        return "/suggest";
    }

    public static String addFeed() {
        return "/new-feed-save";
    }

    public static String addFeedForm() {
        return "/new-feed";
    }

    public static String createShareLink() {
        return "/create-share-link";
    }

    public static String saveShareLink() {
        return "/save-share-link";
    }

    public static String listen() {
        return "/l/:rowId";
    }

    public static String listen(String rowId) {
        return "/l/" + rowId;
    }

    public static String privacyPolicy() {
        return "/legal/privacy-policy";
    }

    public static String analyticsPixel() {
        return "/onebyone";
    }

    public static String browse() {
        return "/browse";
    }

    public static String browseLetter() {
        return "/browse/:letter";
    }

    public static String browseEpisodes() {
        return "/browse/show/:id";
    }

    public static String jsErrorPixel() {
        return "/jserror";
    }
}
