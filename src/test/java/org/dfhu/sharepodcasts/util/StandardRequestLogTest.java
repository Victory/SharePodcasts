package org.dfhu.sharepodcasts.util;

import org.dfhu.sharepodcasts.morphs.AbstractLog;
import org.dfhu.sharepodcasts.morphs.ClientSideErrorMorph;
import org.dfhu.sharepodcasts.morphs.RequestLogMorph;
import org.dfhu.sharepodcasts.morphs.ServerSideErrorMorph;
import org.junit.Test;
import org.mongodb.morphia.annotations.Entity;
import spark.Request;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class StandardRequestLogTest {

    @Test
    public void canUpdateOtherFieldAfterRunning() {
        Request req = mock(Request.class);
        TLog tLog = StandardRequestLog.build(TLog.class, req, "/somepath");
        tLog.otherField = "true";
        assertEquals("/somepath", tLog.pathname);
    }

    @Test
    public void examples() {
        Request req = mock(Request.class);
        StandardRequestLog.build(RequestLogMorph.class, req, "/somepath");
        StandardRequestLog.build(ClientSideErrorMorph.class, req, "/somepath");
        StandardRequestLog.build(ServerSideErrorMorph.class, req, "/somepath");
    }

    @Test
    public void ipFromXRealIP() {
        String header = "X-Real-IP";
        String target = "123.123.123.123";
        Request req = mock(Request.class);
        when(req.headers(header)).thenReturn(target);
        TLog tLog = StandardRequestLog.build(TLog.class, req, "/somepath");
        assertEquals(target, tLog.ip);
    }

    @Test
    public void ipFromXForwardedForHasPresidence() {
        String header = "X-Forwarded-For";
        String target = "123.123.123.123";
        Request req = mock(Request.class);
        when(req.headers(header)).thenReturn(target);
        when(req.headers("X-Real-IP")).thenReturn("FAILURE");
        TLog tLog = StandardRequestLog.build(TLog.class, req, "/somepath");
        assertEquals(target, tLog.ip);
    }

    @Entity("testtesttest")
    private static class TLog extends AbstractLog {
        String otherField;

        public TLog() {
            super();
        }
    }
}