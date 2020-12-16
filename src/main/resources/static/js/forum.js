(function () {
    "use strict";

    $('#new-post').hide()
    $('#post-form').click(function () {
        $('#new-post').show()
        $('#post-form').hide()
    })

    $('.post-reply').hide()

    $('#close-post').click(function () {
        $('#new-post').hide()
        $('#post-form').show()
    })

    let buttonValue;

    $('.reply').click(function () {
        $('.post-reply').hide()
        buttonValue = $(this).attr("value");
        $('#'+buttonValue).show()
        console.log(buttonValue)
    })

    $('.close-reply').click(function () {
        buttonValue = $(this).attr("value");
        $('#'+buttonValue).hide()
        console.log(buttonValue)
    })

})();