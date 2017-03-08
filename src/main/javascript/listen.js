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

    var $thisLink = $("#thislink");
    if ($thisLink.length > 0) {
        $thisLink.val(thisPageFullUrl());
        $thisLink.attr("size", $thisLink.val().length);
        $thisLink.css("font-family", "monospace");
        $thisLink.css("font-size", ".7em");
        $thisLink.on("focus", evt => {
            let target = evt.target;
            target.setSelectionRange(0, target.value.length);
        });
    }
}

/** PoC for turning 10:30 style time stamps to jumps to that timestamp in the audio */
function bindTimeJumps() {
    let $box = $("[vic-time-jumps]");
    if ($box.length < 1) {
        return;
    }

    let text = $box.text();

    let regex = /\s([0-9]+:[0-9]+)\s/gim;
    let replaced = text.replace(regex, ' <a href="#$1" class="timejump">$1</a> ');
    $box.html(replaced);
    $box.find(".timejump").click(evt => {
        evt.preventDefault();
        let $this = $(evt.target);
        let tic = $this.attr('href');
        let bits = tic.substr(1, 100).split(':');
        let time = parseInt(bits[0])*60 + parseInt(bits[1]);

        let audio = document.getElementsByTagName('audio')[0];
        audio.currentTime = time;
        audio.play();
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
        setTimeout(bindTimeJumps, 2);
    }
};
