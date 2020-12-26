(function () {
    "use strict";

    $(".all-cards").hide();
    $(".all-cards").first().show()
    $(".final-screen").hide()

    $(".words").draggable({
        opacity: .4,
        revert: function (event, ui) {
            $(this).data("uiDraggable").originalPosition = {
                top: 0,
                left: 0
            };
            return !event;
        }
    })

    $('.drop-zone').droppable({
        accept: ".words",
        drop: function (event, ui) {
            $(".words").draggable()
            console.log(ui.draggable)
            $(this).css('border', 'none')
            $(this).find("input").val(ui.draggable[0].innerHTML)
            console.log($(this).find("input").val())
            snapToMiddle(ui.draggable, $(this))
        },
        out: function () {
            $(this).css({'border': 'solid black 1px', 'width': '75px', 'height': '50px'})
        }
    })

    function snapToMiddle(dragger, target) {
        var offset = target.offset();
        var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
        var leftMove = (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
        dragger.offset({top: topMove + offset.top, left: leftMove + offset.left})
    }

    function lockAnswer() {
        var count = $(this).parent().parent().parent().children().children()[0].childElementCount
        var lyricSet = []
        for (var i = 0; i < count; i++) {
            var word = $(this).parent().parent().parent().children().children().children().children().children()[i].value
            if (word !== "") {
                lyricSet.push(word)
            }
        }
        let playerAnswer = lyricSet.join(" ")
        $(this).parent().parent().find("input").val(playerAnswer)
    }

    var totalCardCount = $('.all-cards').length
    var cardShowing = 0
    var timeEnd = -1

    $(".cat-button").click(function () {
        cardShowing++
        lockAnswer()
        console.log($(this).parent().parent().find("input")[0].value)
        $(this).parent().parent().parent().hide()
        $(this).parent().parent().parent().parent().next().first().show()
        timerStart()
    })

    $('.last-cat-button').click(function () {
        lockAnswer()
        console.log($(this).parent().parent().find("input")[0].value)
        $(this).parent().parent().parent().hide()
        $('.final-screen').show()
    })

    function timerStart() {
        console.log("here")
        var timeStart = 5
        var countdown = setInterval(function () {
            if (timeStart !== timeEnd) {
                $('.timer').html(timeStart - (timeEnd + 1))
                timeStart--
            } else {
                clearInterval(countdown)
                $('.timer').html('Times Up!')
                setTimeout(function () {
                    if (cardShowing + 1 === totalCardCount) {
                        $('.all-cards').eq(cardShowing).hide()
                        $('.timer').html('')
                        $('.final-screen').show()
                    } else {
                        $('.all-cards').eq(cardShowing).hide()
                        $('.timer').html('')
                        $('.all-cards').eq(cardShowing + 1).show()
                        timerStart()
                        cardShowing++
                    }
                }, 2000)
            }
        }, 1000)
    }

    timerStart()

})();