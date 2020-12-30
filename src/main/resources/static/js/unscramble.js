(function () {
    "use strict";
    console.log($('main'))

    enquire.register("screen and (max-width: 599px)", {
        match: function () {
            $('main').eq(0).removeClass('web')
            $('main').eq(0).addClass('mobile')
            checkType()
        }
    });

    enquire.register("screen and (min-width: 900px)", {
        match: function () {
            $('main').eq(0).removeClass('mobile')
            $('main').eq(0).addClass('web')
            checkType()
        }
    });

    function checkType() {
        if ($('main').eq(0).hasClass('web')) {
            $('.words').off()
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
                    $(this).droppable("option", "accept", ui.draggable);
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
                    $(this).droppable("option", "accept", '.words');
                    $(this).find("input").val("")
                    $(this).removeClass("occupied")
                    // console.log("Removing Occupied Class...")
                    if (!$(this).hasClass('occupied')) {
                        // console.log("Removed")
                    }
                }
            })

            function revertDraggable() {
                $('.words').each(function () {
                    var $this = $(this),
                        position = $this.data("originalPosition");

                    if (position) {
                        $this.animate({
                            left: position.left,
                            top: position.top
                        }, 500, function () {
                            $this.data("originalPosition", null);
                        });
                    }
                });

                $('.drop-zone').each(function () {
                    $(this).removeClass('occupied')
                    $(this).addClass('unoccupied')
                    $(this).find("input").val("")
                    $(this).droppable("option", "accept", '.words');
                })
            }

            $('.undo-button').click(function () {
                revertDraggable();
            })

            function snapToMiddle(dragger, target) {
                var offset = target.offset();
                var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
                var leftMove = (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
                dragger.offset({top: topMove + offset.top, left: leftMove + offset.left})
            }

        } else if ($('main').eq(0).hasClass('mobile')) {

            $('.all-cards').each(function () {
                $(this).find($('.undo-button-class')).eq(0).addClass('hide')
                $(this).find($('.bf-buttons')).eq(0).removeClass('hide')
                $(this).find($('.drop-zone')).eq(0).addClass('highlighted')
            })

            $('.words').click(function clickJump() {
                let dropZoneCount = $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).length
                let x = 0

                while (x < dropZoneCount) {
                    if ($('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).hasClass('unoccupied') && $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).hasClass('highlighted')) {
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).removeClass('unoccupied')
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).addClass('occupied')
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).find("input").val($(this)[0].innerHTML)
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).find("a").removeClass('hide')
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).find("a").text($(this)[0].innerHTML)
                        $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).removeClass("highlighted")
                        x = 0
                        while (x < dropZoneCount) {
                            if ($('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).hasClass("unoccupied") && !$('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).hasClass("highlighted")) {
                                $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).eq(x).addClass("highlighted")
                                break;
                            } else {
                                x = x + 1
                            }

                        }

                        $(this).addClass('hide')
                        break;
                    } else {
                        x = x + 1
                    }
                }

            })

            $('.mobile-word').click(function () {
                $('.all-cards').eq(cardIndexShowing).find($('.drop-zone')).each(function () {
                    $(this).removeClass('highlighted')
                })
                $(this).parent().addClass("highlighted")
                $(this).parent().removeClass('occupied')
                $(this).parent().addClass('unoccupied')
                $(this).addClass('hide')

                let wordToReshow = $(this).text()
                let y = 0

                while (y < $('.all-cards').eq(cardIndexShowing).find($('.words')).length) {
                    if ($('.all-cards').eq(cardIndexShowing).find($('.words')).eq(y).text() === wordToReshow) {
                        $('.all-cards').eq(cardIndexShowing).find($('.words')).eq(y).removeClass('hide')
                        break;
                    } else {
                        y = y + 1
                    }
                }

                $(this).text("")
                $(this).parent().find("input").val("")
            })

        }
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
        interval = setInterval(function () {
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
                        $('audio')[cardIndexShowing].pause()
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
        $('.all-cards').eq(cardIndexShowing + 1).removeClass('hide')
        $('audio')[cardIndexShowing].pause()
        cardIndexShowing++

    })

    $('.last-cat-button').click(function () {
        $('.all-cards').eq(cardIndexShowing).find(".fullAnswer").val(lockAnswer())
        console.log($(this).parent().parent().find("input")[0].value)
        $('.all-cards').eq(cardIndexShowing).addClass('hide')
        clearInterval(interval)
        $('.final-screen').removeClass('hide')
        $('audio')[cardIndexShowing].pause()
    })


    $('.song-clip-button').click(function () {
        console.log($('.all-cards').eq(cardIndexShowing).find($('.title')))
        let songToSearch = $('.all-cards').eq(cardIndexShowing).find($('.title'))[0].innerHTML
        console.log(songToSearch)
        const settings = {
            "async": true,
            "crossDomain": true,
            "url": "https://deezerdevs-deezer.p.rapidapi.com/search?q=" + songToSearch,
            "method": "GET",
            "headers": {
                "x-rapidapi-key": SONG_API_KEY,
                "x-rapidapi-host": "deezerdevs-deezer.p.rapidapi.com"
            }
        };

        $.ajax(settings).done(function (response) {
            console.log(response);
            let artist = $('.artist')[cardIndexShowing].innerHTML
            console.log(artist)
            let audioClipPath = ""
            let clipIndex = 0;

            while (audioClipPath === "" && clipIndex < response.data.length) {
                if (response.data[clipIndex].artist.name === artist) {
                    audioClipPath = response.data[clipIndex].preview
                } else {
                    clipIndex++
                }
            }

            $('source').eq(cardIndexShowing).attr('src', audioClipPath)
            $('.song-clip-button').eq(cardIndexShowing).addClass('hide')
            $('.audio-controls').eq(cardIndexShowing).removeClass('hide')
            $('audio').get(cardIndexShowing).volume = .2
            $('audio').get(cardIndexShowing).load();
            $('audio').get(cardIndexShowing).play();
        });
    })

})();