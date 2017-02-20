package org.dfhu.sharepodcasts;

import com.fizzed.rocker.runtime.RockerRuntime;
import org.dfhu.sharepodcasts.controllers.*;
import org.dfhu.sharepodcasts.morphs.DataProvider;
import org.dfhu.sharepodcasts.morphs.finders.EpisodeFinder;
import org.dfhu.sharepodcasts.morphs.finders.ShowFinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.exception;
import static spark.Spark.halt;
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

        ShowFinder showFinder = new ShowFinder(DataProvider.get());
        EpisodeFinder episodeFinder = new EpisodeFinder(DataProvider.get());

        controllerList.add(new AnalyticsController());
        controllerList.add(new CreateShareLinkController());
        controllerList.add(new FeedController());
        controllerList.add(new HomeController());
        controllerList.add(new LegalController());
        controllerList.add(new ListenController(showFinder, episodeFinder));
        controllerList.add(new SuggestController());

        SharePodcastsApplication.setupRoutes(controllerList);
    }
}
