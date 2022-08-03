import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

const commentURI = "/comment";



$(".commentContainer").on("click", ".button-name", function() {
    var uri = "/user/profile/" + $(this).find(".user_idx").val();

    location.href = uri;
});

$(".commentContainer").on("click", ".collapsible", function() {
    var button = $(this);
    var target = button.closest(".comment").children(".collapContent");
    var comment = button.closest(".comment").children(".comment-content");
    button.toggleClass("active");

    if (button.hasClass("active")) {
        $(target).show(100);

        if ($(target).offset().top + 72 > $(".content").height()) {
            document.getElementById("content").scrollBy({
                top: 72 + $(comment).height(),
                behaviour: 'smooth'
            })
        }
    } else {
        $(target).hide(100);
    }
});

$(".commentContainer").on("click", ".cancel", function() {
    var target = $(this).closest(".comment").children(".collapContent");
    $(this).closest(".comment").find(".collapsible").removeClass("active");
    $(target).hide(100);
});

$(".commentContainer").on("submit", "form", function(event) {
    event.preventDefault();

    var container = $(this).closest(".commentContainer");
    var form = $(this);
    var content = $.trim(form.find("textarea").val());
    if (content == undefined || content.length == 0) {
        new alert("내용을 입력해주세요.");
        return false;
    }
    var ajax = new commonAjax;
    ajax.setupWithForm(form);
    ajax.setOnSuccess(function(response) {
        $(container).load(commentURL(container));
    });
    ajax.setOnError(function(response) {
        console.log(response);
    })
    ajax.ajax();
});

$(".commentContainer").on("click", ".button-editComment", function() {
    var commentArea = $(this).closest(".comment").children(".comment-content");
    commentArea.find(".toggle-hidden").each(function() {
        $(this).toggleClass("hidden");
    });
});

$(".commentContainer").on("click", ".button-update", function() {
    var container = $(this).closest(".commentContainer");
    var content = $(this).closest(".comment").find(".modified-text > textarea").val();
    var comment_idx = $(this).closest(".comment").find(".comment-info > input").val();

    var ajax = new commonAjax;
    ajax.setURL(commentURI);
    ajax.setType("PUT");
    ajax.setContentType("application/json");
    ajax.setData(JSON.stringify({
        "comment_idx": comment_idx,
        "content": content
    }));
    ajax.setOnSuccess(function(response) {
        $(container).load(commentURL(container));
    });
    ajax.ajax();
});

$(".commentContainer").on("click", ".button-deleteComment", function() {
    var conf = confirm("댓글을 삭제하시겠습니까?");
    if (conf) {
        var container = $(this).closest(".commentContainer");
        var comment_idx = $(this).closest(".comment").find(".comment-info > input").val();

        var ajax = new commonAjax;
        ajax.setURL(commentURI + "?" + $.param({ "comment_idx": comment_idx }));
        ajax.setType("delete");
        ajax.setOnSuccess(function(response) {
            $(container).load(commentURL(container));
        });
        ajax.ajax();
    }
});

function commentURL(container) {
    var articleIndex = $(container).find(".C-INFO > input[name='article_idx']").val();
    return commentURI + "/box/" + articleIndex;
}