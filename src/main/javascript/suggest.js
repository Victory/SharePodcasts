import {vLog} from "./vic";

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
            let $this = $(evt.target);

            $results.addClass("hidden");
            $results.html('');

            if ($this.val().length < 3) {
                return;
            }

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
        }); // on input

    },
};
