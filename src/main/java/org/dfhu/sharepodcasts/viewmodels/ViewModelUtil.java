package org.dfhu.sharepodcasts.viewmodels;

import spark.Request;
import spark.Response;

public class ViewModelUtil {
    public static class Noop implements ViewModel {
        public Noop(Request req, Response res) {
        }

        @Override
        public boolean isAjax() {
            return false;
        }
    }
}
