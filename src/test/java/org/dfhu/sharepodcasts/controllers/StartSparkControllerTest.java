package org.dfhu.sharepodcasts.controllers;

import org.junit.BeforeClass;

/**
 * Starts before class and stops after class
 */
public class StartSparkControllerTest {

    protected static SparkTestUtil sparkTestUtil;

    @BeforeClass
    public static void initSpark() {
        if (sparkTestUtil == null) {
            sparkTestUtil = new SparkTestUtil(4567);
        }
    }
}
