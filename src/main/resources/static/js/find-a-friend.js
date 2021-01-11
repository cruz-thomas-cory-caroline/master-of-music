(function () {
    "use strict";

    $('#find-friend').on('keyup change', userSearch)

    function userSearch() {
        let userSearch = $('#find-friend')[0].value.toLowerCase()

        if (userSearch === "") {
            console.log("String is Empty")
            $('.user').each(function () {
                $(this).removeClass('d-none')
            })
        } else {
            $('.user').each(function () {
                if ($(this).find('h1')[0].innerHTML.toLowerCase().startsWith(userSearch)) {
                    $(this).removeClass('d-none');
                } else {
                    if (!$(this).hasClass('d-none')) {
                        $(this).addClass('d-none')
                    }
                }
            })
        }
    }

    $('.search-button').click(function(){
        $(this).parent().toggleClass('open');
    });



})();