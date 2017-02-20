package org.dfhu.sharepodcasts.controllers;

import org.junit.Test;
import spark.Spark;

public class HomeControllerTest extends StartSparkControllerTest {

    @Test
    public void getHome() throws Exception {
        HomeController c = new HomeController();
        c.setupRoutes();
        Spark.awaitInitialization();
        SparkTestUtil.UrlResponse get =
                sparkTestUtil.doMethod("GET", "/", null);
    }

}
