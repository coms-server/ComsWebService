<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/article/board.css"/>">

    <script type="module" src="<c:url value="/resources/js/article/board.js"/>"></script>
</head>

<body>
<main class="content">

    <div class="pagedesc">
        <h2 class="title noselect"><a href="/board/${context}/list/1">
            <c:out value="${boardName}"/>
        </a></h2>
    </div>

    <div class="box article-list-area">
        <div class="box-content">
            <div class="article-list notice">
                <div id="INFO" type="hidden">
                    <input type="hidden" id="context" value="${context}">
                </div>
                <c:forEach var="ntc" items="${notices}">
                    <div class="article-container">
                        <div class="doc-idx">${ntc.article_idx}</div>
                            <div class="doc-title">
                                <b><c:out value="[공지] ${ntc.title}" /></b>
                            </div>
                            <div class="doc-date">
                                ${ntc.write_date_string}
                            </div>
                            <div class="doc-author">
                                <c:import url="/user/small-profile/${ntc.user_idx}"/>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="article-list">
                    <div id="INFO" type="hidden">
                        <input type="hidden" id="context" value="${context}">
                    </div>
                    <c:forEach var="atc" items="${articles}">
                        <div class="article-container">
                            <div class="doc-idx">
                                ${atc.article_idx}
                            </div>
                            <div class="doc-title">
                                <c:out value="${atc.title}"/>
                            </div>
                            <div class="doc-date">
                                ${atc.write_date_string}
                            </div>
                            <div class="doc-author">
                                <c:import url="/user/small-profile/${atc.user_idx}"/>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="box-footer underbar">
                <nav class="page-idx" aria-label="Page navigation example">
                    <ul class="pagination">
                        <li class="page-item">
                            <a class="page-link" href="${pager.veryFirstPage}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">Previous</span>
                        </a>
                    </li>
                    <c:forEach var="pageNum" begin="${pager.beginPage}" end="${pager.endPage}">
                        <c:choose>
                            <c:when test="${pageNum eq pager.currentPage}">
                                <li class="page-item"><a class="page-link active" href="#">${pageNum}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="/board/${context}/list/${pageNum}">${pageNum}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li class="page-item">
                      <a class="page-link" href="${pager.veryLastPage}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                      </a>
                    </li>
                  </ul>
                </nav>            
                <c:choose>
                    <c:when test="${is_root eq false}">
                        <c:choose>
                            <c:when test="${is_important eq true}">
                                <sec:authorize access="principal.isAdmin()">
                                    <button id="newArticle" class="newArticle dark noselect">
                                        <i class="fas fa-pen fa-vc"></i>
                                        새 글쓰기
                                    </button>
                                </sec:authorize>
                                <sec:authorize access="!principal.isAdmin()">
                                    <div id="dummy" class="newArticle"></div>
                                </sec:authorize>
                            </c:when>
                            <c:otherwise>
                                <button id="newArticle" class="newArticle dark noselect">
                                    <i class="fas fa-pen fa-vc"></i>
                                    새 글쓰기
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <div id="dummy" class="newArticle"></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </main>
</body>