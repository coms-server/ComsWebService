import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

let isStatusValid = true;

$(document).ready(function () {
    bindUserManager();
});

function bindUserManager() {
    $("#task").on("click", "#download", download);

    bindModal();
    bindPendingUserApproval();
}

function bindPendingUserApproval() {
    $("#task").on("click", "#pending-table .grant, #pending-table .deny", function () {
        var userIndex = $(this).closest("tr").find(".user-idx-holder").val();
        var approved = $(this).hasClass("grant") ? true : false;
        var ajax = new commonAjax;
        ajax.setURL("/account/approval/" + userIndex);
        ajax.setType("PUT");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify(approved));
        ajax.setOnSuccess(function () {
            new alert("작업 완료.");
            setTimeout(function () {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function () {
            new alert("작업 실패.");
        });
        ajax.ajax();
    });
}

function bindModal() {
    $("#task").on("click", "#main-table .modify", function () {
        const userIdx = $(this).closest("tr").find(".user-idx-holder").val();
        const userName = $(this).closest("tr").find("#userName").html();
        const statusString = $(this).closest("tr").find("#userStatusString").html();
        let prevStatus;
        switch (statusString) {
            case "신입생":
                prevStatus = 1;
                break;
            case "재학생":
                prevStatus = 2;
                break;
            case "휴학생":
                prevStatus = 3;
                break;
            case "졸업생":
                prevStatus = 4;
                break;
        }

        $("#userEditModal #user_idx").val(userIdx).parent().find("p").html(userName);
        $("#userEditModal #prevStatus").val(prevStatus);
        $("#userEditModal select.status").val(prevStatus);
    })

    bindStatus();

    bindChangeStatus();
    bindPasswordReset();
    bindForceWithdraw();
}

function bindStatus() { //FIXME: JSTL로 변경예정
    const status = [{
        val: "1",
        label: "신입생"
    },
        {
            val: "2",
            label: "재학생"
        },
        {
            val: "3",
            label: "휴학생"
        },
        {
            val: "4",
            label: "졸업생"
        }
    ];

    $(".status").each(function () {
        const select = $(this);

        $(status).each(function () {
            const stat = document.createElement("option");
            stat.value = this.val;
            stat.innerHTML = this.label;

            if (stat.value == $("#prevStatus").val()) {
                $(stat).prop("selected", true);
            }

            select.append(stat);
        });
    })

    $("#task").on("change", ".status", function () {
        if (status.findIndex(x => x.val === $(this).val()) >= 0) {
            isStatusValid = true;
        } else {
            isStatusValid = false;
        }
    })
}

function bindChangeStatus() {
    $("#task").on("click", "#modiUser", function () {
        if (!isStatusValid)
            return;

        const target = $("#userEditModal");

        const ajax = new commonAjax();
        ajax.setURL("/user/information");
        ajax.setType("PUT");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify({
            "user_idx": target.find("#user_idx").val(),
            "status": target.find("#modi-status").val()
        }));
        ajax.setOnSuccess(function () {
            new alert("재학상태를 수정했습니다.");
            setTimeout(function () {
                window.location.reload();
            }, 500);
        });
        ajax.setOnError(function () {
            new alert("요청에 실패했습니다.")
        });
        ajax.ajax();
    })
}

// TODO: double query처리
function bindPasswordReset() {
    $("#task").on("click", "#userEditModal #resetPassword", function () {
        const userIndex = $("#userEditModal #user_idx").val();
        const name = $("#userEditModal #user_idx").siblings("p").text().trim();
        const confirmString = name + "의 비밀번호 초기화하고 메일로 전송합니다 이 작업은 되돌릴 수 없습니다";
        if (!confirm(confirmString))
            return;

        var ajax = new commonAjax;
        ajax.setURL("/account/password/reset/" + userIndex);
        ajax.setType("GET");
        ajax.setOnSuccess(function () {
            new alert("비밀번호가 초기화 되었습니다");
        });
        ajax.setOnError(function () {
            new alert("작업 실패.");
        });
        ajax.ajax();
    });
}

function bindForceWithdraw() {
    $("#task").on("click", "#userEditModal #extractUser", function () {
        const userIndex = $("#userEditModal #user_idx").val();
        const name = $("#userEditModal #user_idx").siblings("p").text().trim();
        const confirmString = "정말로 " + name + "을(를) 제명하겠습니까? 이 작업은 되돌릴 수 없습니다";
        if (!confirm(confirmString))
            return;

        const confirmAgainString = name + "의 제명을 진행합니다 이 작업은 되돌릴 수 없습니다 남은 확인:";
        for (let i = 3; i > 0; i--) {
            if (!confirm(confirmAgainString + i))
                return;
        }

        var ajax = new commonAjax;
        ajax.setURL("/account/delete/" + userIndex);
        ajax.setType("DELETE");
        ajax.setOnSuccess(function () {
            new alert("제명되었습니다");
            setTimeout(function () {
                window.location.reload();
            }, 800);
        });
        ajax.setOnError(function () {
            new alert("작업 실패.");
        });
        ajax.ajax();
    });
}

function download() {
    var titles = [];
    var data = [];

    $("#main-table th").each(function () {
        titles.push($(this).text());
    });
    titles.pop();
    $("#main-table-data tr").each(function () {
        $(this).children().each(function () {
            data.push($(this).text());
        });
        data.pop();
    });

    var CSVString = prepCSVRow(titles, titles.length, "");
    CSVString = prepCSVRow(data, titles.length, CSVString);

    var downloadLink = document.createElement("a");
    var blob = new Blob(["\ufeff", CSVString]);
    var url = URL.createObjectURL(blob);
    downloadLink.href = url;
    downloadLink.download = "data.csv";

    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

function prepCSVRow(arr, columnCount, initial) {
    var row = "";
    var delimeter = ",";
    var newLine = "\r\n";

    function splitArray(_arr, _count) {
        var splitted = [];
        var result = [];
        _arr.forEach(function (item, idx) {
            if ((idx + 1) % _count === 0) {
                splitted.push(item);
                result.push(splitted);
                splitted = [];
            } else {
                splitted.push(item);
            }
        });
        return result;
    }

    var plainArr = splitArray(arr, columnCount);
    plainArr.forEach(function (arrItem) {
        arrItem.forEach(function (item, idx) {
            row += "\"" + item + "\"" + ((idx + 1) === arrItem.length ? "" : delimeter);
        });
        row += newLine;
    });
    return initial + row;
}