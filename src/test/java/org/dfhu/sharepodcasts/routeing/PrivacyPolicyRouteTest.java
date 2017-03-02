package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.approutes.PrivacyPolicyRoute;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PrivacyPolicyRouteTest {
    @Test
    public void get() {
        PrivacyPolicyRoute privacyPolicyRoute = new PrivacyPolicyRoute();
        VicSession vicSession = new VicSession(null, null);
        RockerModel privacyPolicyRouteRockerModel =
                privacyPolicyRoute.getRockerModel(null, null, vicSession);
        String actual = privacyPolicyRouteRockerModel.render().toString();
        assertTrue(actual.contains("Privacy Policy"));
    }
}
