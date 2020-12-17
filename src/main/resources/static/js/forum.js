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
    $('#close-post-edit').hide()
    $( "#post-edit" ).click(function() {
        $('.post-title').replaceWith($('<input th:field="*{title}"></input>').val($('.post-title').text()));
        $('.post-body').replaceWith($('<textarea th:field="*{body}"></textarea>').text($('.post-body').text()));
        $('#submit-post-edit').show()
        $('#close-post-edit').show()
        $('.post-buttons').hide()
    });

    $('#close-post-edit').click(function () {
        $('.post-buttons').show()
    })

    $('#close-comment-edit').hide()
    $('#submit-comment-edit').hide()
    $( "#comment-edit" ).click(function() {
        $('.comment-body').replaceWith($('<textarea th:field="*{body}"></textarea>').text($('.comment-body').text()));
        $('#submit-comment-edit').show()
        $('#close-comment-edit').show()
        $('.comment-buttons').hide()
    });

    $('#close-comment-edit').click(function () {
        $('.comment-buttons').show()
    })

})();