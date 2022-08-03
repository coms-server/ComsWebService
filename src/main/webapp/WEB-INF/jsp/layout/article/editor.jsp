<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/quill.snow.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/vs2015.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/article/quill/katex.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/css/article/editor.css"/>">

    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/article/quill/highlight.pack.js"/>"></script>
    <script src="<c:url value="/resources/js/article/quill/katex.min.js"/>"></script>
    <script src="<c:url value="/resources/js/article/quill/quill.min.js"/>"></script>
    <script src="<c:url value="/resources/js/article/quill/image-resize.min.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/article/editor.js"/>"></script>
</head>

<body>
<div id="INFO" type="hidden">
    <input type="hidden" id="article_idx" value="${article.article_idx}">
    <input type="hidden" id="context" value="${context}">
    <input type="hidden" id="board_idx" value="${board_idx}">
    <input type="hidden" id="is_subarticle" value="${is_subarticle}">
    <input type="hidden" id="is_notice" value="${article.is_notice}">
    <input type="hidden" id="parent_idx" value="${parentArticle.article_idx}">
</div>

<div class="content editor-container">

    <div class="pagedesc">
        <h2 class="title">
            <c:out value="${boardName} - 글쓰기"/>
        </h2>
    </div>

    <c:if test="${is_subarticle eq true}">
            <div class="quill-container box border">
                <div id="quill-reader" style="visibility:hidden;">
                    <c:out value="${parentArticle.content}" />
                </div>
            </div>
        </c:if>

        <div class="box">
            <div id="title" class="box-header">
                <input type="text" class="dark" placeholder="제목" name="title" tabindex="1" value="<c:out value='${article.title}'/>">
                <hr class="noshow">
                <sec:authorize access="principal.isAdmin()">
                    <div class="checkbox">
                        <input type="checkbox" id="isNotice">
                        <label for="isNotice" class="pointer">공지</label>
                    </div>
                </sec:authorize>
            </div>
            <div class="box-content">
                <div class="quill-container">
                    <div id="quill-toolbar">
                        <span class="ql-formats">
                            <select class="ql-font"></select>
                            <select class="ql-size"></select>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-bold"></button>
                            <button class="ql-italic"></button>
                            <button class="ql-underline"></button>
                            <button class="ql-strike"></button>
                        </span>
                        <span class="ql-formats">
                            <select class="ql-color"></select>
                            <select class="ql-background"></select>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-script" value="sub"></button>
                            <button class="ql-script" value="super"></button>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-header" value="1"></button>
                            <button class="ql-header" value="2"></button>
                            <button class="ql-blockquote"></button>
                            <button class="ql-code-block"></button>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-list" value="ordered"></button>
                            <button class="ql-list" value="bullet"></button>
                            <button class="ql-indent" value="-1"></button>
                            <button class="ql-indent" value="+1"></button>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-direction" value="rtl"></button>
                            <select class="ql-align"></select>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-link"></button>
                            <button class="ql-image"></button>
                            <button class="ql-video"></button>
                            <button class="ql-formula"></button>
                        </span>
                        <span class="ql-formats">
                            <button class="ql-clean"></button>
                        </span>
                    </div>
                    <div id="quill-editor" style="visibility:hidden;">
                        <c:out value="${article.content}" />
                    </div>
                    <div id="attachment-container">
                        <div id="prev-attachment-list">
                            <c:forEach var="attachment" items="${attachments}" varStatus="status">
                                <div class="attachment prev-attachment noselect">
                                    <a href=<c:out value="${attachment.directory}" /> download>
                                    <c:out value="${attachment.filename} (${attachment.filesize} ${attachment.unit})" />
                                    </a>
                                </div>
                                <c:if test="${status.last}">
                                    <p class="prev-attachment-noti">
                                        파일을 새로 업로드하면 기존 파일은 삭제됩니다. 미리 백업해 주세요.
                                    </p><hr>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div id="new-attachment-list"></div>
                    </div>
                </div>
            </div>
        
            <div class="box-footer underbar">
                <label class="file dark">
                    <i class="fas fa-paperclip"></i>
                    <span>
                        파일첨부
                    </span>
                    <input type="file" name="attachment" id="attachment" class="hidden" multiple>
                </label>
                <div>
                    <button class="submit agree">
                        <i class="fas fa-pen fa-vc"></i>
                            제출
                    </button>
                    <button id="abort" class="dark">
                        취소
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>