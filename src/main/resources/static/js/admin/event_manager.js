import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

$(document).ready(function() {
    bindEventManager();
});

$("#event-table").on("toggle.bs.table toggle.bs.table", setTableVal());

function bindEventManager() {
    bindDeleteButton();
    bindModal();
}

function setTableVal() {
    $(".date").each(function() {
        var date = $(this).text();
        $(this).text(date.substring(0, date.lastIndexOf(":")));
    });
}

function prettifyString(str) {
    return str.substring(0, str.lastIndexOf(":")).replace(" ", "T");
}

function bindModal() {
    $("#task").on("click", "#event-table .modify", function() {
        var eventIdx = $(this).closest("tr").find(".event-idx-holder").val();

        var ajaxE = new commonAjax;
        ajaxE.setURL("/event/" + eventIdx);
        ajaxE.setType("GET");
        ajaxE.setDataType("json");
        ajaxE.setOnSuccess(function(response) {
            $("#eventEditModal #event_idx").val(response.event_idx);
            $("#eventEditModal #event_title").val(response.title);
            $("#eventEditModal #event_desc").html(response.event_desc); // xss filter 적용해야함
            $("#eventEditModal #event_beginDate").val(prettifyString(response.begin_date));
            $("#eventEditModal #event_endDate").val(prettifyString(response.end_date));
            $("#eventEditModal #event_url").val(response.url);
            $("#eventEditModal #prevIsImportant").val(response.is_important);

            bindPreVal();
        });
        ajaxE.setOnError(function() {
            new alert("이벤트 정보를 가져오는데 실패했습니다");
        })
        ajaxE.ajax();

        var ajaxP = new commonAjax;
        ajaxP.setURL("/event/participant/" + eventIdx);
        ajaxP.setType("GET");
        ajaxP.setDataType("json");
        ajaxP.setOnSuccess(function(response) {
            $(".userNames").each(function() {
                var list = $(this);

                $(list).empty();
                $(response).each(function() {
                    $(list).append("<label>" + this.term + "기 " + this.name + "</label>");
                })
            })
        });
        ajaxP.setOnError(function() {
            new alert("참가자 목록을 가져오는데 실패했습니다");
        })
        ajaxP.ajax();
    })

    $("#task").on("click", "#event-table .participant", function() {
        var eventIdx = $(this).closest("tr").find(".event-idx-holder").val();

        $("#participant-table").bootstrapTable({
            url: '/event/participant/' + eventIdx,
            columns: [{
                field: 'term',
                title: '기수'
            }, {
                field: 'name',
                title: '이름'
            }],
            search: true,
            stickyHeader: true,
            theadClasses: "thead-dark",
            sortable: true,
            pagination: true
        })
    })
}

function bindDeleteButton() {
    $("#task").on("click", "#event-table .delete", function() {
        var confirmString = "이 이벤트를 삭제합니다 이 작업은 되돌릴 수 없습니다";
        if (!confirm(confirmString))
            return;

        var eventIndex = $(this).closest("tr").find(".event-idx-holder").val();
        var ajax = new commonAjax;
        ajax.setURL("/event/" + eventIndex);
        ajax.setType("DELETE");
        ajax.setOnSuccess(function() {
            new alert("삭제되었습니다");
            setTimeout(function() {
                location.reload();
            }, 500);
        });
        ajax.setOnError(function() {
            new alert("작업 실패.");
        });
        ajax.ajax();
    });
}

function bindPreVal() {
    if ($("#eventEditModal #prevIsImportant").val() == "true") {
        $("#eventEditModal #event_isImportant").closest("label").addClass('active');
        $("#eventEditModal #event_isImportant").attr("checked", true);
    } else {
        $("#eventEditModal #event_isImportant").closest("label").removeClass('active');
        $("#eventEditModal #event_isImportant").attr("checked", false);
    }
}