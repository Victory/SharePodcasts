package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.views.errors.NotFound;

import static spark.Spark.halt;

public class Halting {
    private static final String notFoundPage = NotFound.template().render().toString();

    public static void haltNotFound() {
        halt(404, notFoundPage);
    }
}
