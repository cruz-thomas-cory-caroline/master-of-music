(function () {
    "use strict";

    $("#playButton").hide();
    $("#lyricOptions").hide();
    $("#triviaOptions").hide();
    $("#backButton").hide();
    $('#audioButtonOff').hide();
    $("#menuRecord").hide();
    $("#wordMenu").hide();
    $("#unscrambleOptions").hide();
    $("#musicTheoryOptions").hide();

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
            $("#musicTheorySection").hide();
            $("#unscrambleLyricSection").hide();
            $("#backButton").show();
            $(".divMoveDown").css("margin-top", "18%");
        });

        lyricSelection.click(function() {
            $("#triviaSection").hide();
            $("#lyricOptions").show();
            $("#musicTheorySection").hide();
            $("#unscrambleLyricSection").hide();
            $("#backButton").show();
            $(".divMoveDown").css("margin-top", "18%");
        });

        $("#musicTheorySelection").click(function() {
            $("#unscrambleLyricSection").hide();
            $("#musicTheoryOptions").show();
            $("#triviaSection").hide();
            $("#lyricSection").hide();
            $("#backButton").show();
        });

        $("#unscrambleLyricSelection").click(function() {
            $("#musicTheorySection").hide();
            $("#unscrambleOptions").show();
            $("#lyricSection").hide();
            $("#triviaSection").hide();
            $("#backButton").show();
        });

        // TRIVIA-GAME JS
        let diffSelection = $("input[type='radio'][name='difficultyOptions']:checked").val();
        if (diffSelection === "easy") {
            $(".carousel-item").setAttribute("data-interval", "false");
        } else if (diffSelection === "medium") {
            $(".carousel-item").setAttribute("data-interval", "10000");
        }

    });

})();