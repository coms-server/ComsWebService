import {alert} from "../tools.js";
import {commonAjax} from "../ajax.js";

let isStatusValid = true;
let isPhoneNumValid = true;
let isPwValid = false;
let isPwChecked = false;

let blockDoubleQuery = false;

$(document).ready(function () {

    bindFormInput();
})


$("#task").on("click", ".leave", function () {
    if (confirm("정말 탈퇴하시겠습니까? (주의사항을 확인했습니다)")) {
        var userIndex = $(this).siblings("#user_idx").val();
        var ajax = new commonAjax;
        ajax.setURL("/account/delete/" + userIndex);
        ajax.setType("DELETE");
        ajax.setOnSuccess(function () {
            new alert("회원탈퇴 처리되었습니다");
            var ajax = new commonAjax;
            ajax.setURL("/logout");
            ajax.setType("POST");
            ajax.setOnSuccess(function () {
                location.href = "/index";
            })
            ajax.ajax();
        });
        ajax.setOnError(function (response) {
            $("html").html(response);
        });

        ajax.ajax();
    }
});

$("#task").on("click", ".infoModiBtn", function () {
    if (!validateInfo() || blockDoubleQuery)
        return;

    //값 치환
    $("#modi-email_addr").val($("#email_addr-id").val() + "@" + $("#email_addr-domain").val());
    $("#modi-home_addr").val($("#modi-post-num").val() + "@" + $("#modi-address1").val() +
        "@" + $("#modi-address2").val() + "@" + $("#modi-address3").val());

    var ajax = new commonAjax;
    ajax.setURL("/user/information");
    ajax.setType("PUT");
    ajax.setContentType("application/json");
    ajax.setData(JSON.stringify({
        "user_idx": $("#user_idx").val(),
        "status": $("#modi-status").val(),
        "email_addr": $("#modi-email_addr").val(),
        "email_send": $("#modi-email-msg").is(":checked"),
        "phone_num": $("#modi-phone_num").val(),
        "sms_send": $("#modi-phone-msg").is(":checked"),
        "home_addr": $("#modi-home_addr").val()
    }));
    ajax.setOnSuccess(function () {
        new alert("정보를 수정 했습니다");
        blockDoubleQuery = true;
        setTimeout(function () {
            window.location.reload();
        }, 500);
    });
    ajax.setOnError(function () {
        new alert("요청에 실패했습니다.\n 입력 항목을 다시 확인해주세요.");
    });
    ajax.ajax();
});


$("#task").on("click", ".pwModiBtn", function () {
    if (!validatePw() || blockDoubleQuery)
        return;

    var userIndex = $("#user_idx").val();
    var password = $("#modi-pw2").val();

    var ajax = new commonAjax;
    ajax.setURL("/account/password/update/" + userIndex);
    ajax.setType("PUT");
    ajax.setContentType("application/json");
    ajax.setData(JSON.stringify({
        "password": password
    }));
    ajax.setOnSuccess(function () {
        blockDoubleQuery = true;
        new alert("비밀번호 수정을 요청했습니다");
        setTimeout(function () {
            var redirect = new commonAjax;
            redirect.setURL("/logout");
            redirect.setType("POST");
            redirect.setOnSuccess(function () {
                window.location.href = "/login";
            })
            redirect.ajax();
        }, 500);
    });
    ajax.setOnError(function () {
        new alert("비밀번호를 재확인 해주세요.");
    });
    ajax.ajax();
})

function validateInfo() {
    if (!isStatusValid) {
        new alert("재학상태를 확인해주세요");
    } else if (!isPhoneNumValid) {
        new alert("전화번호를 확인해주세요");
    } else {
        return true;
    }
    return false;
}

function validatePw() {
    if (!isPwValid || !isPwChecked) {
        new alert("비밀번호를 확인해주세요");
    } else {
        return true;
    }
    return false;
}

function bindFormInput() {
    if ($("#prevSmsSend").val() == "true") {
        $("#modi-phone-msg").attr("checked", true);
    }

    if ($("#prevEmailSend").val() == "true") {
        $("#modi-email-msg").attr("checked", true);
    }

    var tokenAddr;
    if ($("#prevHomeAddr").val() != null) {
        tokenAddr = $("#prevHomeAddr").val().split("@");
        $("#modi-post-num").val(tokenAddr[0]);
        $("#modi-address1").val(tokenAddr[1]);
        $("#modi-address2").val(tokenAddr[2]);
        $("#modi-address3").val(tokenAddr[3]);
    }

    bindStatus();
    bindPhoneNum();
    bindEmail();
    bindAddress();
    bindPW();
}

function bindStatus() { //FIXME: JSTL로 변경예정
    var status = [{
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
        var select = $(this);

        $(status).each(function () {
            var stat = document.createElement("option");
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

function bindPhoneNum() {
    $("#task").on("keyup", "#modi-phone_num", function () {
        $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));

        if ($(this).val() != "") {
            isPhoneNumValid = true;
        }
    });
}

function bindEmail() { // 선택창으로 이메일 도메인을 채울 수 있도록 함
    var domains = [{
        val: "",
        label: "직접입력"
    },
        {
            val: "naver.com",
            label: "naver.com"
        },
        {
            val: "gmail.com",
            label: "gmail.com"
        },
        {
            val: "daum.net",
            label: "daum.net"
        },
        {
            val: "kw.ac.kr",
            label: "kw.ac.kr"
        }
    ];

    var tokenEmail;
    if ($("#prevEmailId").val() != null) {
        tokenEmail = $("#prevEmailId").val().split("@");
        $("#email_addr-id").val(tokenEmail[0]);
    }

    $(".email-domain").each(function () {
        var select = $(this);

        $(domains).each(function (indexD, itemD) {
            var v = document.createElement("option");
            v.value = itemD.val;
            v.innerHTML = itemD.label;

            if (v.innerHTML == tokenEmail[1]) {
                $(v).prop("selected", true);
                $("#email_addr-domain").val(v.value);
            }

            select.append(v);
        });
    })

    $("#task").on("change", ".email-domain", function () {
        var target = $("#email_addr-domain");
        $(target).val($(this).val());

        if ($(this).val() != "") {
            $(target).attr("readonly", "true");
        } else {
            $(target).removeAttr("readonly");
        }
    });
}

function bindAddress() {
    $("#task").on("click", ".btn-address", function () {
        new daum.Postcode({
            oncomplete: function (data) {
                var addr = '';
                var extraAddr = '';

                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

                if (data.userSelectedType === 'R') {
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    $("#modi-address3").val(extraAddr);

                } else {
                    $("#modi-address3").val("");
                }
                $("#modi-post-num").val(data.zonecode);
                $("#modi-address1").val(addr);
                $("#modi-address2").focus();
            }
        }).open();
    });
}

function bindPW() { // 비밀번호를 입력하는 즉시 확인함(정규식, 재확인)
    $("#task").on("keyup", ".password", function () {
        var regex = /(?=.*\d{1,40})(?=.*[a-zA-Z]{1,40}).{8,40}$/g;

        var password = $(this).parent().find("#modi-pw1").val();
        var rePassword = $(this).parent().find("#modi-pw2").val();
        var desc1 = $(this).parent().find("#pw-checker");
        var desc2 = $(this).parent().find("#pw-rechecker");

        if (regex.test(password)) {
            isPwValid = true;
            desc1.html("사용가능한 비밀번호입니다");
        } else {
            isPwValid = false;
            desc1.html("8~40자 특수문자( ! $ % * @ ^ ` ~ ), 영문 대 소문자, 숫자를 사용하세요.");
        }

        if (password != "" && rePassword != "") {
            if (password == rePassword) {
                isPwChecked = true;
                desc2.html("패스워드가 일치합니다");
            } else {
                isPwChecked = false;
                desc2.html("패스워드가 일치하지 않습니다");
            }
        } else {
            if (password == "") {
                desc1.html("");
            }
            if (rePassword == "") {
                desc2.html("");
            }
        }
    });
}