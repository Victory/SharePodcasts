import {vLog} from "./vic";
import {disablesEventName, loadsEventName} from "./buttons";

module.exports = {
    suggestKeyup: function () {

        let $input = $("[vic-suggest]");
        let $tmp = $("[vic-suggest-prototype]");
        let $prototype = $tmp.clone();
        let $rowId = $("[name=rowId]");
        let $button = $("[vic-suggest-button]");
        let $form = $button.parents("form").first();

        $tmp.remove();
        let $results = $("[vic-suggest-results]");

        let clearSuggestions = evt => {
            $results.addClass("hidden");
            $results.html('');
        };

        // reset input
        $input.val('');
        // disable on load
        $input.trigger(disablesEventName, "disable");
        // clear suggestions on focus
        $input.on("focus", clearSuggestions);

        var xhr;
        $input.on("input", evt => {
            $input.trigger(loadsEventName, "show");
            let $this = $(evt.target);

            $rowId.val("");

            $results.addClass("hidden");
            $results.html('');

            if ($this.val().length < 3) {
                $input.trigger(disablesEventName, "disable");
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
                if (evt.statusText == "abort") {
                    return;
                }
                $input.trigger(loadsEventName, "hide");
            });

            xhr.done(data => {
                $results.removeClass("hidden");
                $input.trigger(loadsEventName, "hide");

                try {
                    var suggestions = JSON.parse(data);
                } catch (e) {
                    throw e;
                }

                suggestions.data.forEach(item => {
                    let $cur = $prototype.clone();
                    let $a = $cur.find("a");
                    $a.attr('href', '/l/' + item.rowId);

                    let $episode = $a.find("[vic-episode-title]");
                    $episode.text(item.name);
                    let $show = $a.find("[vic-show-title]");
                    $show.text(item.showTitle);

                    $cur.click(evt => {
                        evt.preventDefault();
                        $rowId.val(item.rowId);
                        clearSuggestions(evt);
                        $input.val(item.name);

                        $input.trigger(disablesEventName, "enable");
                    });
                    $results.append($cur);
                });

            });
        }); // on input


        $button.click(() => {
            $form.submit();
            //console.log(evt.target);
        }); // on submit
    },
};
