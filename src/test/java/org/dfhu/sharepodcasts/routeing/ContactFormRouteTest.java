package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.VicSession;
import org.dfhu.sharepodcasts.approutes.ContactFormRoute;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ContactFormRouteTest {
    @Test
    public void get() {
        ContactFormRoute contactFormRoute = new ContactFormRoute();

        VicSession vicSession = mock(VicSession.class);
        when(vicSession.isAjax()).thenReturn(false);

        RockerModel model =
                contactFormRoute.getRockerModel(null, null, vicSession);
        String actual = model.render().toString();
        assertTrue(actual.contains("Contact"));
    }
}
