(function () {
    "use strict";

    $(".words").draggable({
        opacity: .4,
    })

    $('.drop-zone').droppable({
        accept: ".words",
        drop: function (event, ui) {
            $(".words").draggable()
            console.log(ui.draggable)
            $(this).find("input").val(ui.draggable[0].innerHTML)
            console.log($(this).find("input").val())
            snapToMiddle(ui.draggable,$(this));
            //     ui.draggable.remove();
            //     $(this).append(itemDisplay("cement_build"))
            // }
        }
    })

    function snapToMiddle(dragger, target){
        var offset = target.offset();
        var topMove = (target.outerHeight(true) - dragger.outerHeight(true)) / 2;
        var leftMove= (target.outerWidth(true) - dragger.outerWidth(true)) / 2;
        dragger.offset({ top: topMove + offset.top, left: leftMove + offset.left })
    }


})();