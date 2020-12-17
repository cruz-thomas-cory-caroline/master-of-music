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

    $('#submit-post-edit').hide()
    $( "#post-edit" ).click(function() {
        $('.post-title').replaceWith($('<input th:field="*{title}"></input>').val($('.post-title').text()));
        $('.post-body').replaceWith($('<textarea th:field="*{body}"></textarea>').text($('.post-body').text()));
        $('#submit-post-edit').show()

    });

    $('#submit-comment-edit').hide()
    $( "#comment-edit" ).click(function() {
        $('.comment-body').replaceWith($('<textarea th:field="*{body}"></textarea>').text($('.comment-body').text()));
        $('#submit-comment-edit').show()
    });

})();