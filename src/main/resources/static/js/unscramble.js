(function () {
    "use strict";

    $(".words").draggable()

    $('.drop-zone').droppable({
        accept: ".words",
        drop: function (event, ui) {
            $(".words").draggable()
            console.log(ui.draggable)
            $(this).find("input").val()
            console.log($(ui).val())
            console.log($(this).find("input").attr("innerHTML"))
            //     ui.draggable.remove();
            //     $(this).append(itemDisplay("cement_build"))
            // }
        }
    })


})();