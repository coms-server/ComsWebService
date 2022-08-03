export class alert {
    /**
     * 
     * @param {string} content 경고창 내용
     * @param {string} title 경고창 제목
     * @param {string} bgc 배경 RGB String (alretBGC class)
     * @param {boolean} isClosedOnBack 바깥쪽을 클릭해도 닫을 수 있음
     */
    constructor(content = '', title = '', bgc = '#FFFFFF', isClosedOnBack = true) {
        alert.modal = null;
        alert.bgalt = null;

        if (typeof content === 'string' || content instanceof String)
            content = content.split(`\n`).join(`<br>`);
        if (alert.modal) {
            alert.modal.remove();
        }
        if (title == '') title = "알림";

        alert.modal = document.createElement("div");
        $(alert.modal).addClass("modal").attr("tabindex", "-1").attr("role", "dialog");
        alert.modalDialog = document.createElement("div");
        $(alert.modalDialog).addClass("modal-dialog").attr("role", "document")
        alert.modalContent = document.createElement("div");
        $(alert.modalContent).addClass("modal-content");
        alert.modalHeader = document.createElement("div");
        $(alert.modalHeader).addClass("modal-header");
        alert.modalBody = document.createElement("div");
        $(alert.modalBody).addClass("modal-body");
        alert.modalFooter = document.createElement("div");
        $(alert.modalFooter).addClass("modal-footer");

        let h = document.createElement("h5");
        $(h).addClass("modal-title").html(title);

        let p = document.createElement("p");
        $(p).html(content);

        let btn = document.createElement("button");
        $(btn).attr("type", "button").attr("data-dismiss", "modal").addClass("btn").addClass("btn-secondary").html("확인");

        $(alert.modalHeader).append(h);
        $(alert.modalBody).append(p);
        $(alert.modalFooter).append(btn);
        $(alert.modalContent).append(alert.modalHeader).append(alert.modalBody).append(alert.modalFooter);
        $(alert.modalDialog).append(alert.modalContent);
        $(alert.modal).append(alert.modalDialog);

        $(btn).on("click", function() {
            $(alert.modal).modal("hide");
            $(alert.modal).remove();
        })

        $("body").append(alert.modal);
        $(alert.modal).modal("show");
    }
}

export function bindSelectBar(target, callback) {
    $(".selectContent").first().addClass("selected").siblings().removeClass("selected");
    $("#task").load($(".selectContent").first().children(".link").val());

    $("#selectBar").on("click", ".selectContent", function() {
        $(this).addClass("selected").siblings().removeClass("selected");
        var link = $(this).children(".link").val();
        if (link)
            $(target).load(link, callback);
    });
}

export function bindPagination(target) {
    $(target).on("click", ".page-idx > a", function() {
        var listURL = $(this).siblings(".listURL").val();

        $(target).load(listURL + "?page=" + $(this).children("input").val());
    })
}

export function getContextPath() {
    var hostIndex = location.href.indexOf(location.host) + location.host.length;
    return location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
}


export function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}