import {showError, vLog} from "./vic";
import {disablesEventName, loadsEventName} from "./buttons";

module.exports.importFeed = () => {

    let $form = $("[vic-import-feed-form]");
    let $input = $("[vic-import-feed-input]");
    let $button = $("[vic-import-feed-btn]");
    let $alertBox = $("[vic-import-alert-box]");


    // clear the input
    $input.val("");
    // disable the submit button
    $input.trigger(disablesEventName, "disable");

    // when we have good input enable button
    $input.on('input', (evt) => {
        var $this = $(evt.target);
        var val = $this.val();
        if (val.length < 7) {
            $input.trigger(disablesEventName, "disable");
            return;
        }
        $input.trigger(disablesEventName, "enable");
    });

    let addFeed = evt => {
        evt.preventDefault();

        $input.trigger(loadsEventName, "show");
        $input.trigger(disablesEventName, "disable");
        $input.prop('disable');

        let val = $input.val();
        let xhr = $.ajax({
            url: "/add-feed",
            data: {url: val},
            method: "POST",
            dataType: "json",
        });

        xhr.done(data => {
            $input.trigger(loadsEventName, "hide");
            $input.trigger(disablesEventName, "enable");
            $input.val('');
            $input.prop('enable');

            if (!data.success) {
                showError(data);
                return;
            }

            let alert = $("<div>", {
                "class": "alert alert-dismissible alert-success",
                text: data.msg
            });

            $alertBox.html('');
            $alertBox.append(alert);

        });

        xhr.fail(evt => {
            $input.trigger(loadsEventName, "hide");
            $input.trigger(disablesEventName, "enable");
            $input.prop('enable');

            vLog("fail", evt);
            showError("error", evt);
        });
    };

    $button.click(addFeed);
    $form.submit(addFeed)
    $alertBox.click(evt => {
         $alertBox.html('');
    });
};
