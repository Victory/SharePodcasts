import {getLabelFor, setText, vLog, listen, qs, getVal, ajaxPost, showError, cloneNode, removeClass, addClass} from "./vic";

module.exports = {
    suggestKeyup: function () {
        let $input = $("[vic-suggest]");
        let $tmp = $("[vic-suggest-prototype]");
        let $prototype = $tmp.clone();
        $tmp.remove();
        let $results = $("[vic-suggest-results]");

        $input.on("blur", evt => {
            $results.addClass("hidden");
            $results.html('');
        });

        var xhr;
        $input.on("input", evt => {

            $results.addClass("hidden");
            $results.html('');

            let $this = $(evt.target);
            if (xhr != null) {
                xhr.abort();
            }

            xhr = $.ajax({
                url: "/suggest",
                method: "POST",
                data: {q: $this.val()}
            });

            xhr.fail(evt => {
                console.error("error", evt);
            });

            xhr.done(data => {

                $results.removeClass("hidden");
                vLog("done", data);
                try {
                    var suggestions = JSON.parse(data);
                } catch (e) {
                    throw e;
                }

                suggestions.forEach(item => {
                    let $cur = $prototype.clone();
                    $cur.find("a").text(item.name);
                    $results.append($cur);
                });

            });
        });


        /*
        var vXhr;
        $input.keyup(evt => {
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
                i
            })
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
        */
    },
};
