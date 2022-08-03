<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/default-set.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/admin/admin_popup.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/executive_editor.js"/>"></script>

    <title>임원단 추가</title>
</head>

<body>
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#execEditModal">
    Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="execEditModal" tabindex="-1" role="dialog" aria-labelledby="execEditModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">임원단 관리</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            <div class="a-info">
                <div class="a-item">
                    <div class="a-name">
                        <p>이름</p>
                    </div>
                    <div class="a-value">
                        <input type="hidden" id="user_idx" value="${executiveInfo.user_idx}">
                        <p><c:out value="${executiveInfo.name}" /></p>
                    </div>
                </div>

                <div class="a-item">
                    <div class="a-name">
                        <p>직책</p>
                    </div>
                    <div class="a-value">
                        <input id="prevPosit" value="${executiveInfo.position}" hidden>
                        <select id="position" class="position" value="${executiveInfo.position}">
                            <option value="" selected hidden>선택</option>
                        </select>
                    </div>
                </div>
                <div class="a-item">
                    <div class="a-name">
                        <p>회장단 기수</p>
                    </div>
                    <div class="a-value">
                        <input type="text" id="nth" value="${executiveInfo.nth}" placeholder="기수">
                        <hr class="noshow">
                        <div class="checkbox">
                            <input id="prevActive" value="${executiveInfo.active}" hidden>
                            <input type="checkbox" id="active">
                            <label for="active" class="pointer">현재 임기중이라면 체크</label>
                        </div>
                    </div>
                </div>
                <div class="a-item">
                    <div class="a-name">
                        <p>임원단 제명</p>
                    </div>
                    <div class="a-value">
                        <input id="exec_idx" value="${executiveInfo.exec_idx}" hidden>
                        <button id="extract" class="danger" value="제명">제명</button>
                    </div>
                </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary">저장</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>
</body>