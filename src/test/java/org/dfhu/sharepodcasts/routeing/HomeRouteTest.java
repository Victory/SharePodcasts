package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.approutes.HomeRoute;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HomeRouteTest {
    @Test
    public void hasTitleTag() {
        HomeRoute homeRoute = new HomeRoute();
        RockerModel home
                = homeRoute.getRockerModel(null, null);
        String actual = home.render().toString();
        assertTrue("Title tag is present", actual.contains("<title>"));
    }
}
