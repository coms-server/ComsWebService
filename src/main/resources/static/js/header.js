import {commonAjax} from "./ajax.js";

$(document).ready(function() {
    $(".sidebar-toggle").click(function() {
        $("#sidebar").toggleClass("open");
    });

    $(".noti-toggle").click(function() {
        $(this).toggleClass("open");
        $("#notiContainer").toggleClass("open");
    });

    $(".nav-board > div").click(function() {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    $(".profile").click(function() {
        window.location.href = "/user/profile/" + $("#user_idx").val();
    });

    $(".logout").click(function() {
        $(this).children().submit();
    });

    $("#linkList").on("mouseover", ".rootBoard", function() {
        $(this).find(".subBoardContainer").delay(200).addClass("open");
    })
    $("#linkList").on("mouseout", ".rootBoard", function() {
        $(this).find(".subBoardContainer").removeClass("open");
    })

    setInterval(function() {
        $("#notiContainer .notiList").empty();

        if ($("#user_idx").val() != typeof undefined) {
            updateNotificationList();
        }
    }, 20 * 1000);
    setInterval(updateUnreadCount, 20 * 1000);

    if ($("#user_idx").val() != typeof undefined) {
        updateNotificationList();
    }
    updateUnreadCount();
})

function updateUnreadCount() {
    var userIndex = $("#user_idx").val();

    var ajax = new commonAjax;
    ajax.setURL("/notification/" + userIndex);
    ajax.setDataType("JSON");
    ajax.setType("GET");
    ajax.setOnSuccess(function(response) {
        var unread = 0;
        $(response).each(function() {
            if (!this.is_read)
                unread++;
        });

        if (unread == 0) {
            $("#notiCount").removeClass("open");
        } else {
            $("#notiCount").text(unread).addClass("open");
        }
    });
    ajax.setOnError(function(response) {
        $("#notiCount").hide();
    });
    ajax.ajax();
}

function updateNotificationList() {
    var container = $("#notiContainer");
    var userIndex = $("#user_idx").val();

    var ajax = new commonAjax;
    ajax.setURL("/notification/" + userIndex);
    ajax.setDataType("JSON");
    ajax.setType("GET");
    ajax.setOnSuccess(function(response) {
        $(response).each(function(index, item) {
            var noti = document.createElement("div");
            var title = document.createElement("div");
            var date = document.createElement("div");
            var link = document.createElement("div");
            $(title).addClass("title").addClass("noselect").html(this.title);
            $(date).addClass("date").html(this.date_string);
            $(link).addClass("link").append(title).append(date);
            if (this.is_read) {
                $(link).addClass("read");
            }

            var deleteBtn = document.createElement("i");
            $(deleteBtn).addClass("delete").addClass("fas").addClass("fa-times");

            $(noti).addClass("noti").append(link).append(deleteBtn);
            container.find(".notiList").append(noti);

            $(link).click(function() {
                var ajax = new commonAjax;
                ajax.setURL("/notification/" + item.noti_idx);
                ajax.setType("PUT");
                ajax.setOnSuccess(function() {
                    if (item.url) {
                        window.open(item.url, "_blank");
                    }
                    $(link).addClass("read");
                    updateUnreadCount();
                });
                ajax.ajax();
            })

            $(deleteBtn).click(function() {
                var btn = $(this);
                var ajax = new commonAjax;
                ajax.setURL("/notification/" + item.noti_idx);
                ajax.setType("DELETE");
                ajax.setOnSuccess(function() {
                    $(btn).closest(".noti").remove();

                    if ($(".noti").length == 0) {
                        var noti = document.createElement("div");
                        var link = document.createElement("div");
                        var title = document.createElement("div");
                        $(title).addClass("title").addClass("noselect").html("알림 없음");
                        $(link).addClass("link").append(title);
                        $(noti).addClass("noti").append(link);
                        container.find(".notiList").append(noti);
                    }
                    updateUnreadCount();
                });
                ajax.ajax();
            })
        })

        if (container.find(".noti").length == 0) {
            var noti = document.createElement("div");
            var link = document.createElement("div");
            var title = document.createElement("div");
            $(title).addClass("title").addClass("noselect").html("알림 없음");
            $(link).addClass("dummy").append(title);
            $(noti).addClass("noti").append(link);
            container.find(".notiList").append(noti);
        }
    });
    ajax.ajax();
}