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

        // $("#submitQuiz").hide();
        // let difficultySelected;
        //
        // $('#gameSetupForm input').on('change', function() {
        //     difficultySelected = ($('input[name=difficultyOptions]:checked', '#gameSetupForm').val());
        //     console.log(difficultySelected)
        //     if (difficultySelected === "easy") {
        //         $("#submitQuiz").show();
        //     }
        // });

        // var timeleft = 10;
        // var downloadTimer = setInterval(function(){
        //     if(timeleft <= 0){
        //         clearInterval(downloadTimer);
        //         document.getElementById("timer").innerHTML = timeleft;
        //     } else {
        //         document.getElementById("countdown").innerHTML = timeleft;
        //     }
        //     timeleft -= 1;
        // }, 1000);

        let timeLeft = 10;
        $(".carControls").click(function() {
            var downloadTimer = setInterval(function(){
                if(timeLeft <= 0){
                    clearInterval(downloadTimer);
                    document.getElementById("timer").innerHTML = timeLeft;
                } else {
                    document.getElementById("timer").innerHTML = timeLeft;
                }
                timeLeft -= 1;
            }, 1000);
        })

    });

})();