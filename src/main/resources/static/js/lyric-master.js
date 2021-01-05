(function () {
    "use strict";

var interval;
var startTime;
var timeEnd = 0

function timerStart() {
    var timeStart = startTime
    $('.lyricMaster-timer').html(timeStart)
    interval = setInterval(function () {
        if (timeStart !== timeEnd) {
            timeStart--
            $('.lyricMaster-timer').html(timeStart)
        } else {
            clearInterval(interval)
            $('.lyricMaster-timer').html('Times Up!')
            $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
            setTimeout(function () {
                if (cardIndexShowing + 1 === totalCardCount) {
                    $('.all-cards').eq(cardIndexShowing).addClass('hide')
                    $('.lyricMaster-timer').html('')
                    $('.final-screen').removeClass('hide')
                } else {
                    $('audio')[cardIndexShowing].pause()
                    $('.all-cards').eq(cardIndexShowing).addClass('hide')
                    $('.lyricMaster-timer').html('')
                    cardIndexShowing++
                    $('.all-cards').eq(cardIndexShowing).removeClass('hide')
                    timerStart()
                }
            }, 2000)
        }
    }, 1000)
}

$('#startButton').click(function () {
    // $('.game-start').addClass('hide')
    // $(".all-cards").first().removeClass('hide')
    startTime = $('.lyricMaster-timer')[0].innerHTML
    console.log(startTime)
    timerStart()
})

    $('#showAnswerBtn').click(function() {
        $('#showAnswer').addClass("highlight");

    });

})();