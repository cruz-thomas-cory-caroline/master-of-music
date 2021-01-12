(function () {
    "use strict";

var interval;
var startTime;
var timeEnd = 0
var cardIndexShowing = -1;
var dataVal = 0;


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
        cardIndexShowing++;
    });

    $('.lyricCards .LMcard:first').addClass('active');

    prev.on('click', function(e){
        e.preventDefault(); // avoid scrolling to top or following the link

        var previousNode = lyricCards.filter('.active').prev();
        showCard(previousNode);
        cardIndexShowing--;
    });




    $('#hint').click(function () {
        console.log($('.title'));
        $(this).find($('.title'));
        $('.title').eq(cardIndexShowing).show('slow');

    });

})();