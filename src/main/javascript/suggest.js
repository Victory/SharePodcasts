import {getLabelFor, setText, vLog, listen, qs, getVal, ajaxPost, showError, cloneNode, removeClass, addClass} from "./vic";

module.exports = {
    suggestKeyup: function () {
        console.log($("body").html().substr(0, 50));

        var input = qs("[vic-suggest]");
        var suggestResults = qs("[vic-suggest-results]");
        var suggestPrototype = cloneNode(qs("[vic-suggest-results] > .hidden"));

        input.value = "";

        var vXhr;
        listen(input, "keyup", evt => {
            let val = getVal(evt);
            if (val.length < 3) {
                addClass(suggestResults, "hidden");
                return;
            }
            removeClass(suggestResults, "hidden");

            var options = {
                data: "q=" + encodeURIComponent(val)
            };

            if (typeof vXhr !== "undefined") {
                vXhr.xhr.abort();
            }

            vXhr = ajaxPost("/suggest", options);

            vXhr.then(evt => {
                try {
                    var suggestions = JSON.parse(evt.responseText);
                } catch (e) {
                    throw e;
                }

                suggestions.forEach(item => {
                    var cloned = cloneNode(suggestPrototype);

                    cloned.querySelector("a").setAttribute("href", "/media/" + item.rowId);
                    removeClass(cloned, "hidden");

                    setText(cloned.querySelector("[vic-suggest-name]"), item.name);
                    setText(cloned.querySelector("[vic-suggest-type]"), item.mediaType);

                    suggestResults.appendChild(cloned);
                });

            }, evt => {
                vLog(evt);
            i})
            .then(r => {

            },
            err => {
                showError(e);
            });



            vXhr.done(evt => {
                suggestResults.innerHTML = '';
                vXhr = undefined;
            });

        });

        listen(input, "focus", evt => {
            setText(getLabelFor(evt), "");
        });
    },
};
