import {disablesEventName, loadsEventName} from "./buttons";
import {showError} from "./vic";

module.exports = {
    bindContactForm: () => {
        let $form = $("[vic-contact-form]");
        if ($form.length < 1) {
            return;
        }
        let $input = $("#your-name");
        let $comment = $("#msg");
        let $btn = $form.find('button');
        let $contactSuccess = $("[vic-contact-success]");

        $input.trigger(disablesEventName, "disable");

        // Check that we have needed inputs, with a debounce
        let targetCount = 0;
        $form.on("input", () => {
            targetCount += 1;
            const thisCount = targetCount;
            setTimeout(() => {
                if (targetCount > thisCount) {
                    return;
                }
                if ($input.val().length > 1 &&
                    $comment.val().length > 10) {
                    $input.trigger(disablesEventName, "enable");
                } else {
                    $input.trigger(disablesEventName, "disable");
                }
            }, 200);
        });

        $form.submit((evt) => {
            evt.preventDefault();
            $input.trigger(loadsEventName, "show");
            $input.trigger(disablesEventName, "disable");
            $btn.text("Sending...");

            let xhr = $.ajax({
                url: $form.attr('action'),
                method: "POST",
                data: $form.serialize(),
                dataType: "json"
            });

            xhr.done(data => {
                $form.remove();
                $contactSuccess.removeClass("hidden");
            });

            xhr.fail(evt => {
                showError("error", evt);
            });
        });
    },
};