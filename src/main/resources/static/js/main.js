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

        let difficultyLevel = $("#difficultySelected").val();
        console.log(difficultyLevel);

        let timeLeft;
        if (difficultyLevel === "easy") {
            timeLeft = 60;
        } else if (difficultyLevel === "medium") {
            timeLeft = 45;
        } else if (difficultyLevel === "hard") {
            timeLeft = 30;
        }

        $("#startGameButton").click(function() {
            let downloadTimer = setInterval(function(){
                if(timeLeft <= 0){
                    redirect();
                    clearInterval(downloadTimer);
                } else {
                    document.getElementById("timer").innerHTML = timeLeft;
                }
                timeLeft -= 1;
            }, 1000);
        })

        let quizForm = $("#quizForm");
        function redirect() {
            quizForm.submit();
        }

    });

})();