import {alert} from "./tools.js";

$(document).ready(function() {

    $("#signup").click(function() {
        window.location.href = "/account/signup";
    });

    $("#find").click(function() {
        new alert("관리자에게 문의해주세요");
    })
});