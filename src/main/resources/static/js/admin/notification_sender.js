import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

$(document).ready(function() {
    bindSendAll();
    bindSubmit();
    bindReset();
});

function bindSendAll() {
    $("#notiIsGlobal").on("click", function() {
        $("#selectUser").toggleClass("hidden");
        $("#allLabel").toggleClass("hidden");
        $("#userNames").toggleClass("hidden");
    })
}

function bindSubmit() {
    $("#sendNotiBtn").on("click", function() {
        var title = $("#notiTitle").val();
        if (title == undefined || title.length == 0) {
            new alert("제목을 입력해주세요.");
            return;
        }
        var url = $("#notiUrl").val();
        var user_idx;
        if ($("#notiIsGlobal").is(":checked")) {
            user_idx = ["-1"];
        } else {
            var list = $("#notification .userList").val();
            if (list == undefined || list.length == 0) {
                new alert("대상을 선택해주세요.");
                return;
            }
            user_idx = JSON.parse(list);
            if (user_idx.length == 0) {
                new alert("대상을 선택해주세요.");
                return;
            }
        }

        var data = {
            "user_idx": user_idx,
            "title": title,
            "url": url
        }

        var ajax = new commonAjax;
        ajax.setURL("/notification");
        ajax.setType("POST");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify(data));
        ajax.setOnSuccess(function() {
            new alert("발송 성공.");
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("발송 실패.");
        });
        ajax.ajax();
    })
}

function bindReset() {
    $("#adminTab").on("show.bs.tab", function() {
        $("#notification").find(".userNames").empty();
        $("#notification").find(".userList").val("");
    })
}