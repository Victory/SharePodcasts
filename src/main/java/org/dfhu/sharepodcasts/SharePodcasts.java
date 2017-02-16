package org.dfhu.sharepodcasts;

import com.fizzed.rocker.runtime.RockerRuntime;
import org.dfhu.sharepodcasts.controllers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.staticFiles;

/**
 * Main
 */
public class SharePodcasts {
    public static void main(String[] args) {

        try {
            System.getProperties().load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find config file config.properties");
        }
        boolean dev = System.getProperty("dev").equals("true");

        if (dev) {
            // DEBUGING
            RockerRuntime.getInstance().setReloading(true);
            String publicDir = System.getProperty("user.dir") + "/src/main/resources/public";
            staticFiles.externalLocation(publicDir);
        } else {
            // PRODUCTION
            staticFiles.location("/public");
            RockerRuntime.getInstance().setReloading(false);
        }

        List<Controller> controllerList = new ArrayList<>();
        controllerList.add(new HomeController());
        controllerList.add(new SuggestController());
        controllerList.add(new FeedController());
        controllerList.add(new ListenController());

        SharePodcastsApplication.setupRoutes(controllerList);
    }
}
