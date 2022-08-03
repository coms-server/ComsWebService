<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="ko">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>

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

    <script type="module" src="<c:url value="/resources/js/admin/event_manager.js"/>"></script>

    <!--   캘린더 모듈    -->
    <link rel="stylesheet" href="<c:url value="/resources/css/event.css"/>">

    <script type="module" src="<c:url value="/resources/js/calendar.js"/>"></script>
    <script src="<c:url value="/resources/js/moment.min.js"/>"></script>

    <title>COM'S 행사목록</title>
</head>

<body>
<div class="content">
    <div class="pagedesc">
        <h2 class="title">행사목록</h2>
    </div>

    <div class="box event">
        <div id="calendar"></div>
    </div>

    <div id="task" class="box border">
        <table id="event-table"
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
                        data-sort-priority='[{"sortName": "begin_date","sortOrder":"desc"}, {"sortName":"title","sortOrder":"asc"}]'

                        data-pagination="true"
                        data-page-list="[10, 20, 30, 40, 50, 100, All]"
                        data-pagination-pre-text="이전"
                        data-pagination-next-text="다음"
                        >
                        <thead>
                            <tr>
                            <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="event_idx" data-class="hidden">EventIndex</th>
                            <th data-align="center" data-sortable="true"  data-switchable="false" data-field="title">제목</th>
                            <th data-align="center" data-sortable="false" data-switchable="true"  data-field="event_desc" data-class="hidden">설명</th>
                            <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="begin_date">시작</th>
                            <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="end_date">종료</th>
                            <th data-align="center" data-sortable="true"  data-switchable="false" data-field="is_important">중요함</th>
                            <th data-align="center" data-sortable="false" data-switchable="true"  data-field="url">안내글</th>
                            <th data-align="center" data-sortable="false" data-switchable="false" data-field="operation">작업</th>
                            </tr>
                        </thead>
                        <tbody id="event-table-data">
                            <c:forEach var="event" items="${eventList}">
                                <tr>
                                <td><c:out value="${event.event_idx}"/></td>
                                <td><c:out value="${event.title}"/></td>
                                <td><c:out value="${event.event_desc}"/></td>
                                <td class="date"><c:out value="${event.begin_date}"/></td>
                                <td class="date"><c:out value="${event.end_date}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${event.is_important eq true}">
                                            O
                                        </c:when>
                                        <c:otherwise>
                                            X
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="<c:out value='${event.url}'/>" target="_blank"><c:out value="${event.url}"/></a></td>
                                <td>
                                    <div class="btn-group-vertical">
                                        <button class="btn btn-secondary participant" data-toggle="modal" data-target="#participantModal">
                                            <i class="fa fa-users" aria-hidden="true"></i> 참여자 목록
                                            <input class="event-idx-holder" value="${event.event_idx}" hidden>
                                        </button>
                                        <%-- 유저 참여버튼 --%>
                                        <%-- <button class="btn btn-secondary join">
                                            <i class="far fa-calendar-check"></i> 참여
                                            <input class="event-idx-holder" value="${event.event_idx}" hidden>
                                            <input class="user-idx-holder" value="${userIndex}" hidden>
                                        </button> --%>
                                    </div>
                                </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
            </div>
            
            <div class="modal fade" id="participantModal" tabindex="-1" role="dialog"
                aria-labelledby="participantModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="participantModalLabel">참가자</h5>
                        </div>
                        <div class="modal-body">
                            <div id="participant-table"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                data-dismiss="modal">닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>