$(document).ready(function() {
    $(".article-list").on("click", ".doc-title, .doc-date", function() {
        location.href = "/board/" + $("#INFO > #context").val() + "/reader/" + parseInt($(this).siblings(".doc-idx").html());
    })

    $(".article-list").on("click", ".article-container", function() {
        $(this).addClass("selected");
    })

    $("#newArticle").click(function() {
        location.href = "/board/" + $("#INFO > #context").val() + "/editor";
    })
})