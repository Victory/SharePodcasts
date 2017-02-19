package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.templateengine.RockerTemplateEngine;
import spark.Spark;
import spark.TemplateViewRoute;

abstract class BaseController {
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
}
