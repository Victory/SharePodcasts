package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.templateengine.RockerTemplateEngine;
import spark.Request;
import spark.Spark;
import spark.TemplateViewRoute;

abstract class BaseController {
    /**
     * Handle a basic get request
     * @param path - the path for this request
     * @param templateViewRoute
     */
    public static void doGet(String path, TemplateViewRoute templateViewRoute) {
        Spark.get(path, templateViewRoute, RockerTemplateEngine.getInstance());
    }
}
