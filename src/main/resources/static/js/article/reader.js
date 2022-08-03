import {commonAjax} from "../ajax.js";
import {alert} from "../tools.js";

$(document).ready(function() {
    bindReader();
    initBtns();
    initVote();
});

function bindReader() {
    //NOTE: 페이지 로드할 때 초기화
    $(".reader-container").each(function() {
        initReader(this);
    })
}

function initReader(container) {
    var readContainer = $(container).find(".quill-reader").get(0);

    var quill = new Quill(readContainer, {
        modules: {
            syntax: true,
            toolbar: false
        },
        readOnly: true,
        theme: "snow"
    });

    var content = JSON.parse($(readContainer).text());
    quill.setContents(content);
    $(readContainer).css("visibility", "visible");
}

function initBtns() {
    $(".article-area, .answer-area").on("click", ".button-delete", function() {
        if (confirm("글을 삭제하시겠습니까?")) {
            var article_idx = $(this).closest(".reader-container").find("input[name=article_idx]").val();
            var uri = "/article";

            var ajax = new commonAjax;
            ajax.setURL(uri + "?" + $.param({ "article_idx": article_idx }));
            ajax.setType("DELETE");
            ajax.setOnSuccess(function(response) {
                if ($(this).hasClass("true")) {
                    location.reload();
                } else {
                    location.href = "/board/" + $("#INFO > #context").val() + "/list/";
                }
            });
            ajax.ajax();
        }
    });

    $(".article-area, .answer-area").on("click", ".button-modify", function() {
        var article_idx = $(this).closest(".reader-container").find("input[name=article_idx]").val();
        var uri = "/board/" + $("#INFO > #context").val() + "/editor";

        location.href = uri + "/" + article_idx;
    });

    $(".article-area, .answer-area").on("click", ".button-writter", function() {
        var uri = "/user/profile/" + $("input[name=article_user_idx]").val();

        location.href = uri;
    });

    $("#newAnswer-area > button").click(function() {
        location.href = "/board/" + $("#INFO > #context").val() + "/answer/" + $("#INFO > #article_idx").val();
    });

    $(".article-list").on("click", ".doc-title, .doc-date", function() {
        location.href = "/board/" + $("#INFO > #context").val() + "/reader/" + $(this).siblings(".doc-idx").html();
    });

    $(".article-list").on("click", ".article-container", function() {
        $(this).addClass("selected");
    })

    $(".underbar > #toList").click(function() {
        location.href = "/board/" + $("#INFO > #context").val() + "/list/" + $("#INFO > #page").val();
    });
}

function initVote() {
    $(".vote").each(function(index, item) {
        var article_idx = $(this).closest(".reader-container").find("input[name=article_idx]").val();

        var ajax = new commonAjax;
        ajax.setURL("/vote/" + article_idx);
        ajax.setType("GET");
        ajax.setOnSuccess(function(response) {
            var res = JSON.parse(response);
            $(item).html(res.totalVote);

            if (res.userVote == 1) {
                $(item).siblings(".upVote").addClass("voted");
            } else if (res.userVote == 0) {
                $(item).siblings(".downVote").addClass("voted");
            }

            var vote = $(item);
            var value = parseInt(vote.html());

            vote.removeClass("positive negative zerosum");
            if (value == 0)
                vote.addClass("zerosum");
            else if (value < 0)
                vote.addClass("negative");
            else
                vote.addClass("positive");
        });
        ajax.ajax();
    })

    $(".post").on("click", ".voteBtn", function() {
        var target = $(this);
        var articleIndex = $(this).closest(".reader-container").find("input[name=article_idx]").val();
        var upVote = $(this).hasClass("upVote") ? true : false;

        var ajax = new commonAjax;
        ajax.setURL("/vote/" + articleIndex);
        ajax.setType("POST");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify(upVote));
        ajax.setOnSuccess(function(response) {
            $(target).siblings(".vote").html(response);
            $(target).siblings(".voted").toggleClass("voted");
            $(target).toggleClass("voted");

            var vote = $(target).siblings(".vote");
            var value = parseInt(vote.html());

            vote.removeClass("positive negative zerosum");
            if (value == 0)
                vote.addClass("zerosum");
            else if (value < 0)
                vote.addClass("negative");
            else
                vote.addClass("positive");
        });
        ajax.setOnError(function() {
            new alert("자기 글에 투표할 수 없습니다");
        });

        ajax.ajax();
    })
}