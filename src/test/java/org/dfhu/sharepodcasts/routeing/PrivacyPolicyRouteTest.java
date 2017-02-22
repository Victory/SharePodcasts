package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.approutes.PrivacyPolicyRoute;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PrivacyPolicyRouteTest {
    @Test
    public void get() {
        PrivacyPolicyRoute privacyPolicyRoute = new PrivacyPolicyRoute();
        RockerModel privacyPolicyRouteRockerModel =
                privacyPolicyRoute.getRockerModel(null, null);
        String actual = privacyPolicyRouteRockerModel.render().toString();
        assertTrue(actual.contains("Privacy Policy"));
    }
}
