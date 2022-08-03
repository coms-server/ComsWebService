export function commonAjax() {
    this.url = "";
    this.type = "";
    this.data = null;
    this.dataType = "";
    this.contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    this.processData = true;
    this.onSuccess = null;
    this.onError = null;

    this.setType = function setType(type) {
        this.type = type;
    }

    this.setURL = function setURL(url) {
        this.url = url;
    }

    this.setData = function setData(data) {
        this.data = data;
    }

    this.setDataType = function setDataType(dataType) {
        this.dataType = dataType;
    }

    this.setContentType = function setContentType(contentType) {
        this.contentType = contentType;
    }

    this.setProcessData = function setProcessData(processData) {
        this.processData = processData;
    }

    this.setOnSuccess = function setOnSuccess(onSuccess) {
        this.onSuccess = onSuccess;
    }

    this.setOnError = function setOnError(onError) {
        this.onError = onError;
    }

    this.setupWithForm = function setupWithForm(form) {
        var obj = $(form);
        this.type = obj.attr("method");
        this.url = obj.attr("action");
        this.data = obj.serialize();
    }

    this.ajax = function ajax() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        return $.ajax({
            type: this.type,
            url: this.url,
            data: this.data,
            dataType: this.dataType,
            contentType: this.contentType,
            processData: this.processData,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: this.onSuccess,
            error: this.onError
        });
    }
};