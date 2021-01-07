(function () {
    "use strict";

    $(".carControls").hide();

    $(document).ready(function(){

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
                    $(".carControls").show();
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