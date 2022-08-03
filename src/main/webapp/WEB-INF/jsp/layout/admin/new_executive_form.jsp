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

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/new_executive_form.js"/>"></script>


    <title>임원단 추가</title>
</head>

<body>
<div class="content">
    <h2>임원단 추가</h2>
    <div class="a-info">
        <div class="a-item">
            <div class="a-name">
                <p>대상</p>
            </div>
            <div class="a-value">
                <div class="searchArea">
                    <input type="text" id="user_idx" class="near right" placeholder="UserIndex">
                </div>
                <hr class="noshow">
                <button onClick="window.open('/admin/user/manager', '_blank')">UserIndex 검색</button>
            </div>
            </div>

            <div class="a-item">
                <div class="a-name">
                    <p>직책</p>
                </div>
                <div class="a-value">
                    <select id="position" class="position">
                        <option value="" selected hidden>선택</option>
                        <%-- TODO: controller에서 postion 테이블 bean으로 끌어다와서 modelAttribute로 추가 후 아래와 같이 사용 --%>
                        <%-- <c:forEach>
                            <option value="{model이름.실제값(db에 저장되는값)}" selected hidden>{model이름.라벨(보여질 실제이름)}</option>
                        </c:forEach> --%>
                    </select>
                </div>
            </div>
            <div class="a-item">
                <div class="a-name">
                    <p>회장단 기수</p>
                </div>
                <div class="a-value">
                    <input type="text" id="nth" class="full width" placeholder="기수">
                    <hr class="noshow">
                    <div class="checkbox">
                        <input type="checkbox" id="active">
                        <label for="active" class="pointer">현재 임기중이라면 체크</label>
                    </div>
                </div>
            </div>
        </div>
        <div style="text-align: center;">
            <button id="addExecBtn" class="agree">추가</button>
            <button id="close">취소</button>
        </div>
    </div>
</body>