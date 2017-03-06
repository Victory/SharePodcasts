package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.approutes.ContactFormSaveRoute;
import org.dfhu.sharepodcasts.morphs.ContactMorph;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ContactFormSaveRouteTest {

    @Test
    public void testStoresMsg() {

        String email = "email@example.com";
        String name = "Joe Shmoe";
        String msg = "This is my message";

        Request req = mock(Request.class);
        when(req.queryParams("email")).thenReturn(email);
        when(req.queryParams("name")).thenReturn(name);
        when(req.queryParams("msg")).thenReturn(msg);

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);

        ContactFormSaveRoute contactFormSaveRoute =
                new ContactFormSaveRoute(analyticsStore);
        contactFormSaveRoute.getJsonResponse(req, null);

        verify(req, times(1)).queryParams("email");
        verify(req, times(1)).queryParams("name");
        verify(req, times(1)).queryParams("msg");

        ArgumentCaptor<ContactMorph> captor = ArgumentCaptor.forClass(ContactMorph.class);

        verify(analyticsStore, times(1)).submit(captor.capture());
        assertEquals(name, captor.getValue().name);
        assertEquals(email, captor.getValue().email);
        assertEquals(msg, captor.getValue().msg);

    }



    @Test
    public void testEmailIsOptional() {
        String name = "Joe Shmoe";
        String msg = "This is my message";

        Request req = mock(Request.class);
        when(req.queryParams("email")).thenReturn(null);
        when(req.queryParams("name")).thenReturn(name);
        when(req.queryParams("msg")).thenReturn(msg);

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);

        ContactFormSaveRoute contactFormSaveRoute =
                new ContactFormSaveRoute(analyticsStore);
        contactFormSaveRoute.getJsonResponse(req, null);

        verify(req, times(1)).queryParams("email");
        verify(req, times(1)).queryParams("name");
        verify(req, times(1)).queryParams("msg");

        ArgumentCaptor<ContactMorph> captor = ArgumentCaptor.forClass(ContactMorph.class);

        verify(analyticsStore, times(1)).submit(captor.capture());
        assertEquals(name, captor.getValue().name);
        assertEquals(null, captor.getValue().email);
        assertEquals(msg, captor.getValue().msg);

    }
}
