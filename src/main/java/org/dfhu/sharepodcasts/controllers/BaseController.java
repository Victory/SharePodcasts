package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.templateengine.RockerTemplateEngine;
import org.dfhu.sharepodcasts.views.errors.NotFound;
import spark.Spark;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

abstract class BaseController {
    private static final String notFoundPage = NotFound.template().render().toString();

    /**
     * Handle a basic get request
     * @param path - the path for this request
     * @param templateViewRoute - handler
     */
    static void doGet(String path, TemplateViewRoute templateViewRoute) {
        Spark.get(path, templateViewRoute, RockerTemplateEngine.getInstance());
    }

    /**
     * Handle basic post request
     * @param path - the path for this request
     * @param templateViewRoute - handler
     */
    static void doPost(String path, TemplateViewRoute templateViewRoute) {
        Spark.post(path, templateViewRoute, RockerTemplateEngine.getInstance());
    }

    static void haltNotFound() {
        halt(404, notFoundPage);
    }
}
