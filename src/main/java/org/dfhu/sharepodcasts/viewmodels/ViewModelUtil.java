package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
import spark.Request;
import spark.Response;

public class ViewModelUtil {
    public static class Noop extends AbstractViewModel implements ViewModel {
        private final Request req;
        private final Response res;

        public Noop() {
            super(null);
            req = null;
            res = null;
        }

        public Noop(Request req, Response res, VicSession vicSession) {
            super(vicSession);
            this.req = req;
            this.res = res;
        }

        @Override
        public boolean isAjax() {
            if (this.vicSession != null) {
                return super.isAjax();
            }
            return false;
        }
    }
}
