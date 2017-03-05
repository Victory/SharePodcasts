package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.morphs.ServerSideErrorMorph;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.viewmodels.ViewModelUtil;
import org.dfhu.sharepodcasts.views.errors.NotFound;

import java.io.PrintWriter;
import java.io.StringWriter;

import static spark.Spark.exception;
import static spark.Spark.halt;

public class Halting {
    private static final String notFoundPage =
            NotFound.template(new ViewModelUtil.Noop())
                    .render()
                    .toString();

    public static void haltNotFound() {
        halt(404, notFoundPage);
    }

    public static void haltNotImplemented() {
        halt(501, notFoundPage);
    }

    public static void bindInternalServerError(final AnalyticsStore analyticsStore) {
        exception(Exception.class, (exc, req, res) -> {
            ServerSideErrorMorph log = new ServerSideErrorMorph();
            log.populateCommon(req, req.pathInfo());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exc.printStackTrace(pw);
            log.stackTrace = sw.toString();

            log.errorClass = exc.getClass().getName();
            log.errorMessage = exc.getMessage();

            analyticsStore.submit(log);

            res.body(notFoundPage);
            res.status(500);
        });
    }
}
