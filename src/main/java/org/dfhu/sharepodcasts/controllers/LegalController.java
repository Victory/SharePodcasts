package org.dfhu.sharepodcasts.controllers;

import org.dfhu.sharepodcasts.RouteManager;
import org.dfhu.sharepodcasts.templateengine.RockerTemplateModel;
import org.dfhu.sharepodcasts.viewmodels.ViewModelUtil;
import org.dfhu.sharepodcasts.views.legal.Privacy;

public class LegalController extends BaseController implements Controller {
    @Override
    public void setupRoutes() {
        doGet(RouteManager.privacyPolicy(), (req, res) -> {
            Privacy template = Privacy.template(ViewModelUtil.NOOP);
            return new RockerTemplateModel(template);
        });

    }
}
