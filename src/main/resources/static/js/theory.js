(function () {
    "use strict";

    let timeLimit;
    let difficultyLevel = $("#theoryDifficultySelected").val();

    console.log(difficultyLevel)

    if(difficultyLevel === "option1"){
        timeLimit = 60;
    }else if(difficultyLevel === "option2"){
        timeLimit = 30;
    } else if(difficultyLevel === "option3"){
        timeLimit = 10;
    }

    console.log(timeLimit)

    $(document).ready(function () {
        let downloadTimer = setInterval(function(){
            if(timeLimit <= 0){
                clearInterval(downloadTimer);
            } else {
                $(".carControls").show();
                document.getElementById("timer").innerHTML = timeLimit;
            }
            timeLimit -= 1;
        }, 1000);
    });


})();
