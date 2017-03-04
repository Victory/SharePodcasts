package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.VicSession;
abstract class AbstractViewModel {

    protected final VicSession vicSession;

    public AbstractViewModel(VicSession vicSession) {
        this.vicSession = vicSession;
    }

    public boolean isAjax() {
        return vicSession.isAjax();
    }

}
