import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

var isTitleValid = false;
var isDateValid = false;
var blockDoubleQuery = false;

$(document).ready(function() {
    bindSubmit();
    bindTitleValidator();
    bindDateValidator();
})

function bindSubmit() {
    $("#addEventBtn").click(function() {
        if (!validate() || blockDoubleQuery)
            return;

        var ajax = new commonAjax;
        ajax.setURL("/event");
        ajax.setType("POST");
        ajax.setContentType("application/json")
        ajax.setData(JSON.stringify({
            "title": $("#eTitle").val(),
            "event_desc": $("#eDesc").val(),
            "begin_date": $("#eBeginDate").val().replace("T", " ") + ":00.0",
            "end_date": $("#eEndDate").val().replace("T", " ") + ":00.0",
            "url": $("#url").val(),
            "is_important": $("#eIsImportant").is(":checked")
        }));
        ajax.setOnSuccess(function() {
            new alert("등록 성공");
            blockDoubleQuery = true;
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("등록에 실패했습니다");
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

function bindTitleValidator() {
    $("#eTitle").change(function() {
        if ($(this).val() == "") {
            isTitleValid = false;
        } else {
            isTitleValid = true;
        }
    })
}

function bindDateValidator() {
    $(".dateArea").children().each(function() {
        $(this).change(function() {
            if ($("#eBeginDate").val() > $("#eEndDate").val()) {
                isDateValid = false;
            } else {
                isDateValid = true;
            }
        })
    })
}