<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="ko">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table-sticky-header.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/seperateContent.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table-sticky-header.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table-multiple-sort.min.js"/>"></script>
</head>

<body>

<div class="content">
    <div class="pagedesc">
        <h2 class="title">회원명부</h2>
    </div>
    <div class="box border">
        <table id="table"
               data-toggle="table"
               data-thead-classes="thead-dark"
               data-buttons-align="left"
               data-sticky-header="true"
               data-sticky-header-offset-left="5"
               data-sticky-header-offset-right="5"

               data-show-columns="true"
               data-search="true"
               data-search-align="left"
                    data-search-time-out="100"
                    data-visible-search="true"

                    data-show-multi-sort="true"
                    data-sort-priority='[{"sortName": "term","sortOrder":"desc"}, {"sortName":"name","sortOrder":"asc"}]'

                    data-pagination="true"
                    data-page-list="[10, 20, 30, 40, 50, All]"
                    data-pagination-pre-text="이전"
                    data-pagination-next-text="다음"
                    >
                    <thead>
                        <tr>
                        <th data-align="center" data-sortable="true"  data-switchable="false" data-field="term"         >기수</th>
                        <th data-align="center" data-sortable="true"  data-switchable="false" data-field="name"         >이름</th>
                        <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="major_string" >전공</th>
                        <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="status_string">재학상태</th>
                        <th data-align="center" data-sortable="false" data-switchable="true"  data-field="email_addr"   >이메일</th>
                        </tr>
                    </thead>
                    <tbody id="table-data">
                        <c:forEach var="user" items="${userList}">
                            <tr>
                            <td><c:out value="${user.term}"/></td>
                            <td><a href="/user/profile/${user.user_idx}"><c:out value="${user.name}"/></a></td>
                            <td><c:out value="${user.major_string}"/></td>
                            <td><c:out value="${user.status_string}"/></td>
                            <td><c:out value="${user.email_addr}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>