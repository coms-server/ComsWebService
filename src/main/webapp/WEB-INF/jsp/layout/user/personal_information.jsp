<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/user_modify.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/seperateContent.css"/>">
</head>

<body>
<div id="task" class="sepContainer">
    <div id="modifyInfo" class="seperator box">
        <div class="box-header">
            <h3 class="title">
                정보수정
            </h3>
        </div>
        <div class="box-content">
            <div class="m-item">
                <div class="m-name">
                    <h4>재학상태</h4>
                </div>
                <hr class="noshow">
                <div class="m-value">
                    <input type="hidden" id="prevStatus" value="${userInfo.status}">
                    <select name="statusText" id="modi-status" class="status gray" required>
                        <option value="" selected hidden>선택</option>
                    </select>
                </div>
            </div>
            <div class="m-item">
                <div class="m-name">
                    <h4>휴대전화</h4>
                </div>
                <hr class="noshow">
                <div class="m-value">
                    <input type="text" id="modi-phone_num" name="phone_num" maxlength="13" class="phone_num"
                    value="${userInfo.phone_num}" placeholder="하이픈(-) 없이 작성" required>
                    <hr class="noshow">
                    <div class="checkbox">
                        <input type="hidden" id="prevSmsSend" value="${userInfo.sms_send}">
                        <input type="checkbox" name="sms_send" id="modi-phone-msg">
                        <label for="modi-phone-msg" class="pointer">메시지 수신 동의</label>
                    </div>
                </div>
            </div>
            <div class="m-item">
                <div class="m-name">
                    <h4>이메일</h4>
                </div>
                <hr class="noshow">
                <div class="m-value">
                    <input name="email_addr" id="modi-email_addr" hidden>
                    <input type="hidden" id="prevEmailId" value="<c:out value="${userInfo.email_addr}" />">
                    <div id="big">
                        <div id="email-id-area">
                            <input type="text" id="email_addr-id" required> <span id="at">@</span>
                        </div>
                        <div class="select-container">
                            <input type="text" id="email_addr-domain" class="near right" value="" required>
                            <select class="email-domain near left gray"></select>
                        </div>
                    </div>
                    <div class="checkbox">
                        <input type="hidden" id="prevEmailSend" value="${userInfo.email_send}">
                        <input type="checkbox" name="email_send" id="modi-email-msg">
                        <label for="modi-email-msg" class="pointer">이메일 수신 동의</label>
                    </div>
                </div>
            </div>
            <div class="m-item">
                <div class="m-name">
                    <h4>자택주소</h4>
                </div>
                <hr class="noshow">
                <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
                <div class="m-value">
                    <input name="home_addr" id="modi-home_addr" hidden>
                    <textarea id="prevHomeAddr" hidden><c:out value="${userInfo.home_addr}"/></textarea>
                    <div id="post-num-area">
                        <input type="text" id="modi-post-num" class="near right" placeholder="우편 번호">
                        <input type="button" id="btn-address" class="btn-address near left gray" value="우편번호 찾기"><br>
                    </div>
                    <div id="big">
                        <div id="small">
                            <input type="text" id="modi-address1" placeholder="기본주소">
                            <input type="text" id="modi-address2" placeholder="상세주소">
                        </div>
                        <input type="text" id="modi-address3" placeholder="참고항목">
                    </div>
                </div>
            </div>
            <hr class="noshow">
            <div style="text-align: center;">
                <input type="button" id="infoModiBtn" class="infoModiBtn agree" value="정보수정">
            </div>
            </div>
        </div>

        <div class="seperator">
            <div id="modifyPw" class="box">
                <div class="box-header">
                    <h3 class="title">
                      비밀번호 변경
                    </h3>
                </div>
                <div class="box-content">
                    <div class="m-item">
                        <div class="m-value">
                            <input type="password" name="password" id="modi-pw1" class="password" placeholder="비밀번호" autocomplete="off"
                                bind="modi-pw2" desc="pw-checker" required>
                            <p class="desc" id="pw-checker"></p>
                            <hr class="noshow">
                            <input type="password" name="modi-pw-again" id="modi-pw2" class="password" placeholder="비밀번호 확인" autocomplete="off"
                                desc="pw-rechecker" required>
                            <p class="desc" id="pw-rechecker"></p>
                        </div>
                    </div>
                    <div style="text-align: center;">
                        <input type="button" id="pwModiBtn" class="pwModiBtn agree" value="비밀번호 변경">
                    </div>
                </div>
            </div>
            <div id="leave" class="box">
                <div class="box-header">
                    <h3 class="title">
                        회원탈퇴
                    </h3>
                </div>
                <div class="box-content">
                    <div class="m-item" style="text-align: justify">
                        회원탈퇴 후 개인정보 처리에 대해서는 페이지 좌하단 <b>개인정보처리방침</b>을 참조해주시기 바랍니다. 탈퇴후에 모든 개인정보는 적절히 삭제되지만 회원이 <b>작성한 게시글/댓글은 삭제하지
                        않으니</b>, 탈퇴 후에 자신이 작성한 게시글이 보이지 않길 원한다면 <b>탈퇴전에 모두 삭제</b>해주시기 바랍니다.
                    </div>
                    <div style="text-align: center;">
                        <input id="user_idx" value="${userInfo.user_idx}" hidden>
                        <input type="button" id="leaveBtn" class="leave danger" value="회원탈퇴">
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>