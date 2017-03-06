import {buildPlayer} from "./listen";
import {bindButtonUiActions} from "./buttons";
import {importFeed} from "./feed";
import {suggestKeyup} from "./suggest";
import {bindContactForm} from "./contact";

let $bodyBox = $("#bodyBox");

function rebindAll() {
    buildPlayer();
    bindButtonUiActions();
    importFeed();
    suggestKeyup();
    bindContactForm();
}

/**
 * Load ajax version of page into $bodyBox
 * @param {String} href - href to load from
 * @param {Boolean} shouldPush - if we should push the href onto window.history
 */
function ajaxLoadBodyBox(href, shouldPush) {
    $.get(href + "?ajax=1", (data) => {
        $bodyBox.html(data);
        rebindAll();

        $("title").html(window.vic.title);
        window.scrollTo(0, 0);

        if (shouldPush && history.pushState) {
            let stateObj = {
                href: href,
                title: window.vic.title,
            };
            history.pushState(stateObj, "vic", href);
            // defined in FullPage.rocker.html
            window.analytics();
        }
    });
}

function linkClickAjaxLoadBodyBox(evt) {
    evt.preventDefault();

    $(".navbar-responsive-collapse").collapse('hide');

    let $this = $(evt.target);
    let href = $this.attr('href');
    // if click on same link return
    if (href == document.location.pathname) {
        document.location = href;
        return;
    }
    ajaxLoadBodyBox(href, true);
}

module.exports = {
    bindBodyBox: () => {

        window.onpopstate = (evt) => {
            let state = evt.state;
            if (state == null) {
                document.location = "/";
                return;
            }
            let href = state.href;
            ajaxLoadBodyBox(href, false);
            return true;
        };

        $("body").on("click", "[vic-body-box]", linkClickAjaxLoadBodyBox);
    },
};

