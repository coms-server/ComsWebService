<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="ko">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script type="module" src="<c:url value="/resources/js/admin/user_selector.js"/>"></script>
</head>

<body>
<div id="modalTable" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">회원 선택</h5>
            </div>
            <div class="modal-body">
                <table id="table"
                       data-toggle="table"
                       data-thead-classes="thead-dark"
                       data-buttons-align="left"
                       data-sticky-header="true"
                       data-sticky-header-offset-left="5"
                       data-sticky-header-offset-right="5"

                       data-search="true"
                       data-search-align="left"
                            data-search-time-out="100"
                            data-visible-search="true"
                                            
                            data-id-field="user_idx"
                            data-select-item-name="user_idx"
                            data-checkbox-header="true"
                            data-click-to-select="true"
                            data-maintain-meta-data="true"

                            data-show-multi-sort="true"
                            data-sort-priority='[{"sortName": "state","sortOrder":"desc"},{"sortName":"term","sortOrder":"desc"}]'

                            data-pagination="true"
                            data-page-list="[10, 20, 30, 40, 50, All]"
                            data-pagination-pre-text="이전"
                            data-pagination-next-text="다음"
                            >
                            <thead>
                                <tr>
                                <th data-checkbox="true" data-sortable="true" data-field="state" ></th>
                                <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="user_idx" data-class="hidden">UserIndex</th>
                                <th data-align="center" data-sortable="true"  data-switchable="false" data-field="term">기수</th>
                                <th data-align="center" data-sortable="true"  data-switchable="false" data-field="name">이름</th>
                                <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="major_string">전공</th>
                                <th data-align="center" data-sortable="true"  data-switchable="true"  data-field="status_string">재학상태</th>
                                <th data-align="center" data-sortable="false" data-switchable="true"  data-field="email_addr" data-class="hidden">이메일</th>
                                </tr>
                            </thead>
                            <tbody id="table-data">
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                    <td>
                                        <c:forEach var="idx" items="${selectedList}">
                                            <c:if test="${idx eq user.user_idx}">1</c:if>
                                        </c:forEach>
                                    </td>
                                    <td><c:out value="${user.user_idx}"/></td>
                                    <td><c:out value="${user.term}"/></td>
                                    <td><a href="/user/profile/${user.user_idx}" target="_blank"><c:out value="${user.name}"/></a></td>
                                    <td><c:out value="${user.major_string}"/></td>
                                    <td><c:out value="${user.status_string}"/></td>
                                    <td><c:out value="${user.email_addr}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="close" class="btn btn-secondary">선택후 닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>