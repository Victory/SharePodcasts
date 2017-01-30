package org.dfhu.sharepodcasts;

import com.fizzed.rocker.runtime.RockerRuntime;
import org.dfhu.sharepodcasts.controllers.Controller;
import org.dfhu.sharepodcasts.controllers.HomeController;

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

        //staticFiles.location("/public");

        List<Controller> controllerList = new ArrayList<>();
        controllerList.add(new HomeController());

        SharePodcastsApplication.setupRoutes(controllerList);
    }
}
