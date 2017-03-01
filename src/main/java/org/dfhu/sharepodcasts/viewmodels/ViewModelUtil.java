package org.dfhu.sharepodcasts.viewmodels;

import spark.Request;
import spark.Response;

public class ViewModelUtil {
    public static class Noop extends AbstractViewModel implements ViewModel {
        private final Request req; public Noop(Request req, Response res) {
            super(req, res);
            this.req = req;
        }

        @Override
        public boolean isAjax() {
            if (this.req == null) {
                return false;
            }

            return super.isAjax();
        }
    }
}
