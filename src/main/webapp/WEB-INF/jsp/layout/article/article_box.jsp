<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <c:if test="${qr_pragma_once == null}">
        <c:set var="qr_pragma_once" value="true" scope="request"/>
        <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/quill.snow.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/vs2015.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/katex.min.css"/>">

        <script src="<c:url value="/resources/js/article/quill/highlight.pack.js"/>"></script>
        <script src="<c:url value="/resources/js/article/quill/katex.min.js"/>"></script>
        <script src="<c:url value="/resources/js/article/quill/quill.min.js"/>"></script>
        <script src="<c:url value="/resources/js/article/quill/image-resize.min.js"/>"></script>
    </c:if>
</head>

<body>
    <div class="reader-container">
        <div class="title box-header">
            <div class="article-header">
                <c:if test="${qnaMode}">
                    <c:choose>
                        <c:when test="${article.is_subarticle eq true}">
                            <span>A</span>
                        </c:when>
                        <c:otherwise>
                            <span>Q</span>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
            <h2 class="article-title">
                <c:out value="${article.title}" />
            </h2>
            <p class="article-info">
                <span class="author">
                    <a class="button-writter"><c:out value="${article.name}" /></a>
                    <input type="hidden" name="article_user_idx" value="${article.user_idx}" hidden>
                </span>
                <span class="view">
                    <c:out value="${article.write_date_string}" />
                </span>
                <input type="hidden" name="article_idx" value="${article.article_idx}">

                <sec:authorize access="principal.isAdmin() or principal.user_idx eq ${article.user_idx}">
                    <span class="modify"><a class="button-modify">수정</a></span>
                    <span class="delete"><a class="button-delete ${article.is_subarticle}">삭제</a></span>
                </sec:authorize>
            </p>
        </div>

        <div id="newAnswer-area" class="article box-content">

            <%-- NOTE: VOTE --%>
            <div class="voteArea left">
                <div class="upVote voteBtn">
                    <i class="fas fa-chevron-up fa-xbig"></i>
                </div>
                <div class="vote noselect"></div>
                <div class="downVote voteBtn">
                    <i class="fas fa-chevron-down fa-xbig"></i>
                </div>
            </div>

            <%-- NOTE: ARTICLE AREA --%>
            <div class="content-area">
                <div class="attachment">
                    <table>
                        <c:forEach var="attachment" items="${attachments}">
                            <tr>
                                <td>
                                    <a href="${attachment.directory}" download>
                                        <c:out value="${attachment.filename} (${attachment.filesize} ${attachment.unit})" />
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <div class="quill-area">
                    <div class="quill-reader" style="visibility:hidden;">
                        <c:out value="${article.content}" />
                    </div>
                </div>
                
                <div class="voteArea bottom">
                    <div class="upVote voteBtn">
                        <i class="fas fa-chevron-up fa-xbig"></i>
                    </div>
                    <div class="vote noselect"></div>
                    <div class="downVote voteBtn">
                        <i class="fas fa-chevron-down fa-xbig"></i>
                    </div>
                </div>
                
                <div class="dummyArea"></div>

                <div>
                    <c:import url="/user/small-profile/${article.user_idx}"/>
                </div>
            </div>

            <%-- NOTE: COMMENT --%>
            <c:choose>
                <c:when test="${article.is_notice eq true || is_important eq true}">
                    <div class="comment-area">
                        <div class="commentContainer">
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="comment-area">
                        <div class="commentContainer">
                            <c:import url="/comment/box/${article.article_idx}"/>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${qnaMode eq true}">
            <c:choose>
                <c:when test="${article.is_subarticle eq true}">
                </c:when>
                <c:otherwise>
                    <div id="newAnswer-area" class="box-footer">
                        <button class="agree">
                            새로운 답변
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</body>