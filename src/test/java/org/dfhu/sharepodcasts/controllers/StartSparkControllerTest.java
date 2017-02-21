package org.dfhu.sharepodcasts.controllers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.testng.annotations.BeforeGroups;
import spark.Spark;

/**
 * Starts before class and stops after class
 */
public class StartSparkControllerTest {

    private static final int port = 5678;

    protected static SparkTestUtil sparkTestUtil;


    @BeforeClass
    public static void initSpark() {
        if (sparkTestUtil == null) {
            Spark.port(port);
            sparkTestUtil = new SparkTestUtil(port);

            Spark.get("/running-in-testing", (req, res) -> "Running in testing");
            Spark.awaitInitialization();
        }
    }

    @AfterClass
    public static void stopSpark() {
        if (sparkTestUtil != null) {
            sparkTestUtil = null;
            Spark.stop();
        }
    }
}
