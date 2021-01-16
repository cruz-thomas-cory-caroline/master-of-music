(function () {
    "use strict";

    // $('#new-post').hide()
    // $('.post-reply').hide()
    // $('.post-edit-buttons').hide()
    // $('.comment-edit-buttons').hide()

    $('#post-form').click(function () {
        $('#new-post').slideDown()
        $('#post-form').hide()
    })

    $('#close-post').click(function () {
        $('#new-post').slideUp()
        $('#post-form').show().delay(3000)
    })

    let buttonValue;

    $('.view-replies').click(function () {
        if ($(this).hasClass('showing-comments')) {
            $(this).removeClass('showing-comments')
            $(this).parent().parent().parent().find($('.comment-section')).eq(0).slideUp()
        } else {
            $(this).addClass('showing-comments')
            $(this).parent().parent().parent().find($('.comment-section')).eq(0).slideDown()
        }
    })

    $('.reply').click(function () {
        $('.post-reply').hide()
        buttonValue = $(this).attr("value");
        $('#' + buttonValue).show()
        console.log(buttonValue)
    })

    $('.close-reply').click(function () {
        buttonValue = $(this).attr("value");
        $('#' + buttonValue).hide()
        console.log(buttonValue)
    })

    let previousPostTitle;
    let previousPostBody;

    $('.post-edit').click(function () {
        buttonValue = $(this).attr("value");
        // previousPostTitle = $('#title-edit'+buttonValue)[0].outerHTML
        previousPostBody = $('#body-edit' + buttonValue)[0].outerHTML
        // $('#title-edit'+buttonValue).replaceWith($('<input name="post-title" style="width: 50%"></input>').val($('#title-edit'+buttonValue).text()).attr('id', "input-post-title"+buttonValue));
        $('#body-edit' + buttonValue).replaceWith($('<textarea name="post-body" class="post-body-edit" style="width: 100%; height: 100px"></textarea>').text($('#body-edit' + buttonValue).text()).attr('id', "input-post-body" + buttonValue));
        $('#post-edit-buttons' + buttonValue).show()
        $('#post-admin-buttons' + buttonValue).hide()
    });

    $('.close-post-edit').click(function () {
        buttonValue = $(this).attr("value");
        $('#input-post-title' + buttonValue).replaceWith(previousPostTitle);
        $('#input-post-body' + buttonValue).replaceWith(previousPostBody);
        $('#post-admin-buttons' + buttonValue).show()
        $('#post-edit-buttons' + buttonValue).hide()
    })

    let previousCommentBody;

    $('.comment-edit').click(function () {
        buttonValue = $(this).attr("value");
        previousCommentBody = $('#comment-edit' + buttonValue)[0].outerHTML
        $('#comment-edit' + buttonValue).replaceWith($('<textarea name="comment-body" style="width: 100%; height: 100px"></textarea>').text($('#comment-edit' + buttonValue).text()).attr('id', "input-comment-body" + buttonValue));
        $('#comment-edit-buttons' + buttonValue).show()
        $('#comment-admin-buttons' + buttonValue).hide()
    });

    $('.close-comment-edit').click(function () {
        buttonValue = $(this).attr("value");
        $('#input-comment-body' + buttonValue).replaceWith(previousCommentBody);
        $('#comment-admin-buttons' + buttonValue).show()
        $('#comment-edit-buttons' + buttonValue).hide()
    })

})();