package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.approutes.JsErrorRoute;
import org.dfhu.sharepodcasts.morphs.ClientSideErrorMorph;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class JsErrorRouteTest {
    @Test
    public void setsIp() {
        String expected = "1.2.3.4";

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);

        Request request = mock(Request.class);
        when(request.ip()).thenReturn(expected);

        Response response = mock(Response.class);

        JsErrorRoute jsErrorRoute = new JsErrorRoute(analyticsStore);
        jsErrorRoute.getBytes(request, response);

        ArgumentCaptor<ClientSideErrorMorph> captor =
                ArgumentCaptor.forClass(ClientSideErrorMorph.class);
        verify(analyticsStore, times(1)).submit(captor.capture());

        String actual = captor.getValue().ip;
        assertEquals(expected, actual);
    }

    @Test
    public void returnsPngWithHeaders() {

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);

        JsErrorRoute analyticsRoute = new JsErrorRoute(analyticsStore);
        byte[] body = analyticsRoute.getBytes(request, response);

        assertTrue(new String(body).contains("PNG"));

        verify(response, times(1)).header("Content-type", "image/png");
    }

    @Test
    public void getsPathname() {
        String expected = "/some/path";

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);
        Request request = mock(Request.class);
        when(request.queryParams("pathname")).thenReturn(expected);
        Response response = mock(Response.class);

        JsErrorRoute jsErrorRoute = new JsErrorRoute(analyticsStore);
        jsErrorRoute.getBytes(request, response);

        verify(request, times(1)).queryParams("pathname");

        ArgumentCaptor<ClientSideErrorMorph> captor =
                ArgumentCaptor.forClass(ClientSideErrorMorph.class);

        verify(analyticsStore, times(1)).submit(captor.capture());
        assertEquals(expected, captor.getValue().pathname);
    }



    @Test
    public void getsMsg() {
        String pathname = "/some/path";
        String expected = "Some Error Message";

        AnalyticsStore analyticsStore = mock(AnalyticsStore.class);
        Request request = mock(Request.class);
        when(request.queryParams("pathname")).thenReturn(pathname);
        when(request.queryParams("msg")).thenReturn(expected);
        Response response = mock(Response.class);

        JsErrorRoute jsErrorRoute = new JsErrorRoute(analyticsStore);
        jsErrorRoute.getBytes(request, response);

        verify(request, times(1)).queryParams("pathname");

        ArgumentCaptor<ClientSideErrorMorph> captor =
                ArgumentCaptor.forClass(ClientSideErrorMorph.class);

        verify(analyticsStore, times(1)).submit(captor.capture());
        assertEquals(pathname, captor.getValue().pathname);
        assertEquals(expected, captor.getValue().errorMessage);
    }
}

