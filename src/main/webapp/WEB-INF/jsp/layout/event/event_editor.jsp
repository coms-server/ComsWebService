<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/default-set.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/admin/admin_popup.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table-sticky-header.min.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/event/event_editor.js"/>"></script>

    <title>행사 관리</title>
</head>

<body>
<div class="content">
    <h2>행사 관리</h2>
    <div class="a-info">
        <input id="event_idx" value="${event.event_idx}" hidden>
        <div class="a-item">
            <div class="a-name">
                <p>개요</p>
            </div>
            <div class="a-value edit">
                <input type="text" id="eTitle" class="full width" value="${event.title}" placeholder="제목" required>
                <hr class="noshow">
                <textarea id="eDesc" class="full width" placeholder="설명" required><c:out
                        value="${event.event_desc}"/></textarea>
            </div>
        </div>

        <div class="a-item">
                <div class="a-name">
                    <p>일정</p>
                </div>
                <div class="a-value edit">
                    <div class="dateArea">
                        <input type="datetime-local" id="eBeginDate" class="near right full width" 
                            value="${event.begin_date}" max="2099-12-31" required>
                        <hr class="noshow">
                        <input type="datetime-local" id="eEndDate" class="near left full width" 
                            value="${event.end_date}" max="2099-12-31" required>
                    </div>                    
                </div>
            </div>
            <div class="a-item">
                <div class="a-name">
                    <p>안내글</p>
                </div>
                <div class="a-value edit">
                    <input type="text" id="url" class="full width" value="${event.url}" placeholder="URL">
                </div>
            </div>
            <div class="a-item">
                <div class="a-name">
                    <p>필수참여 여부</p>
                </div>
                <div class="a-value edit">
                    <div class="checkbox">
                        <input type="checkbox" id="eIsImportant" value="${event.is_important}">
                        <label for="eIsImportant" class="pointer">연례행사 등 필수행사라면 체크</label>
                    </div>
                </div>
            </div>
            <div class="a-item">
                <div class="a-name">
                    <p>참여인원</p>
                </div>

                <div class="a-value edit">
                    <div id="selectUser">
                        <c:import url="/admin/user/selector">
                            <c:param name="selected" value="${selected}"></c:param>
                        </c:import>
                    </div>
                    <hr class="noshow">
                    <input id="userList" hidden>
                </div>
            </div>
            <div class="a-item">
                <div class="a-name">
                    <p>참여인원</p>
                </div>

                <div class="a-value userList">
                    <div id="userNames">
                        <c:forEach var="user" items="${userLIst}">
                            <label>${user.term}기 ${user.name}</label><br>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div style="text-align: center;">
            <button id="saveBtn" class="agree">저장</button>
            <button id="closeBtn">취소</button>
        </div>
    </div>
</body>