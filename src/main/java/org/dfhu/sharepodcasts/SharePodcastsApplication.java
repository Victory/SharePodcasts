package org.dfhu.sharepodcasts;

import org.dfhu.sharepodcasts.controllers.Controller;

import java.util.Collection;

public class SharePodcastsApplication {

    public static void setupRoutes(Collection<Controller> controllers) {
        controllers.stream().forEach(c -> c.setupRoutes());
    }

}
