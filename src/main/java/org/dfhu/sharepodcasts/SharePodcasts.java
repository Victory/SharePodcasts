package org.dfhu.sharepodcasts;

import com.fizzed.rocker.runtime.RockerRuntime;
import org.dfhu.sharepodcasts.controllers.Controller;
import org.dfhu.sharepodcasts.controllers.FeedController;
import org.dfhu.sharepodcasts.controllers.HomeController;
import org.dfhu.sharepodcasts.controllers.SuggestController;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.staticFiles;

/**
 * Main
 */
public class SharePodcasts {
    public static void main(String[] args) {

        // DEBUGING
        RockerRuntime.getInstance().setReloading(true);
        String publicDir = System.getProperty("user.dir") + "/src/main/resources/public";
        staticFiles.externalLocation(publicDir);

        // PRODUCTION
        //staticFiles.location("/public");
        //RockerRuntime.getInstance().setReloading(false);

        List<Controller> controllerList = new ArrayList<>();
        controllerList.add(new HomeController());
        controllerList.add(new SuggestController());
        controllerList.add(new FeedController());

        SharePodcastsApplication.setupRoutes(controllerList);
    }
}
