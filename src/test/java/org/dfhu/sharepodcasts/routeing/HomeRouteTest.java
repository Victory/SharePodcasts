package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.approutes.HomeRoute;
import org.junit.Test;
import spark.Request;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

public class HomeRouteTest {
    @Test
    public void hasTitleTag() {
        HomeRoute homeRoute = new HomeRoute();
        Request req = mock(Request.class);

        RockerModel home
                = homeRoute.getRockerModel(req, null);
        String actual = home.render().toString();
        assertTrue("Title tag is present", actual.contains("<title>"));
    }
}
