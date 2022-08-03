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
    <script type="module" src="<c:url value="/resources/js/event/new_event_form.js"/>"></script>

    <title>행사 추가</title>
</head>

<body>
<div class="modal fade" id="addEventModal" tabindex="-1" role="dialog" aria-labelledby="addEventModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addEventModalLabel">행사 추가</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="a-info">
                    <div class="a-item">
                        <div class="a-name">
                            <p>개요</p>
                        </div>
                    <div class="a-value">
                        <input type="text" id="eTitle" class="full width" placeholder="제목" required>
                        <hr class="noshow">
                        <textarea id="eDesc" class="full width" placeholder="설명" required ></textarea>
                    </div>
                </div>

                <div class="a-item">
                    <div class="a-name">
                        <p>일정</p>
                    </div>
                    <div class="a-value">
                        <div class="dateArea">
                            <input type="datetime-local" id="eBeginDate" class="near right" max="2099-12-31T00:00:00" required>
                            <hr class="noshow">
                            <input type="datetime-local" id="eEndDate" class="near left" max="2099-12-31T00:00:00" required>
                        </div>                    
                    </div>
                </div>
                <div class="a-item">
                    <div class="a-name">
                        <p>안내글</p>
                    </div>
                    <div class="a-value">
                        <input type="text" id="url" class="full width" placeholder="URL">
                    </div>
                </div>
                <div class="a-item">
                    <div class="a-name">
                        <p>필수참여 여부</p>
                    </div>
                    <div class="a-value">
                        <div class="checkbox">
                            <input type="checkbox" id="eIsImportant">
                            <label for="eIsImportant" class="pointer">연례행사 등 필수행사라면 체크</label>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" id="addEventBtn" class="btn btn-secondary" data-dismiss="modal">추가</button>
            <button type="button" id="closeBtn" class="btn btn-primary">취소</button>
          </div>
        </div>
      </div>
    </div>
</body>