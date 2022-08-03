import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

var isPositionValid = false;

$(document).ready(function() {

    bindExtract();
    bindSubmit();
    bindModal();
})

function bindSubmit() {
    $("#modiExec").on("click", function() {
        if (!validate()) {
            return;
        }

        var ajax = new commonAjax;
        ajax.setURL("/executive");
        ajax.setType("PUT");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify({
            "user_idx": $("#execEditModal #user_idx").val(),
            "exec_idx": $("#execEditModal #exec_idx").val(),
            "position": $("#execEditModal #position").val(),
            "nth": $("#execEditModal #nth").val(),
            "active": $("#execEditModal #active").is(":checked")
        }));
        ajax.setOnSuccess(function() {
            new alert("수정 완료");
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("수정 실패");
        });
        ajax.ajax();
    })
}

function validate() {
    if (!isPositionValid) {
        new alert("직책을 확인해주세요");
    } else {
        return true;
    }
    return false;
}

function bindExtract() {
    $("#extract").on("click", function() {
        if (!confirm("정말로 제명하시겠습니까?"))
            return;

        var ajax = new commonAjax;
        ajax.setURL("/executive/" + $("#exec_idx").val());
        ajax.setType("DELETE");
        ajax.setOnSuccess(function() {
            new alert("제명 완료");
            $(opener.document).find("#task").load("/admin/executive/list?page=1");
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("제명 실패");
        });
        ajax.ajax();
    })
}

function bindModal() {
    $("#task").on("click", "#active-table .modify, #nonactive-table .modify", function() {
        var execIdx = $(this).closest("tr").find(".exec-idx-holder").val();

        var ajax = new commonAjax;
        ajax.setURL("/executive/" + execIdx);
        ajax.setType("GET");
        ajax.setDataType("json");
        ajax.setOnSuccess(function(response) {
            $("#execEditModal #user_idx").val(response.user_idx).parent().find("p").html(response.name);
            $("#execEditModal #prevPosit").val(response.position);
            $("#execEditModal #execEditModalselect.postion").val(response.postion);
            $("#execEditModal #nth").val(response.nth);
            $("#execEditModal #prevActive").val(response.active);
            $("#execEditModal #exec_idx").val(response.exec_idx);

            bindPreVal();
        });
        ajax.setOnError(function() {})
        ajax.ajax();
    })
}

function bindPreVal() {
    var list = $("#execEditModal .position");
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
            $(item).empty();

            $(positions).each(function() {
                var position = document.createElement("option");
                position.value = this.val;
                position.innerHTML = this.label;

                if (position.value == $("#prevPosit").val()) {
                    $(position).prop("selected", true);
                    isPositionValid = true;
                }

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

    if ($("#execEditModal #prevActive").val() == "true") {
        $("#execEditModal #active").closest("label").addClass('active');
        $("#execEditModal #active").attr("checked", true);
    } else {
        $("#execEditModal #active").closest("label").removeClass('active');
        $("#execEditModal #active").attr("checked", false);
    }
}