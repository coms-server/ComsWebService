import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

var isPositionValid = true;
$(document).ready(function() {

    bindSubmit();
    bindPosition();
    bindModal();
})

function bindSubmit() {
    $("#addExecBtn").click(function() {
        if (!validate()) {
            return;
        }

        var temp = $("#execAddModal .userList").get(0);

        var ajax = new commonAjax;
        ajax.setURL("/executive");
        ajax.setType("POST");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify({
            "user_idx": JSON.parse($(temp).val())[0],
            "position": $("#execAddModal #position").val(),
            "nth": $("#execAddModal #nth").val(),
            "active": $("#execAddModal #active").is(":checked")
        }));
        ajax.setOnSuccess(function() {
            new alert("추가 완료");
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("값을 점검해 주세요");
        });
        ajax.ajax();
    })
}

function validate() {
    if (!isPositionValid) {
        new alert("직책을 확인해주세요");
    } else {
        var temp = $("#execAddModal .userList").get(0);
        var list = $(temp).val();
        if (list == undefined || list.length == 0) {
            new alert("대상을 선택해주세요");
            return false;
        } else if (JSON.parse(list).length > 1) {
            new alert("대상은 한명만 지정할 수 있습니다");
        } else {
            return true;
        }
    }
    return false;
}

function bindPosition() {
    var list = $("#execAddModal .position");
    var positions = [];

    var ajax = new commonAjax;
    ajax.setURL("/user/get-position-list");
    ajax.setDataType("JSON");
    ajax.setType("GET");
    ajax.setOnSuccess(function(response) {
        $(response).each(function(index, item) {
            var position = new Object();
            position.val = item.position_idx;
            position.label = item.position;

            positions.push(position);
        });

        list.each(function(index, item) {
            $(positions).each(function() {
                var position = document.createElement("option");
                position.value = this.val;
                position.innerHTML = this.label;

                $(item).append(position);
            });

            $(this).on("change", function() {
                if (positions.findIndex(x => x.val == $(this).val()) >= 0) {
                    isPositionValid = true;
                } else {
                    isPositionValid = false;
                }
            });
        });
    });
    ajax.setOnError(function() {
        new alert("전공목록을 가져오는데 실패했습니다");
    })
    ajax.ajax();
}

function bindModal() {
    $("#execAddModal").on("hidden.bs.modal", function() {
        $(this).find(".userNames").empty();
        $(this).find(".userList").val("");
    });
}