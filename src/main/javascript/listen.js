function thisPageFullUrl() {
    return window.location.href;
}

function bindShare() {
    var title = $("title").text();
    $("[vic-network]").each(function (ii, elm) {
        var $this = $(elm);

        var network = $this.attr("vic-network");
        var encodedUrl = encodeURIComponent(thisPageFullUrl());
        if (network == "sms") {
            var link = "sms:?body=" + encodedUrl;
        } else {
            var link = "http://rest.sharethis.com/v1/share/share?"
                + "destination=" + encodeURIComponent(network) + "&"
                + "url=" + encodedUrl + "&"
                + "title=" + encodeURIComponent(title);
        }

        $this.attr('href', link);
    });

    $("#thislink").val(thisPageFullUrl());
    $("#thislink").on("focus", evt => {
        let target = evt.target;
        target.setSelectionRange(0, target.value.length);
    });
}


module.exports = {
    buildPlayer: () => {
        if (typeof window.episode == "undefined") {
            return;
        }

        var $audio = $("<audio>", {
            controls: true
        });

        var $source = $("<source>", {
            src: window.episode.url
        });

        $audio.append($source);
        $("[vic-audio-box]").append($audio);
        $source.get(0).addEventListener("error", evt => {
            $audio.hide();
            $("#audio-error").removeClass("hidden");
        });

        setTimeout(bindShare, 1);
    }
};
