import {alert} from "./tools.js";
import {commonAjax} from "./ajax.js";

var isIdValid = false;
var isPwValid = false;
var isPwChecked = false;
var isNameValid = false;
var isStatusValid = false;
var isDateValid = true;
var isTermValid = false;
var isPhoneNumValid = false;

$(document).ready(function() {
    bindRequireItem();
    bindImmutableItem();
    bindRuleCheck();

    bindFormInput();
    bindSubmit();

    $("#homeBtn").click(function() {
        var real = confirm("회원가입을 그만둘까요? (입력한 데이터는 저장되지 않습니다)");
        if (real) {
            location.href = '/index';
        }
    });

});

function bindSubmit() {
    $("#register-form").submit(function(event) {
        event.preventDefault();

        if (validate()) {
            var ajax = new commonAjax;
            var data = {
                "userAuth": {
                    "alias": $("#sign-id").val(),
                    "password": $("#sign-pw1").val(),
                    "name": $("#sign-name").val()
                },
                "userInfo": {
                    "major": $("#major option:selected").val(),
                    "status": $("#status").val(),
                    "birth": $("#birth").val(),
                    "term": $("#term").val(),
                    "phone_num": $("#phone_num").val(),
                    "sms_send": $("#sign-phone-msg").is(":checked"),
                    "email_addr": $("#email_addr-id").val() + "@" + $("#email_addr-domain").val(),
                    "email_send": $("#sign-email-msg").is(":checked"),
                    "home_addr": $("#sign-post-num").val() + "@" + $("#sign-address1").val() +
                        "@" + $("#sign-address2").val() + "@" + $("#sign-address3").val()
                }
            };
            ajax.setType("post");
            ajax.setContentType("application/json");
            ajax.setData(JSON.stringify(data));
            ajax.setURL("/account/register");

            ajax.setOnSuccess(function() {
                new alert("회원가입을 요청했습니다");
                setTimeout(function() {
                    window.location.href = "/index";
                }, 500);

            });
            ajax.setOnError(function() {
                new alert("등록에 실패했습니다\n 입력항목을 다시 확인해주세요.");
            });
            ajax.ajax();
        } else {
            return;
        }
    });
}

function validate() {
    if (!isIdValid) {
        new alert("아이디를 확인해주세요");
    } else if (!isPwValid || !isPwChecked) {
        new alert("비밀번호를 확인해주세요");
    } else if (!isNameValid) {
        new alert("이름을 확인해주세요");
    } else if (!isStatusValid) {
        new alert("재학상태를 확인해주세요");
    } else if (!isDateValid) {
        new alert("생년월일을 확인해주세요");
    } else if (!isTermValid) {
        new alert("기수를 확인해주세요");
    } else if (!isPhoneNumValid) {
        new alert("전화번호를 확인해주세요");
    } else {
        return true;
    }
    return false;
}

function bindRequireItem() { // "필" 태그를 띄워줌
    var list = $("[req]");
    list.html("필");
    list.click(function() {
        new alert("해당 항목은 필수 항목입니다", "필수");
    });
}

function bindImmutableItem() { // "불" 태그를 띄워줌
    var item = $("[immutable]");
    item.html("불");
    item.click(function() {
        new alert("해당 항목은 가입 후 수정이 불가능한 항목입니다", "변경불가");
    });
}

function bindRuleCheck() { // 약관에 모두 동의한 경우에만 정보입력창을 보여줌
    var list = $(".rule-desc");
    var hiddenContent = $(".mainSign");
    var noti = $(".noti");
    var checkbox = $(".checkbox input[command='rule-check']");

    hiddenContent.hide();

    checkbox.change(function() {
        if (!ruleCheck(list)) {
            hiddenContent.hide(300);
            noti.show(300);
        } else {
            hiddenContent.show(300);
            noti.hide(300);
        }
    });
}

function ruleCheck(list) { // 약관에 모두 둥의했는지 확인함
    var is_allCheck = true;

    list.each(function() {
        if (!$(this).find("input[command='rule-check']").is(":checked")) {
            is_allCheck = false;
            $(this).find(".rule-content").show(300);
        } else {
            $(this).find(".rule-content").hide(300);
        }
    });

    return is_allCheck;
}

function bindFormInput() {
    bindID();
    bindPW();
    bindTerm();
    bindEmail();
    bindName();
    bindStatus();
    bindBirth();
    bindPhoneNum();
    bindAddress();
}

function bindID() {
    $("input[name='alias']").keyup(function() { // html input이 변경되는 경우 아이디 검증을 요구함
        var desc = $("#id-status");

        isIdValid = false;
        desc.html("아이디 확인 버튼을 눌러주세요.");
    });

    $("#id-checker").click(function() { // 아이디 확인(정규식, 중복확인)
        var regex = /(?=^[\da-zA-Z])(?=.*\d{0,16})(?=.*[_-]{0,16})(?=.*[a-zA-Z]{0,16}).{4,16}$/g;
        var id = $("input[name='alias']");
        var desc = $("#id-status");

        function yes() { // 색상추가 해야함 #F56C42
            isIdValid = true;
            desc.html("사용할 수 있습니다");
        }

        function no(msg) { // 색상추가 해야함 #4298F5
            isIdValid = false;
            desc.html(msg);
        }

        if (regex.test(id.val())) {
            desc.html(""); // desc clear

            var ajax = new commonAjax;
            ajax.setURL("/account/is-alias-exist/" + id.val());
            ajax.setType("GET");
            ajax.setOnSuccess(function(response) {
                if (response) {
                    no("해당 ID는 이미 사용중입니다");
                } else {
                    yes();
                }
            });
            ajax.ajax();
        } else {
            no("4~16자의 영문 소문자, 숫자와 특수기호( _ - )만 사용 가능하며, 첫글자에는 특수기호를 사용할 수 없습니다");
        }
    });
}

function bindPW() { // 비밀번호를 입력하는 즉시 확인함(정규식, 재확인)
    var list = $("input[type='password']");

    list.each(function(index, item) {
        $(item).keyup(function() {
            var regex = /(?=.*\d{1,40})(?=.*[a-zA-Z]{1,40}).{8,40}$/g;

            var password = $(this).parent().find("#sign-pw1").val();
            var rePassword = $(this).parent().find("#sign-pw2").val();
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
                desc1.html("");
                desc2.html("");
            }
        });
    });
}

function bindName() {
    var list = $("input[name='name']");

    list.each(function(index, item) {
        $(item).keyup(function() {
            var regex = /[가-힣]{2,10}$/g;
            var desc = $("#" + $(item).attr("desc"));

            if (regex.test($(item).val())) {
                isNameValid = true;
                desc.html("");
            } else {
                isNameValid = false;
                desc.html("형식에 맞지 않는 이름입니다")
            }
        });
    });
}

function bindStatus() {
    var target = $("#status");
    var list = $("select[name='statusText']");
    var status = [
        { val: "1", label: "신입생" },
        { val: "2", label: "재학생" },
        { val: "3", label: "휴학생" },
        { val: "4", label: "졸업생" }
    ];

    list.each(function(index, item) {
        $(status).each(function() {
            var stat = document.createElement("option");
            stat.value = this.val;
            stat.innerHTML = this.label;

            $(item).append(stat);
        });

        $(this).change(function() {
            target.val($(this).val());

            if (status.findIndex(x => x.val === $(this).val() >= 0)) {
                isStatusValid = true;
            } else {
                isStatusValid = false;
            }
        });
    });
}

function bindBirth() {
    isDateValid = true;

    $("input[name='birth']").val(new Date().toISOString().slice(0, 10));

    $("input[name='birth']").change(function() {
        var regex = /^(19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/g;
        var regexY = /([0-9]{4})/g;

        if (regex.test($(this).val()) && $(this).val().match(regexY) < new Date().getFullYear()) {
            isDateValid = true;
        } else if ($(this).val() == null) {
            isDateValid = true;
        } else {
            isDateValid = false;
        }
    });
}

function bindTerm() { // 현재 신입생의 기수를 자동으로 채워주고 띄워줌
    var term = $("input[name='term']");
    var termMin = 1;
    var termMax = new Date().getFullYear() - 1966;
    var desc = term.next().next();

    term.attr("value", termMax);
    term.attr("min", termMin);
    term.attr("max", termMax);
    desc.html("올해 동아리 신입생은 <b>" + termMax + "</b>기 입니다");

    isTermValid = true;

    term.keyup(function() {
        if ($(this).val() < termMin || termMax < $(this).val()) {
            isTermValid = false;
        } else {
            isTermValid = true;
        }
    });
}

function bindPhoneNum() {
    var list = $("input[name='phone_num']");

    list.each(function(index, item) {
        $(item).keyup(function() {
            $(item).val($(item).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));

            if ($(item).val() != "") {
                isPhoneNumValid = true;
            }
        });
    });
}

function bindEmail() { // 선택창으로 이메일 도메인을 채울 수 있도록 함
    var list = $("select.email-domain");
    var domains = [
        { val: "", label: "직접입력" },
        { val: "naver.com", label: "naver.com" },
        { val: "gmail.com", label: "gmail.com" },
        { val: "daum.net", label: "daum.net" },
        { val: "kw.ac.kr", label: "kw.ac.kr" }
    ];

    list.each(function(indexL, itemL) {
        var target = $("#email_addr-domain");

        $(domains).each(function(indexD, itemD) {
            var v = document.createElement("option");
            v.value = itemD.val;
            v.innerHTML = itemD.label;
            itemL.appendChild(v);
        });

        $(this).change(function() {
            target.val($(this).val());

            if ($(this).val() != "") {
                target.attr("readonly", "true");
            } else {
                target.removeAttr("readonly");
            }
        });
    });
}

function bindAddress() {
    $("#btn-address").click(function() {
        new daum.Postcode({
            oncomplete: function(data) {
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
                    $("#sign-address3").attr("value", extraAddr);

                } else {
                    $("#sign-address3").attr("value", "");
                }
                $("#sign-post-num").attr("value", data.zonecode);
                $("#sign-address1").attr("value", addr);
                $("#sign-address2").focus();
            }
        }).open();
    });
}