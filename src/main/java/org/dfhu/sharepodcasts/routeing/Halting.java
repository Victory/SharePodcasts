package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.viewmodels.ViewModelUtil;
import org.dfhu.sharepodcasts.views.errors.NotFound;

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
}
