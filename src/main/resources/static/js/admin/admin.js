import {bindPagination} from "../tools.js";

$(document).ready(function() {
    const target = $("#task");

    bindPagination(target);

    $(".min-header").each(function (idx, item) {
        $(item).closest("th").css("width", "110px")
    })
})