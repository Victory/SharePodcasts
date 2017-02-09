import {showError, vLog} from "./vic";

module.exports.importFeed = () => {

    let $form = $("[vic-import-feed-form]");
    let $input = $("[vic-import-feed-input]");
    let $button = $("[vic-import-feed-button]");
    let $alertBox = $("[vic-import-alert-box]");

    let addFeed = evt => {
        evt.preventDefault();

        var val = $input.val();
        var xhr = $.ajax({
            url: "/add-feed",
            data: {url: val},
            method: "POST",
            dataType: "json",
        });

        xhr.done(data => {
            console.log("done", data);
            if (!data.success) {
                showError(data);
                return;
            }

            var alert = $("<div>", {
                "class": "alert alert-dismissible alert-success",
                text: data.msg
            });

            $alertBox.html('');
            $alertBox.append(alert);

        });

        xhr.fail(evt => {
            console.log(evt.responseText);
            console.log(JSON.parse(evt.responseText));
            vLog("fail", evt);
            showError("error", evt);
        });
    };

    $button.click(addFeed);
    $form.submit(addFeed)
    $alertBox.click(evt => {
         $alertBox.html('');
    });

    console.log("importFeed");
};
