<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>

    <script type="module" src="<c:url value="/resources/js/login.js"/>"></script>
</head>

<main class="content">
    <div class="pagedesc">
        <h2 class="title">로그인</h2>
    </div>
    <div class="login-area box">
        <form:form action="/login" method="post" id="login-form">
            <label for="alias">아이디</label>
            <input id="alias" name="alias" type="text" placeholder="아이디" style="height: 37px !important;" autofocus
                   required>
            <label for="password">비밀번호</label>
            <input id="password" name="password" type="password" placeholder="비밀번호" style="height: 37px !important;"
                   required>
            <input type="submit" id="submit" class="agree" value="로그인">
        </form:form>
        <div>
            <span id="signup">회원가입</span>
            <span id="find">아이디/비밀번호 찾기</span>
        </div>
    </div>
</main>