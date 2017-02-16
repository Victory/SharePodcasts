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
    }
};
