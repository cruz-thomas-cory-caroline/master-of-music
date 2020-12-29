(function () {
    "use strict";

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

            if (!ui.draggable.data("originalPosition")) {
                ui.draggable.data("originalPosition",
                    ui.draggable.data("uiDraggable").originalPosition);
            }
        },

        out: function (event, ui) {
            $(this).droppable( "option", "accept", '.words');
            $(this).find("input").val("")
            $(this).removeClass("occupied")
            // console.log("Removing Occupied Class...")
            if (!$(this).hasClass('occupied')) {
                // console.log("Removed")
            }
        }
    })

    function revertDraggable() {
        $('.words').each(function() {
            var $this = $(this),
                position = $this.data("originalPosition");

            if (position) {
                $this.animate({
                    left: position.left,
                    top: position.top
                }, 500, function() {
                    $this.data("originalPosition", null);
                });
            }
        });

        $('.drop-zone').each(function() {
            $(this).removeClass('occupied')
            $(this).addClass('unoccupied')
            $(this).find("input").val("")
        })
    }

    function snapToMiddle(dragger, target) {
        var offset = target.offset();
        var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
        var leftMove = (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
        dragger.offset({top: topMove + offset.top, left: leftMove + offset.left})
    }

    var totalCardCount = $('.all-cards').length
    var cardIndexShowing = 0

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
    var timeEnd = 0

    function timerStart() {
        var timeStart = startTime
        $('.unscramble-timer').html(timeStart)
        interval  = setInterval(function () {
            if (timeStart !== timeEnd) {
                timeStart--
                $('.unscramble-timer').html(timeStart)
            } else {
                clearInterval(interval)
                $('.unscramble-timer').html('Times Up!')
                $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
                setTimeout(function () {
                    if (cardIndexShowing + 1 === totalCardCount) {
                        $('.all-cards').eq(cardIndexShowing).addClass('hide')
                        $('.unscramble-timer').html('')
                        $('.final-screen').removeClass('hide')
                    } else {
                        $('.all-cards').eq(cardIndexShowing).addClass('hide')
                        $('.unscramble-timer').html('')
                        cardIndexShowing++
                        $('.all-cards').eq(cardIndexShowing).removeClass('hide')
                        timerStart()
                    }
                }, 2000)
            }
        }, 1000)
    }

    $('#startButton').click(function () {
        $(this).addClass('hide')
        $(".all-cards").first().removeClass('hide')
        startTime = $('.unscramble-timer')[0].innerHTML
        console.log(startTime)
        timerStart()
    })

    $(".cat-button").click(function () {
        $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
        console.log($(this).parent().parent().find("input")[0].value)
        $('.all-cards').eq(cardIndexShowing).addClass('hide')
        clearInterval(interval)
        timerStart()
        $('.all-cards').eq(cardIndexShowing+1).removeClass('hide')
        cardIndexShowing++

    })

    $('.last-cat-button').click(function () {
        $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
        console.log($(this).parent().parent().find("input")[0].value)
        $('.all-cards').eq(cardIndexShowing).addClass('hide')
        clearInterval(interval)
        $('.final-screen').removeClass('hide')

    })

    $('#undo-button').click(function() {
        revertDraggable();
    })




})();