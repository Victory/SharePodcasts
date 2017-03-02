package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;

public class ViewModelUtil {
    public static class Noop extends AbstractViewModel implements ViewModel {
        public Noop() {
            super(null);
        }

        @Override
        public boolean isAjax() {
           return false;
        }
    }
}
