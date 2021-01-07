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
            redirect();
            clearInterval(interval)
            $('.lyricMaster-timer').html('Times Up!');

            // $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
            // setTimeout(function () {
            //     if (cardIndexShowing + 1 === totalCardCount) {
            //         $('.all-cards').eq(cardIndexShowing).addClass('hide')
            //         $('.lyricMaster-timer').html('')
            //         $('.final-screen').removeClass('hide')
            //     } else {
            //         $('audio')[cardIndexShowing].pause()
            //         $('.all-cards').eq(cardIndexShowing).addClass('hide')
            //         $('.lyricMaster-timer').html('')
            //         cardIndexShowing++
            //         $('.all-cards').eq(cardIndexShowing).removeClass('hide')
            //         timerStart()
            //     }
            // }, 2000)
        }
    }, 1000)
}

    let lyricMasterQuizForm = $("#lyricMasterQuizForm");
    function redirect() {
        lyricMasterQuizForm.submit();
    }

$('#startButton').click(function () {
    $(".game-start").addClass("hide")
    $(".lm-cards").first().removeClass("hide")
    startTime = $('.lyricMaster-timer')[0].innerHTML
    timerStart()
})

    $("#showAnswerBtn").click(function() {
        $("#showAnswer").addClass("highlight");
    });

    //shows previous and next cards
    var prev = $('.prev');
    var next = $('.next');
    var lyricCards = $('.lyricCards .LMcard');

    function showCard( node ){
        node.addClass('active') // show specified node
            .siblings().removeClass('active'); // hide previously active node
    }
    next.on('click', function(e){
        e.preventDefault(); // avoid scrolling to top or following the link

        var nextNode = lyricCards.filter('.active').next();
        showCard(nextNode);
    });

    $('.lyricCards .LMcard:first').addClass('active');

    prev.on('click', function(e){
        e.preventDefault(); // avoid scrolling to top or following the link

        var previousNode = lyricCards.filter('.active').prev();
        showCard(previousNode);
    });


    //hides cards and displays submit when card loop is complete
        ShowTheelement(0);
        $("#previous").addClass('hidden');
        $("#submit").addClass('hidden');

        var dataVal = 0;
        $("#next").click(function () {
            dataVal++;
            $("#previous").removeClass('hidden');
            dataVal === $("#difficultySelected").length - 1 ? $(this).addClass('hidden') : $(this).removeClass('hidden');
            ShowTheelement(dataVal);
            if (dataVal === $("#difficultySelected").length - 1) {
                $("#submit").removeClass('hidden');
            }
        });

        $("#previous").click(function () {
            dataVal--;
            $("#next").removeClass('hidden');
            dataVal === 0 ? $(this).addClass('hidden') : $(this).removeClass('hidden');
            ShowTheelement(dataVal);
            if (dataVal === $(".idrow[data-questions]").length - 2) {
                $("#submit").addClass('hidden');
            }
        });

})();