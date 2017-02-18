const disablesEventName = "vic-btn-disables";
const loadsEventName = "vic-btn-loads";

function bindDisableEnable($input, $btnBox) {
    if ($input.length < 1 || $btnBox.length < 1) {
        return;
    }

    let $button = $btnBox.find("button");
    /**
     * @param {String} action - either "enable" or "disable"
     */
    $input.bind(disablesEventName, (evt, action) => {
        if (action == "enable" && $button.prop('disabled')) {
            $button.prop('disabled', false);
        } else if (action == "disable" && !$button.prop('disabled')) {
            $button.prop('disabled', true);
        }
    });
}

function bindHideShow($imgBox, $input) {
    let $img = $imgBox.find("img");
    /**
     * @param {String} action - either "show" or "hide"
     */
    $input.bind(loadsEventName, (evt, action) => {
        if (action == "show") {
            $img.removeClass("hidden");
        } else if (action == "hide") {
            $img.addClass("hidden");
        }
    });
}
module.exports = {
    /** The name of the event to $.trigger to enable or disable the target button */
    disablesEventName: disablesEventName,
    /** The name of the event to $.trigger to show or hide the target loading image */
    loadsEventName: loadsEventName,

    /**
     * Binds the following hide/show img and enable/disable button
     *
     * Use the following template
     *
     * <input
     *    vic-btn
     *    vic-btn-disables="disables-box"
     *    vic-btn-loads="loads-box">
     *
     * Where the button is in $("#disables-box") and the img is in $("#loads-box")
     *
     * Trigger with:
     *
     * $.trigger(disablesEventName, "disable"|"enable")
     *
     * $.trigger(loadsEventName, "show"|"hide")
     */
    bindButtonUiActions: () => {
        var $elms = $("input[vic-btn]");
        $elms.each((ii, input) => {
            let $input = $(input);
            let $btnBox = $("#" + $input.attr("vic-btn-disables"));
            let $imgBox = $("#" + $input.attr("vic-btn-loads"));

            bindDisableEnable($input, $btnBox);
            bindHideShow($imgBox, $input);
        });
    },
};