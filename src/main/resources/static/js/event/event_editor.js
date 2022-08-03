import {alert} from "../tools.js"
import {commonAjax} from "../ajax.js";

var isTitleValid = true;
var isDateValid = true;
var blockDoubleQuery = false;

$(document).ready(function() {
    // Init date string
    $("#event_beginDate").val($("#event_beginDate").val().replace(" ", "T").substring(0, 16));
    $("#event_endDate").val($("#event_endDate").val().replace(" ", "T").substring(0, 16));

    // Bind validator
    $("#event_title").change(function() {
        isTitleValid = $(this).val() !== "";
    })

    $(".dateArea").children().each(function() {
        $(this).change(function() {
            isDateValid = $("#event_beginDate").val() <= $("#event_endDate").val();
        })
    })

    $("#closeEventEdit").click(function() {
        $("#eventEditModal #event_isImportant").closest("label").button('dispose');
    })

    bindSubmit();
})

function bindSubmit() {
    $("#saveBtn").click(function() {
        if (!validate() || blockDoubleQuery) {
            return;
        }

        var event_idx = $("#event_idx").val();
        var ajax = new commonAjax;
        ajax.setURL("/event");
        ajax.setType("PUT");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify({
            "event_idx": event_idx,
            "title": $("#event_title").val(),
            "event_desc": $("#event_desc").val(),
            "begin_date": $("#event_beginDate").val().replace("T", " ") + ":00.0",
            "end_date": $("#event_endDate").val().replace("T", " ") + ":00.0",
            "url": $("#event_url").val(),
            "is_important": $("#event_isImportant").is(":checked")
        }));
        ajax.setOnSuccess(function() {
            var list = $($(".userList").get(0)).val();
            var participantList = [];
            if (list !== undefined && list.length !== 0)
                participantList = JSON.parse(list);
            var ajax = new commonAjax;
            ajax.setURL("/event/participant/" + event_idx);
            ajax.setType("PUT");
            ajax.setContentType("application/json");
            ajax.setData(
                JSON.stringify(participantList)
            );
            ajax.setOnSuccess(function() {
                blockDoubleQuery = true;
                new alert("이벤트 수정 성공");
                setTimeout(function() {
                    location.reload();
                }, 500);
            });
            ajax.setOnError(function() {
                new alert("이벤트 수정 성공, 참여자 업데이트 실패.");
            });
            ajax.ajax();
        });
        ajax.setOnError(function() {
            new alert("이벤트 수정 실패.");
        });
        ajax.ajax();
    })
}

function validate() {
    if (!isTitleValid) {
        new alert("제목을 확인해주세요");
        return false;
    }
    if (!isDateValid) {
        new alert("일정을 확인해주세요");
        return false;
    }
    return true;
}