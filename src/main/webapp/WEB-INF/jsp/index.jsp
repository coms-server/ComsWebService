<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="index_container" value="true" scope="request" />
<!doctype html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <%-- <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"> --%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--
        TODO: og:url, og:image 값 작성 필
    -->
    <meta property="og:url" content="">
    <meta property="og:type" content="website">
    <meta property="og:title" content="광운대학교 COM'S">
    <meta property="og:description" content="광운대학교 컴퓨터동아리 COM's입니다.">
    <meta property="og:image" content="">

    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
    <meta id="_csrf" name="_csrf" content="${_csrf.token}" />

    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" href="/resources/css/fontawesome/all.min.css">
    <link rel="shortcut icon" href="/resources/css/svg/logo.svg">

    <script src="/resources/js/jquery-3.4.1.min.js"></script>
    <script type="module" src="/resources/js/header.js"></script>

    <title>COM'S</title>
</head>

<body id="bd-${layoutType}">

    <div id="notiContainer">
        <div class="notiList"></div>
    </div>
    <div id="notiCount"></div>
    <div id="sidebar" class="noselect">
        <a class="sidebar-toggle">
            <i class="fas fa-bars fa-xbig"></i>
        </a>
        <div class="top-logo">
            <a href="/">
                <img src="/resources/css/svg/logo.svg" alt="COM's" title="COM's">
            </a>
        </div>
        <div class="logo-divide"></div>
        <sec:authorize access="!isAuthenticated()">
            <div class="nav-list nav-usr login">
                <div class="toolbar">
                    <a href="/login" title="로그인">
                        <i class="fas fa-sign-in-alt"></i>
                        <p>로그인</p>
                    </a>
                    <a href="/account/signup" title="회원가입">
                        <i class="fas fa-user-plus"></i>
                        <p>회원가입</p>
                    </a>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <div class="nav-list nav-usr profileContainer">
                <div class="toolbar">
                    <a class="noti-toggle" title="알림">
                        <i class="fas fa-bell"></i>
                        <p>알림</p>
                    </a>
                    <a href="/mypage/index" title="마이페이지">
                        <i class="fas fa-user"></i>
                        <p>내정보</p>
                    </a>
                    <sec:authorize access="principal.isAdmin()">
                        <a href="/admin/index" title="관리자">
                            <i class="fas fa-user-astronaut"></i>
                            <p>관리자</p>
                        </a>
                    </sec:authorize>
                </div>
                <div class="profileBox">
                    <div class="profile">
                        <sec:authentication property="principal.user_idx" var="user_idx" />
                        <div class="profilePic">
                            <img src="/profile/${user_idx}/60.jpg">
                        </div>
                        <div class="desc">
                            <input id="user_idx" value="${user_idx}" hidden>
                            <sec:authentication property="principal.name" />
                        </div>
                    </div>
                    <a class="logout" title="로그아웃">
                        <form:form action="/logout" method="post">
                            <i class="fas fa-sign-out-alt"></i>
                            <p>로그아웃</p>
                        </form:form>
                    </a>
                </div>
            </div>
        </sec:authorize>
        <div id="linkList">
            <div class="nav-list nav-board">
                <div class="rootBoard">
                    <a href="/event/list/" class="board">
                        <h4 class="nav-category-label">
                            <i class="fas fa-calendar-check"></i>
                            행사 목록
                        </h4>
                    </a>
                </div>
                
                <div class="rootBoard">
                    <a href="/board/notice/" class="board">
                        <h4 class="nav-category-label">
                            <i class="fas fa-star"></i>
                            공지사항
                        </h4>
                    </a>
                    
                    <div class="subBoardContainer">
                        <div class="subBoard">
                            <a href="/board/coms/" class="board">
                                <h4 class="nav-category-label">
                                    동아리 공지
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/kwu/" class="board">
                                <h4 class="nav-category-label">
                                    교내/총동연 공지
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/event/" class="board">
                                <h4 class="nav-category-label">
                                    행사일정
                                </h4>
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="rootBoard">
                    <a href="/board/activity/" class="board">
                        <h4 class="nav-category-label">
                            <i class="fas fa-comments"></i>
                            동아리 활동
                        </h4>
                    </a>
                    <div class="subBoardContainer">
                        <div class="subBoard">
                            <a href="/board/journal/" class="board">
                                <h4 class="nav-category-label">
                                    학술지
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/study/" class="board">
                                <h4 class="nav-category-label">
                                    스터디
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/common-event/" class="board">
                                <h4 class="nav-category-label">
                                    일반행사
                                </h4>
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="rootBoard">
                    <a href="/board/community/" class="board">
                        <h4 class="nav-category-label">
                            <i class="fas fa-comments"></i>
                            커뮤니티
                        </h4>
                    </a>
                    <div class="subBoardContainer">
                        <div class="subBoard">
                            <a href="/board/free-subject/" class="board">
                                <h4 class="nav-category-label">
                                    자유게시판
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/game/" class="board">
                                <h4 class="nav-category-label">
                                    게임
                                </h4>
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="rootBoard">
                    <a href="/board/qna/" class="board">
                        <h4 class="nav-category-label">
                            <i class="fas fa-graduation-cap"></i>
                            Q&A
                        </h4>
                    </a>
                    <div class="subBoardContainer">
                        <div class="subBoard">
                            <a href="/board/about-coms/" class="board">
                                <h4 class="nav-category-label">
                                    동아리
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/programming/" class="board">
                                <h4 class="nav-category-label">
                                    프로그래밍
                                </h4>
                            </a>
                        </div>
                        <div class="subBoard">
                            <a href="/board/assignment/" class="board">
                                <h4 class="nav-category-label">
                                    과제
                                </h4>
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="url">
                    <a href="/user/list">
                        <h4 class="nav-category-label">
                            <i class="fa fa-users"></i>
                            동아리 회원명부
                        </h4>
                    </a>
                </div>
                <%--<div class="url">
                    <a href="https://github.com/coms-server/server-issue/issues" target="_blank">
                        <h4 class="nav-category-label">
                            <i class="fab fa-github"></i>
                            github issue
                        </h4>
                    </a>
                </div>--%>
            </div>
        </div>
        <div id="contributors">
            <div id="socialLink">
                <div class="sLink">
                    <a href="https://github.com/coms-server/server-issue/issues">
                        <i class="fab fa-github"></i>
                    </a>
                </div>
                <div class="sLink">
                    <img src="/resources/css/svg/logo_gray.svg" alt="COM's" title="COM's">
                </div>
                <div class="sLink">
                    <a href="https://www.instagram.com/kwcoms/">
                        <i class="fab fa-instagram"></i>
                    </a>
                </div>
            </div>
            <div class="link">
                <span>
                    <a href="/about-coms">
                        About COM'S
                    </a>
                </span>
                <span>
                    <a href="/information-policy">
                        개인정보처리방침
                    </a>
                </span>
                <span>
                    <a href="mailto:coms-webmaster@naver.com">
                        Contact Us
                    </a>
                </span>
            </div>
            <div class="copyright">
                <span>
                    Copyright © 2020 COM'S All Rights Reserved.
                </span>
            </div>
        </div>
    </div>

    <%-- Main Content --%>
    <c:import url="./layout/${layoutType}.jsp" charEncoding="UTF-8"/>
    <%-- c:import 문은 Jstl이라는 녀석인데, 다른 요소를 가져다 넣는 역할을 한다.
        알아두면 좋은 점: jstl은 웹브라우저에 표시되기 전, 일정한 처리를 거쳐서 약간.. 컴파일 되듯이 처리되어서 나오는 녀석이다.
        ${}
    --%>
</body>

</html>