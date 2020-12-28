(function () {
    "use strict";

    $(".all-cards").hide();
    $(".final-screen").hide();

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
            $(this).droppable( "option", "accept", ui.draggable);
            $(this).removeClass("unoccupied")
            $(this).addClass('occupied')
            $(this).find("input").val(ui.draggable[0].innerHTML)
            snapToMiddle(ui.draggable, $(this))
        },

        out: function (event, ui) {
            $(this).droppable( "option", "accept", '.words');
            $(this).find("input").val("")
            $(this).removeClass("occupied")
            console.log("Removing Occupied Class...")
            if (!$(this).hasClass('occupied')) {
                console.log("Removed")
            }
        }
    })

    function snapToMiddle(dragger, target) {
        var offset = target.offset();
        var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
        var leftMove = (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
        dragger.offset({top: topMove + offset.top, left: leftMove + offset.left})
    }

    var totalCardCount = $('.all-cards').length
    var cardIndexShowing = 0
    var timeEnd = -1

    function lockAnswer() {
        var count = $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).length
        var lyricSet = []
        for (var i = 0; i < count; i++) {
            var word = $('.all-cards').eq(cardIndexShowing).find($('.droppedWord'))[i].value
            if (word !== "") {
                lyricSet.push(word)
            }
        }
        let playerAnswer = lyricSet.join(" ")
        return playerAnswer;
    }
    var interval;
    var startTime;

    function timerStart() {
        var timeStart = startTime
        $('.timer').html(timeStart)
        interval  = setInterval(function () {
            if (timeStart !== timeEnd) {
                $('.timer').html(timeStart - (timeEnd + 1))
                timeStart--
            } else {
                clearInterval(interval)
                $('.timer').html('Times Up!')
                $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
                setTimeout(function () {
                    if (cardIndexShowing + 1 === totalCardCount) {
                        $('.all-cards').eq(cardIndexShowing).hide()
                        $('.timer').html('')
                        $('.final-screen').show()
                    } else {
                        $('.all-cards').eq(cardIndexShowing).hide()
                        $('.timer').html('')
                        cardIndexShowing++
                        $('.all-cards').eq(cardIndexShowing).show()
                        timerStart()
                    }
                }, 2000)
            }
        }, 1000)
    }

    $('#startButton').click(function () {
        $(this).hide()
        $(".all-cards").first().show()
        startTime = $('.timer')[0].innerHTML
        timerStart()
    })

    $(".cat-button").click(function () {
        $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
        console.log($(this).parent().parent().find("input")[0].value)
        $('.all-cards').eq(cardIndexShowing).hide()
        clearInterval(interval)
        timerStart()
        $('.all-cards').eq(cardIndexShowing+1).show()
        cardIndexShowing++

    })

    $('.last-cat-button').click(function () {
        $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
        console.log($(this).parent().parent().find("input")[0].value)
        $('.all-cards').eq(cardIndexShowing).hide()
        clearInterval(interval)
        $('.final-screen').show()

    })




})();