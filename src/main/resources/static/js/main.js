(function () {
    "use strict";

    $("#playButton").hide();
    $("#lyricOptions").hide();
    $("#triviaOptions").hide();
    $("#backButton").hide();
    $('#audioButtonOff').hide();
    $("#menuRecord").hide();
    $("#wordMenu").hide();

    $(document).ready(function(){
        let triviaSelection = $('#triviaSelection');
        let lyricSelection = $('#lyricSelection');
        var audio = new Audio('audio/synthwaveLoop.wav');

        setTimeout(function() {
            $("#playButton").show('slow');
            $("#menuRecord").show('slow');
            $("#wordMenu").show('slow');
        }, 7000)

        $('#audioButton').click(function() {
            audio.play();
                $('#audioButton').hide();
                $('#audioButtonOff').show();
        });

        $('#audioButtonOff').click(function() {
            audio.pause();
            $('#audioButtonOff').hide();
            $('#audioButton').show();
        });

        triviaSelection.click(function() {
            $("#lyricSection").hide();
            $("#triviaOptions").show();
            $("#backButton").show();
        });

        lyricSelection.click(function() {
            $("#triviaSection").hide();
            $("#lyricOptions").show();
            $("#backButton").show();
        });

        $("#backButton").click(function() {
            if ($("#lyricOptions").show()) {
                $("#lyricOptions").hide();
                $("#triviaSection").show();
            } if ($("#triviaOptions").show()) {
                $("#triviaOptions").hide();
                $("#lyricSection").show();
            }
        })

    });

})();