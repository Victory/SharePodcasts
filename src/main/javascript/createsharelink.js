

module.exports = {
    bindSkipCustomShareLink: () => {
        let $btn = $("[vic-custom-link-skip]");
        if ($btn.length < 1) {
            return;
        }

        $btn.click((evt) => {
            let $this = $(evt.target);
            let objectId = $this.attr('vic-custom-link-skip');
            document.location = '/l/' + objectId;
        });
    },
};