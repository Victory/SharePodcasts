import {vLog} from "./vic";

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

        $input.val('');
        $input.on("focus", clearSuggestions);

        var xhr;
        $input.on("input", evt => {
            let $this = $(evt.target);

            $rowId.val("");

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
                try {
                    var suggestions = JSON.parse(data);
                } catch (e) {
                    throw e;
                }

                suggestions.forEach(item => {
                    let $cur = $prototype.clone();
                    let $a = $cur.find("a");
                    $a.text(item.name);
                    $a.attr('href', '#' + item.rowId);

                    $cur.click(evt => {
                        evt.preventDefault();
                        $rowId.val(item.rowId);
                        clearSuggestions(evt);
                        $input.val(item.name);
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
