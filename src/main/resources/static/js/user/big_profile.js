import {commonAjax} from "../ajax.js";
import {alert} from "../tools.js";

$(document).ready(function() {
    var year = -1;
    $(".eventContainer").each(function() {
        var currentYear = $(this).find(".beginDate").text().substring(0, 4);
        if (currentYear != year) {
            year = currentYear;
            $(this).before("<div class='event box-header'>" + year + "</div>");
        }

        $(this).find(".beginDate").html($(this).find(".beginDate").html().substring(0, 10));
        $(this).find(".endDate").html($(this).find(".endDate").html().substring(0, 12));

    });
})

$("body").on("click", "#editBtn, #abortEdit", function() {
    $(".display").toggleClass("hidden");
    $(".editor").toggleClass("hidden");
    $("#submitEdit").toggleClass("hidden");
    $("#abortEdit").toggleClass("hidden");
})

$("body").on("change", "#profilePic", function() {
    var userIndex = $("#userIndex").val();
    var image = $("#profilePic").get(0).files[0];
    var formData = new FormData();
    formData.append("image", image);

    var ajax = new commonAjax;
    ajax.setType("PUT");
    ajax.setURL("/user/profile-image/" + userIndex);
    ajax.setData(formData);
    ajax.setContentType(false);
    ajax.setProcessData(false);
    ajax.setOnSuccess(function() {
        new alert("프로필 사진이 수정되었습니다");
        setTimeout(function() {
            location.reload();
        }, 500);
    });
    ajax.setOnError(function() {
        new alert("프로필 사진 등록 실패");
    });
    ajax.ajax();
})

$("body").on("click", "#deleteProfilePic", function() {
    var userIndex = $("#userIndex").val();
    var ajax = new commonAjax;
    ajax.setType("DELETE");
    ajax.setURL("/user/profile-image/" + userIndex);
    ajax.setOnSuccess(function() {
        new alert("프로필 사진이 삭제되었습니다");
        setTimeout(function() {
            location.reload();
        }, 500);
    });
    ajax.ajax();
})

$("body").on("click", "#submitEdit", function() {
    var ajax = new commonAjax;
    ajax.setURL("/user/intro");
    ajax.setContentType("application/json");
    ajax.setType("PUT");
    ajax.setData(JSON.stringify({
        "user_idx": $("#userIndex").val(),
        "company": $("#newCompany").val(),
        "website": $("#newWebsite").val(),
        "intro": $("#newIntro").val()
    }));
    ajax.setOnSuccess(function() {
        new alert("정보를 수정 했습니다");
        setTimeout(function() {
            location.reload();
        }, 500);
    });
    ajax.setOnError(function() {
        new alert("요청에 실패했습니다\n 입력 항목을 다시 확인해주세요");
    });
    ajax.ajax();
})