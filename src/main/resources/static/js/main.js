$(document).ready(function() {
    $(".article-container").on("click", ".doc-title, .doc-date", function() {
        var context = "/board/";
        var boardContext = $(this).closest(".article-list").find(".INFO > .context").val();
        var reader = "/reader/";
        var articleIndex = parseInt($(this).siblings(".doc-idx").html());

        window.location.href = context + boardContext + reader + articleIndex;
    })


})