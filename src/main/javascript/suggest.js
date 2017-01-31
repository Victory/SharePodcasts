import {getText, getLabelFor, setText, vLog, listen, qs, getVal, ajaxPost} from "./vic";

module.exports = {
    suggestKeyup: function () {
        var input = qs("[vic-suggest]");
        input.value = "";

        listen(input, "keyup", evt => {
            let val = getVal(evt);
            if (val.length < 3) {
                return;
            }
            var options = {
                data: "q=" + encodeURIComponent(val)
            };
            ajaxPost("/suggest", options);
        });

        listen(input, "focus", evt => {
            setText(getLabelFor(evt), "");
        });
    },
};
