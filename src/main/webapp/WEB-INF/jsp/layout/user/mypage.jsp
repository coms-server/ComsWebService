<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/selectable.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">

    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>

    <script type="module" src="<c:url value="/resources/js/user/big_profile.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/user/personal_information.js"/>"></script>
</head>

<body>
<sec:authentication property="principal.user_idx" var="user_idx"/>
<div class="content noselect">
    <div class="pagedesc">
        <h2 class="title" style="font-size: 1.5em">마이페이지</h2>
        <p class="desc">개인정보 관리</p>
    </div>
    <div id="TaskArea" class="box">
        <div class="box-content">
            <ul class="nav nav-tabs" id="myTab" role="tablist" style="margin-bottom: 20px">
                <li class="nav-item" role="presentation">
                    <a class="nav-link active" id="user-tab" data-toggle="tab" href="#profile" role="tab"
                       aria-controls="userProfile" aria-selected="true">프로필</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="event-tab" data-toggle="tab" href="#event" role="tab"
                       aria-controls="eventManager" aria-selected="false">개인정보</a>
                </li>
            </ul>
            <div class="tab-content" id="task">
                    <div class="tab-pane content fade show active" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                        <c:import url="/user/big-profile/${user_idx}"/>
                    </div>
                    <div class="tab-pane content fade" id="event" role="tabpanel" aria-labelledby="event-tab">
                        <c:import url="/mypage/information/${user_idx}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>