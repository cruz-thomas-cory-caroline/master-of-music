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
    $(".carControls").hide();

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

        $('.unscramble-diff-options').click(function () {
            $('.unscramble-diff-options').each(function () {
                $(this).removeClass('unscramble-diff-selected')
            })
            $(this).addClass('unscramble-diff-selected')
        })

        $('.unscramble-genre-options').click(function () {
            $('.unscramble-genre-options').each(function () {
                $(this).removeClass('unscramble-genre-selected')
            })
            $(this).addClass('unscramble-genre-selected')
        })

    });

})();