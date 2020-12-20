(function () {
    "use strict";

    $(".all-cards").hide();
    $(".all-cards").first().show()

    $(".words").draggable({
        opacity: .4,
        revert:  function(event, ui) {
            $(this).data("uiDraggable").originalPosition = {
                top : 0,
                left : 0
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
            //     ui.draggable.remove();
            //     $(this).append(itemDisplay("cement_build"))
            // }
        },
        out: function() {
            $(this).css({'border': 'solid black 1px', 'width': '75px', 'height': '50px'})
        }
    })


    function snapToMiddle(dragger, target) {
        var offset = target.offset();
        var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
        var leftMove = (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
        dragger.offset({top: topMove + offset.top, left: leftMove + offset.left})
    }

    $(".cat-button").click(function () {
        $(this).parent().parent().hide()
        $(this).parent().parent().parent().next().first().show()
    })


})();