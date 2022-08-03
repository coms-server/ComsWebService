import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

var quill;
var fileIndex = 0;
var totalFileSize = 0;
var fileList = new Array();
var fileSizeList = new Array();
const MAX_SIZE = 20971520;

$(document).ready(function() {
    initEditor();
    $("#attachment").change(onFileSelectEvent);
    $(".submit").click(onSubmitClick);
    $("#abort").click(function() {
        window.location.href = "/board/" + $("#INFO > #context").val() + "/";
    });
});

function initEditor() {

    $("#isNotice").prop("checked", $("#INFO > #is_notice").val() === "true");

    quill = new Quill("#quill-editor", {
        modules: {
            syntax: true,
            toolbar: "#quill-toolbar",
            imageResize: {
                modules: ["Resize", "DisplaySize", "Toolbar"]
            }
        },
        theme: "snow"
    });

    var ops = $("#quill-editor").text();
    if (ops.length != 0) {
        var ori = JSON.parse(ops);
        quill.setContents(ori);
    }
    $("#quill-editor").css("visibility", "visible");
    $(".ql-editor").attr("tabindex", "2");

    // answer mode
    if ($("#INFO > #is_subarticle").val() === "true") {
        var oriQuill = new Quill("#quill-reader", {
            modules: {
                syntax: true,
                toolbar: false
            },
            readOnly: true,
            theme: "snow"
        });

        var content = JSON.parse($("#quill-reader").text());
        oriQuill.setContents(content);
        $("#quill-reader").css("visibility", "visible");
    }
}

function onFileSelectEvent(event) {
    event.preventDefault();
    var files = this.files;
    if (files != null) {
        if (files.length < 1) {
            return;
        }
        addToFileList(files);
    }

    if ($(".attachment-container").find(".attachment").length == 0) {
        $(".attachment-container").toggleClass("hidden");
    }
}

function addToFileList(files) {
    if (files != null) {
        for (var i = 0; i < files.length; i++) {
            var fileName = files[i].name;
            var fileSize = files[i].size;

            if (totalFileSize + fileSize > MAX_SIZE) {
                new alert("첨부파일 용량은 20MB를 초과할 수 없습니다");
                break;
            } else {
                totalFileSize += fileSize;
                fileList[fileIndex] = files[i];
                fileSizeList[fileIndex] = fileSize;
                appendHTMLElement(fileIndex, fileName, fileSize);
                fileIndex++;
            }
        }
    } else {
        new alert("파일첨부 에러.");
    }
}

function appendHTMLElement(fileIndex, fileName, fileSize) {
    var textNode = fileName + "(" + fileSizeUnitSelector(fileSize) + ")";
    var $deleteButton = $("<input>", {
        type: "button",
        value: "삭제",
        style: "margin-right: 10px"
    });
    $deleteButton.click(function() {
        deleteFile(fileIndex);
    });

    var $container = $("<div>", {
        id: "new-attachment-" + fileIndex
    });
    $container.append($deleteButton);
    $container.append(textNode);
    $container.addCaddClass("attachment").addClass("noselect");

    $("#new-attachment-list").append($container);
}

function fileSizeUnitSelector(size) {
    var unit;
    if (size > 1024) {
        if (size > 1048576)
            unit = Math.floor(size / 1048576) + "MB";
        else
            unit = Math.floor(size / 1024) + "KB";
    } else {
        unit = size + "B";
    }
    return unit;
}

function deleteFile(fileIndex) {
    totalFileSize -= fileSizeList[fileIndex];
    delete fileList[fileIndex];
    delete fileSizeList[fileIndex];

    $("#new-attachment-" + fileIndex).remove();
}

function onSubmitClick() {
    var title = $("#title > input").val();
    if (title == undefined || title.length == 0) {
        new alert("제목을 입력해주세요.");
        return false;
    }
    if (quill.getLength() == 1) {
        new alert("내용을 입력해주세요.");
        return false;
    }

    beginPosting();
}

function beginPosting() {
    new alert("업로드 중..");
    var context = $("#INFO > #context").val();
    uploadArticle().then(uploadAttachment)
        .done(function(response) {
            window.location.href = "/board/" + context + "/reader/" + response;
        })
        .fail(function() {
            window.alert("게시글 업로드에 실패했습니다");
        });
}

function uploadArticle() {
    var deffered = $.Deferred();

    var title = $("#title > input").val();
    var is_notice = $("#isNotice").is(":checked");
    var content = JSON.stringify(quill.getContents())
    var article_idx = $("#INFO > #article_idx").val();
    var board_idx = $("#INFO > #board_idx").val();
    var parent_idx = $("#INFO > #parent_idx").val();
    var is_subarticle = $("#INFO > #is_subarticle").val();

    var ajax = new commonAjax;
    var data = {
        "article_idx": article_idx,
        "parent_idx": parent_idx,
        "board_idx": board_idx,
        "title": title,
        "content": content,
        "is_subarticle": is_subarticle,
        "is_notice": is_notice
    };
    if (article_idx.length == 0) {
        ajax.setType("POST");
        ajax.setData(data);
    } else {
        ajax.setType("PUT");
        ajax.setContentType("application/json");
        ajax.setData(JSON.stringify(data));
    }
    ajax.setURL("/article");
    ajax.setOnSuccess(function(response) {
        deffered.resolve(response);
    });
    ajax.setOnError(function() {
        deffered.reject();
    });
    ajax.ajax();

    return deffered.promise();
}

function uploadAttachment(article_idx) {
    var deffered = $.Deferred();

    var fileIndexList = Object.keys(fileList);

    if (fileIndexList.length == 0) {
        deffered.resolve(article_idx);
    } else {
        var formData = new FormData();
        for (var i = 0; i < fileIndexList.length; i++) {
            formData.append("attachment", fileList[fileIndexList[i]]);
        }
        formData.append("article_idx", article_idx);

        var ajax = new commonAjax;
        ajax.setType("post");
        ajax.setURL("/attachment/upload");
        ajax.setData(formData);
        ajax.setContentType(false);
        ajax.setProcessData(false);
        ajax.setOnSuccess(function() {
            deffered.resolve(article_idx);
        });
        ajax.setOnError(function() {
            deffered.reject();
        });
        ajax.ajax();
    }

    return deffered.promise();
}