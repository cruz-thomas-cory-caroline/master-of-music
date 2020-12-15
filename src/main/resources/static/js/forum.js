(function () {
    "use strict";

    $('#new-post').hide()
    $('#post-form').click(function () {
        $('#new-post').show()
        $('#post-form').hide()
    })
    $('#close-post').click(function () {
        $('#new-post').hide()
        $('#post-form').show()
    })

})();