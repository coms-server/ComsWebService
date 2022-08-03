<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <c:if test="${cm_pragma_once == null}">
        <c:set var="cm_pragma_once" value="true" scope="request"/>
        <link rel="stylesheet" href="<c:url value="/resources/css/article/comment.css"/>">
        <script type="module" src="<c:url value="/resources/js/article/comment.js"/>"></script>
    </c:if>
</head>

<body>
    <div class="C-INFO" type="hidden">
        <input type="hidden" name="article_idx" value="${article_idx}">
    </div>

    <div class="commentEditor newComment">
        <form:form action="/comment" method="post" id="commentForm_${article_idx}_0">
            <input type="hidden" name="article_idx" value="${article_idx}">
            <input type="hidden" name="parent_idx">
            <textarea class="textbox flexcontent" name="content"></textarea>
            <div class="flexcontent">
                <input type="submit" class="near bottom" value="댓글달기">
                <input type="button" class="near top" value="취소">
            </div>
        </form:form>
    </div>

    <div class="commentBox">
        <c:forEach var="cmt" items="${comments}">
            <c:choose>
                <c:when test="${cmt.is_deleted eq 'false'}">
                    <div class="comment ${cmt.parent_idx eq null ? '' : 'reComment'}">
                        <div class="inb">
                                <p class="comment-info">
                                    <span>
                                        <a class="button-name">
                                            <c:out value="${cmt.name}" />
                                            <input class="user_idx" value="${cmt.user_idx}" hidden>
                                        </a>
                                    </span>
                                    <span>
                                        <c:out value="${cmt.write_date_string}" />
                                    </span>
                                    <c:if test="${cmt.is_modified eq true}">
                                        <span>수정됨</span>
                                    </c:if>
                                    <input type="hidden" name="comment_idx" value="${cmt.comment_idx}" />
                                </p>
                                <div class="tool">
                                    <div class="btns">
                                        <button class="button-reply collapsible near right">
                                            <i class="far fa-comment-dots fa-small"></i>
                                        </button>
                                    </div>
                                    <sec:authorize access="principal.isAdmin() or principal.user_idx eq ${cmt.user_idx}">
                                        <div class="etc">
                                            <button class="near left">
                                                <i class="fas fa-pencil-alt fa-small"></i>
                                            </button>
                                            <div class="menu">
                                                <ul>
                                                    <li class="button-editComment">수정</li>
                                                    <hr>
                                                    <li class="button-deleteComment">삭제</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </sec:authorize>
                                </div>
                        </div>

                        <div class="comment-content">
                            <div class="toggle-hidden">
                                <p style="word-wrap: break-word">
                                    <c:out value="${cmt.content}" />
                                </p>
                            </div>

                            <div class="toggle-hidden modified-text hidden">
                                <textarea class="textbox near right" name="content"><c:out value="${cmt.content}" /></textarea>
                                    <button class="button-update near left">확인</button>
                            </div>
                        </div>

                        <div class="commentEditor collapContent" style="display: none;">
                            <form:form action="/comment" method="post" name="commentForm" id="commentForm_${cmt.comment_idx}">
                                <input type="hidden" name="article_idx" value="${article_idx}">
                                <input type="hidden" name="parent_idx"
                                    value="${cmt.parent_idx eq null ? cmt.comment_idx : cmt.parent_idx}">
                                <textarea class="textbox flexcontent" name="content"></textarea>
                                <div class="flexcontent">
                                    <input type="submit" value="답글달기" class="near bottom">
                                    <input type="button" class="cancel near top" value="취소">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <c:if test="${cmt.parent_idx eq nu}">
                        <div class="comment">
                            <div class="inb">
                            </div>
                            <p class="comment-content">
                                삭제된 댓글입니다.
                            </p>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</body>