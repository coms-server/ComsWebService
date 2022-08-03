<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/article/reader.css"/>">

    <script type="module" src="<c:url value="/resources/js/article/reader.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
</head>

<body>
<div id="INFO" type="hidden">
    <input type="hidden" id="context" value="${context}">
    <input type="hidden" id="board_idx" value="${board_idx}">
    <input type="hidden" id="page" value="${page}">
    <input type="hidden" id="article_idx" value="${mainArticle.article_idx}">
</div>

<main id="content" class="content">

    <div class="pagedesc">
        <h2 class="title">
            <c:out value="${boardName}"/>
        </h2>
    </div>

    <!-- main article -->
        <div class="post box">
            <div class="article-area">
                <c:set var="article" value="${mainArticle}" scope="request" />
                <c:import url="./layout/article/article_box.jsp" charEncoding="UTF-8" />
                <c:remove var="article" scope="request" />
            </div>
        </div>

        <!-- answer articles -->
        <c:if test="${qnaMode}">
            <c:forEach var="answer" items="${answers}">
                <div class="post box">
                    <div class="answer-area">
                            <c:set var="article" value="${answer}" scope="request" />
                            <c:import url="./layout/article/article_box.jsp" charEncoding="UTF-8" />
                            <c:remove var="article" scope="request" />
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <div class="aside-doc-area box">
            <div class="underbar box-header">
                <button id="toList" class="dark">
                    목록으로
                </button>
            </div>
            <div class="article-list box-content">
                <c:if test="${(fn:length(sideArticles) eq 1 && sideArticles[0].article_idx lt mainArticle.article_idx) || fn:length(sideArticles) eq 0}">
                    <div class="no-article noselect">다음 게시글이 없습니다.</div>
                </c:if>
                <c:forEach var="satc" items="${sideArticles}">
                    <div class="article-container">
                        <div class="doc-idx hidden">
                            <c:out value="${satc.article_idx}" />
                        </div>
                        <div class="doc-title">
                            <c:out value="${satc.title}" />
                        </div>
                        <div class="doc-date">
                            <c:out value="${satc.write_date_string}" />
                        </div>
                        <div class="doc-author">
                            <c:import url="/user/small-profile/${satc.user_idx}"/>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${(fn:length(sideArticles) eq 1 && sideArticles[0].article_idx gt mainArticle.article_idx) || fn:length(sideArticles) eq 0}">
                    <div class="no-article noselect">이전 게시글이 없습니다.</div>
                </c:if>
            </div>
        </div>
    </main>
</body>