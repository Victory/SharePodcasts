package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.approutes.PrivacyPolicyRoute;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PrivacyPolicyRouteTest {
    @Test
    public void get() {
        PrivacyPolicyRoute privacyPolicyRoute = new PrivacyPolicyRoute();

        VicSession vicSession = mock(VicSession.class);
        when(vicSession.isAjax()).thenReturn(false);

        RockerModel privacyPolicyRouteRockerModel =
                privacyPolicyRoute.getRockerModel(null, null, vicSession);
        String actual = privacyPolicyRouteRockerModel.render().toString();
        assertTrue(actual.contains("Privacy Policy"));
    }
}
