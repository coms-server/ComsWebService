<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/css/seperateContent.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/article/board.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/mainPage.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>

    <script type="module" src="<c:url value="/resources/js/main.js"/>"></script>
    <script src="<c:url value="/resources/js/moment.min.js"/>"></script>
</head>

<body>
<main class="content">
    <%-- <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>공지 제목</strong> 이곳에 공지 내용이 표시됩니다.
    </div>
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <strong>공지 제목</strong> 이곳에 공지 내용이 표시됩니다.
    </div> --%>
    <div class="sepContainer">
        <div id="eventNotiColumn" class="seperator">
            <div class="board-new-doc notice box">
                <div class="box-header link" onClick="location.href = '/board/notice/'">
                    <h2 class="title">
                        공지사항
                    </h2>
                </div>
                <div class="box-content">
                    <div class="article-list">
                            <div class="INFO" type="hidden">
                                <input type="hidden" class="context" value="notice">
                            </div>
                            <c:forEach var="atc" items="${articleNotice}">
                                <div class="article-container">
                                    <div class="doc-idx hidden">
                                        <c:out value="${atc.article_idx}" />
                                    </div>
                                    <div class="doc-title">
                                        <c:out value="${atc.title}" />
                                    </div>
                                    <div class="doc-date">
                                        <c:out value="${atc.write_date_string}" />
                                    </div>
                                    <div class="doc-author">
                                        <c:import url="/user/small-profile/${atc.user_idx}"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="board-new-doc activity box">
                    <div class="box-header link">
                        <h2 class="title" onClick="location.href = '/board/activity/'">
                            동아리활동
                        </h2>
                    </div>
                    <div class="box-content">
                        <div class="article-list">
                            <div class="INFO" type="hidden">
                                <input type="hidden" class="context" value="activity">
                            </div>
                            <c:forEach var="atc" items="${articleActivity}">
                                <div class="article-container <c:if test="${atc.is_notice}">notice</c:if>">
                                    <div class="doc-idx hidden">
                                        <c:out value="${atc.article_idx}" />
                                    </div>
                                    <div class="doc-title">
                                        <c:out value="${atc.title}" />
                                    </div>
                                    <div class="doc-date">
                                        <c:out value="${atc.write_date_string}" />
                                    </div>
                                    <div class="doc-author">
                                        <c:import url="/user/small-profile/${atc.user_idx}"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="seperator">
                <div class="board-new-doc community box">
                    <div class="box-header link">
                        <h2 class="title" onClick="location.href = '/board/community/'">
                            커뮤니티
                        </h2>
                    </div>
                    <div class="box-content">
                        <div class="article-list">
                            <div class="INFO" type="hidden">
                                <input type="hidden" class="context" value="community">
                            </div>
                            <c:forEach var="atc" items="${articleCommunity}">
                                <div class="article-container <c:if test="${atc.is_notice}">notice</c:if>">
                                    <div class="doc-idx hidden">
                                        <c:out value="${atc.article_idx}" />
                                    </div>
                                    <div class="doc-title">
                                        <c:out value="${atc.title}" />
                                    </div>
                                    <div class="doc-date">
                                        <c:out value="${atc.write_date_string}" />
                                    </div>
                                    <div class="doc-author">
                                        <c:import url="/user/small-profile/${atc.user_idx}"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                
                <div class="board-new-doc qna box">
                    <div class="box-header link" onClick="location.href = '/board/qna/'">
                        <h2 class="title">
                            Q&A
                        </h2>
                    </div>
                    <div class="box-content">
                        <div class="article-list">
                            <div class="INFO" type="hidden">
                                <input type="hidden" class="context" value="qna">
                            </div>
                            <c:forEach var="atc" items="${articleQna}">
                                <div class="article-container <c:if test="${atc.is_notice}">notice</c:if>">
                                    <div class="doc-idx hidden">
                                        <c:out value="${atc.article_idx}" />
                                    </div>
                                    <div class="doc-title">
                                        <c:out value="${atc.title}" />
                                    </div>
                                    <div class="doc-date">
                                        <c:out value="${atc.write_date_string}" />
                                    </div>
                                    <div class="doc-author">
                                        <c:import url="/user/small-profile/${atc.user_idx}"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>
            
        </div>
    </main>
</body>