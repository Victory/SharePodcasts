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
}
