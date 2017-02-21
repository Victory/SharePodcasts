package org.dfhu.sharepodcasts.controllers;

/**
 * Controllers take in routes so that they can be served by Spark
 */
public interface Controller {
    /**
     * Sets up the routes for this controller
     */
    void setupRoutes();
}
