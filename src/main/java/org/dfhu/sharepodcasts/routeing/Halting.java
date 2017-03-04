package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.morphs.ServerSideErrorMorph;
import org.dfhu.sharepodcasts.service.AnalyticsStore;
import org.dfhu.sharepodcasts.viewmodels.ViewModelUtil;
import org.dfhu.sharepodcasts.views.errors.NotFound;

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
        exception(Exception.class, (exception, req, res) -> {
            ServerSideErrorMorph log = new ServerSideErrorMorph();
            log.populateCommon(req, req.pathInfo());
            log.errorClass = exception.getClass().getName();
            log.errorMessage = exception.getMessage();

            analyticsStore.submit(log);

            res.body(notFoundPage);
            res.status(500);
        });
    }
}
