<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/separateContent.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/selectable.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/css/fontawesome/all.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/table/bootstrap-table-sticky-header.min.css"/>">

    <script src="<c:url value="/resources/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table-sticky-header.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/table/bootstrap-table-multiple-sort.min.js"/>"></script>

    <script type="module" src="<c:url value="/resources/js/admin/admin.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/user_manager.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/executive_editor.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/event_manager.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/event/event_editor.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/notification_sender.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/event/new_event_form.js"/>"></script>
    <script type="module" src="<c:url value="/resources/js/admin/new_executive_form.js"/>"></script>
</head>

<body>
<div class="content noselect">
    <div class="pagedesc">
        <h2 class="title">????????? ?????????</h2>
        <p class="desc">???????????? ?????? ??? ?????? ??????</p>
    </div>
    <div class="information box">
        <h3><b>????????????</b></h3>
        <p>
            ??????????????? ????????? ???????????? ?????? ??? 8??? 1?????? ?????? <b>?????????????????????????????? ????????? ?????? ????????? 1?????? ??????</b>?????? ?????????, ??? ??????????????? ??????/????????? ????????? ??????
            <b>????????? ????????????????????? ???????????? ??????</b>??? ??????????????????. ???????????? ????????? ???????????? ??????????????? <b>???????????? ?????????????????? ??????????????? ?????? ??????</b>??? ??? ????????????. ?????????
            ??????????????? ???????????? ?????? ?????? ????????? ????????? <b>???????????? ????????? ????????? ??????</b>????????? ????????????.<br>
        </p><br>

        <h3><b>????????????</b></h3>
        <p>
            ??????????????? ???????????? <b>??????, ??????, ????????????, ???????????? ??? ??????</b>?????? ????????? ???????????? ?????? ?????????????????????. ????????? ????????? ?????????, <b>?????????????????? ??????</b>?????? ?????????
            ????????? ??? ???????????????. ?????? ????????? ??????, ??????????????? ?????? <b>????????? ????????? ?????? ???????????? ??????</b>????????? ?????????.<br>
        </p><br>

        <h3><b>???????????????</b></h3>
        <p>
            ????????? ????????? ????????? ???????????? ?????? ??????????????? ????????? ??????/????????? ??? ??? ????????????.<br>
        </p>
        <hr class="noshow">
        <p>
            <b>????????? ????????? ???????????? ?????? ??????...</b><br>
            &lt;????????????&gt; ????????? ????????????. <i class="fas fa-arrow-right"></i> ???????????? ?????? ????????? ?????? ??? ????????? ???????????? ????????? ????????? ????????? ?????????
            ??????
            &lt;?????? ?????????????????? ??????&gt;??? ???????????????. <i class="fas fa-arrow-right"></i> &lt;??????&gt;????????? ????????????. <i
                class="fas fa-arrow-right"></i> &lt;??? ?????????&gt; ???????????? ????????? ????????? ?????? &lt;?????? ?????????????????? ??????&gt;??? ?????????????????????.
        </p>
    </div>
    <div id="TaskArea" class="box">
        <div class="box-content">
            <ul class="nav nav-tabs" id="adminTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <a class="nav-link active" id="user-tab" data-toggle="tab" href="#user" role="tab"
                       aria-controls="userManager" aria-selected="true">?????? ??????</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="event-tab" data-toggle="tab" href="#event" role="tab"
                       aria-controls="eventManager" aria-selected="false">?????? ??????</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="exec-tab" data-toggle="tab" href="#exec" role="tab"
                       aria-controls="execManager" aria-selected="false">?????? ??????</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="noti-tab" data-toggle="tab" href="#noti" role="tab" aria-controls="noti"
                       aria-selected="false">?????? ??????</a>
                </li>
            </ul>
            <div class="tab-content" id="task">
                <div class="tab-pane content fade show active" id="user" role="tabpanel" aria-labelledby="user-tab">

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                ???????????????
                            </h3>
                        </div>
                        <div class="box-content">
                            <c:if test="${pendingUserList.size() ne 0}">
                                <table id="pending-table" data-toggle="table" data-thead-classes="thead-dark"
                                       data-buttons-align="left" data-sticky-header="true"
                                       data-sticky-header-offset-left="5" data-sticky-header-offset-right="5"
                                       data-show-columns="true" data-search="true" data-search-align="left"
                                       data-search-time-out="100" data-visible-search="true"
                                       data-show-multi-sort="true"
                                       data-sort-priority='[{"sortName": "term","sortOrder":"desc"}, {"sortName":"name","sortOrder":"asc"}]'
                                       data-pagination="true" data-page-list="[10, 20, 30, 40, 50, 100, All]"
                                       data-pagination-pre-text="??????" data-pagination-next-text="??????">
                                    <thead>
                                    <tr>
                                        <th data-align="center" data-sortable="true" data-switchable="false"
                                            data-field="term">
                                            <div class="min-header">??????</div>
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="false"
                                            data-field="name">
                                            <div class="min-header">??????</div>
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="major_string">??????
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="status_string">????????????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="birth">??????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="email_addr" data-class="hidden">?????????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="phone_num">????????????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="home_addr" data-class="hidden">??????
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="email_send" data-class="hidden">????????????
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="sms_send" data-class="hidden">SMS??????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="join_date">????????????
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="false"
                                            data-field="operation">
                                            <div class="min-header">??????</div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody id="pending-table-data">
                                    <c:forEach var="user" items="${pendingUserList}">
                                        <tr>
                                            <td>
                                                <c:out value="${user.term}"/>
                                            </td>
                                            <td><a href="/user/profile/${user.user_idx}">
                                                <c:out value="${user.name}"/></a></td>
                                            <td>
                                                <c:out value="${user.major_string}"/>
                                            </td>
                                            <td>
                                                <c:out value="${user.status_string}"/>
                                            </td>
                                            <td>
                                                <c:out value="${user.birth}"/>
                                            </td>
                                            <td>
                                                <c:out value="${user.email_addr}"/>
                                            </td>
                                            <td>
                                                <c:out value="${user.phone_num}"/>
                                            </td>
                                            <td>
                                                <c:out value="${user.home_addr}"/>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.email_send eq true}">
                                                        O
                                                    </c:when>
                                                    <c:otherwise>
                                                        X
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.sms_send eq true}">
                                                        O
                                                    </c:when>
                                                    <c:otherwise>
                                                        X
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:out value="${user.join_date}"/>
                                            </td>
                                            <td>
                                                <div class="btn-group-vertical">
                                                    <button class="btn btn-secondary grant">
                                                        <i class="fa fa-check" aria-hidden="true"></i> ??????
                                                    </button>
                                                    <input class="user-idx-holder" value="${user.user_idx}"
                                                           hidden>
                                                    <button class="btn btn-secondary deny">
                                                        <i class="fa fa-times" aria-hidden="true"></i> ??????
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                    </div>

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                ????????????
                            </h3>
                        </div>
                        <div class="box-content">
                            <table id="main-table" data-toggle="table" data-thead-classes="thead-dark"
                                   data-buttons-align="left" data-sticky-header="true"
                                   data-sticky-header-offset-left="5" data-sticky-header-offset-right="5"
                                   data-show-columns="true" data-search="true" data-search-align="left"
                                   data-search-time-out="100" data-visible-search="true" data-show-multi-sort="true"
                                   data-sort-priority='[{"sortName": "term","sortOrder":"desc"}, {"sortName":"name","sortOrder":"asc"}]'
                                   data-pagination="true" data-page-list="[10, 20, 30, 40, 50, 100, All]"
                                   data-pagination-pre-text="??????" data-pagination-next-text="??????">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="user_idx" data-class="hidden">UserIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="term">
                                        <div class="min-header">??????</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">??????</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="major_string">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="status_string">????????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="birth" data-class="hidden">??????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="email_addr" data-class="hidden">?????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="phone_num">????????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="home_addr" data-class="hidden">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="email_send" data-class="hidden">????????????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="sms_send" data-class="hidden">SMS??????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="join_date" data-class="hidden">????????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">??????</div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="main-table-data">
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>
                                            <c:out value="${user.user_idx}"/>
                                        </td>
                                        <td>
                                            <c:out value="${user.term}"/>
                                        </td>
                                        <td><a id="userName" href="/user/profile/${user.user_idx}">
                                            <c:out value="${user.name}"/></a></td>
                                        <td>
                                            <c:out value="${user.major_string}"/>
                                        </td>
                                        <td id="userStatusString">
                                            <c:out value="${user.status_string}"/>
                                        </td>
                                        <td>
                                            <c:out value="${user.birth}"/>
                                        </td>
                                        <td>
                                            <c:out value="${user.email_addr}"/>
                                        </td>
                                        <td>
                                            <c:out value="${user.phone_num}"/>
                                        </td>
                                        <td>
                                            <c:out value="${user.home_addr}"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.email_send eq true}">
                                                    O
                                                </c:when>
                                                <c:otherwise>
                                                    X
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.sms_send eq true}">
                                                    O
                                                </c:when>
                                                <c:otherwise>
                                                    X
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:out value="${user.join_date}"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-secondary modify" data-toggle="modal"
                                                    data-target="#userEditModal">
                                                <i class="fa fa-cogs" aria-hidden="true"></i> ??????
                                            </button>
                                            <input class="user-idx-holder" value="${user.user_idx}" hidden>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <button class="btn btn-secondary" type="button" id="download"><i class="fa fa-download"></i>
                    </button>

                    <%--Modal Content--%>
                    <div class="modal fade" id="userEditModal" tabindex="-1" role="dialog"
                         aria-labelledby="userEditModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="userEditModalLabel">?????? ??????</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="user_idx" hidden>
                                                <p></p>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>????????????</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="prevStatus" hidden>
                                                <select id="modi-status" class="status">
                                                    <option value="" selected hidden>??????</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>???????????? ?????????</p>
                                            </div>
                                            <div class="a-value">
                                                <button id="resetPassword" class="btn btn-danger" value="?????????"><i
                                                        class="fa fa-retweet" aria-hidden="true"></i>?????????
                                                </button>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>????????????</p>
                                            </div>
                                            <div class="a-value">
                                                <button id="extractUser" class="btn btn-danger" value="????????????"><i
                                                        class="fas fa-exclamation-triangle"></i>????????????
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" id="modiUser" class="btn btn-primary">??????</button>
                                        <button type="button" id="closeUserEdit" class="btn btn-secondary"
                                                data-dismiss="modal">
                                            ??????
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="tab-pane content fade" id="event" role="tabpanel" aria-labelledby="event-tab">

                    <button type="button" class="btn btn-secondary" id="add-event" data-toggle="modal"
                            data-target="#addEventModal">
                        <i class="fa fa-bolt"> ?????? ??????</i>
                    </button>

                    <%-- Modal Content --%>
                    <div class="modal fade" id="addEventModal" tabindex="-1" role="dialog"
                         aria-labelledby="addEventModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addEventModalLabel">?????? ??????</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <input type="text" id="eTitle" class="full width" placeholder="??????"
                                                       required>
                                                <hr class="noshow">
                                                <textarea id="eDesc" class="full width" placeholder="??????"
                                                          required></textarea>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <div class="dateArea">
                                                    <input type="datetime-local" id="eBeginDate"
                                                           max="2099-12-31T00:00:00" required>
                                                    <hr class="noshow">
                                                    <input type="datetime-local" id="eEndDate"
                                                           max="2099-12-31T00:00:00" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>?????????</p>
                                            </div>
                                            <div class="a-value">
                                                <input type="text" id="url" class="full width" placeholder="URL">
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>???????????? ??????</p>
                                            </div>
                                            <div class="a-value">
                                                <div class="checkbox">
                                                    <input type="checkbox" id="eIsImportant">
                                                    <label for="eIsImportant" class="pointer">????????????</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="addEventBtn" class="btn btn-primary">??????</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">??????</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header">
                            <h3>
                                ????????????
                            </h3>
                        </div>
                        <div class="box-content">
                            <table id="event-table" data-toggle="table" data-thead-classes="thead-dark"
                                   data-buttons-align="left" data-sticky-header="true"
                                   data-sticky-header-offset-left="5" data-sticky-header-offset-right="5"
                                   data-show-columns="true" data-search="true" data-search-align="left"
                                   data-search-time-out="100" data-visible-search="true" data-show-multi-sort="true"
                                   data-sort-priority='[{"sortName": "begin_date","sortOrder":"desc"}, {"sortName":"title","sortOrder":"asc"}]'
                                   data-pagination="true" data-page-list="[10, 20, 30, 40, 50, 100, All]"
                                   data-pagination-pre-text="??????" data-pagination-next-text="??????">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="event_idx" data-class="hidden">EventIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="title">??????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="event_desc" data-class="hidden">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="begin_date">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="end_date">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="is_important">?????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="url">?????????
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">??????</div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="event-table-data">
                                <c:forEach var="event" items="${eventList}">
                                    <tr>
                                        <td>
                                            <c:out value="${event.event_idx}"/>
                                        </td>
                                        <td>
                                            <c:out value="${event.title}"/>
                                        </td>
                                        <td>
                                            <c:out value="${event.event_desc}"/>
                                        </td>
                                        <td class="date">
                                            <c:out value="${event.begin_date}"/>
                                        </td>
                                        <td class="date">
                                            <c:out value="${event.end_date}"/>
                                        </td>
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
                                        <td><a href="<c:out value='${event.url}'/>">
                                            <c:out value="${event.url}"/></a></td>
                                        <td>
                                            <div class="btn-group-vertical">
                                                <!-- Button trigger modal -->
                                                <button type="button" class="btn btn-secondary participant"
                                                        data-toggle="modal" data-target="#participantModal">
                                                    <i class="fa fa-users" aria-hidden="true"></i> ?????????
                                                </button>
                                                <input class="event-idx-holder" value="${event.event_idx}"
                                                       hidden>
                                                <c:if test="${adminMode eq true}">
                                                    <button type="button" class="btn btn-secondary modify"
                                                            data-toggle="modal" data-target="#eventEditModal">
                                                        <i class="fa fa-cogs" aria-hidden="true"></i> ??????
                                                    </button>
                                                    <button class="btn btn-secondary delete">
                                                        <i class="fa fa-times" aria-hidden="true"
                                                           style="color:red"></i> ??????
                                                    </button>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <%-- Modal Content --%>

                        <div class="modal fade" id="eventEditModal" tabindex="-1" role="dialog"
                             aria-labelledby="eventEditModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="eventEditModalLabel">?????? ??????</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="a-info">
                                            <input id="event_idx" hidden>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>??????</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <input type="text" id="event_title" class="full width"
                                                           placeholder="??????" required>
                                                    <hr class="noshow">
                                                    <textarea id="event_desc" class="full width" placeholder="??????"
                                                              required></textarea>
                                                </div>
                                            </div>

                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>??????</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <div class="dateArea">
                                                        <input type="datetime-local" id="event_beginDate"
                                                               class="full width" max="2099-12-31" required>
                                                        <hr class="noshow">
                                                        <input type="datetime-local" id="event_endDate"
                                                               class="full width" max="2099-12-31" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>?????????</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <input type="text" id="event_url" class="full width"
                                                           placeholder="URL">
                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>???????????? ??????</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                        <input type="text" id="prevIsImportant" hidden>
                                                        <label class="btn btn-secondary active pointer">
                                                            <input type="checkbox"
                                                                   id="event_isImportant">????????????</label>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                </div>

                                                <div class="a-value edit">
                                                    <div id="userSelector">
                                                        <button type="button" class="btn btn-primary btn-bg"
                                                                data-toggle="modal" data-target="#modalTable">
                                                            ?????? ??????
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>????????????</p>
                                                </div>

                                                <div class="a-value participant">
                                                    <input class="userList" hidden>
                                                    <div class="userNames">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" id="saveBtn" class="btn btn-primary">??????</button>
                                        <button type="button" id="closeEventEdit" class="btn btn-secondary"
                                                data-dismiss="modal">??????
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="participantModal" tabindex="-1" role="dialog"
                             aria-labelledby="participantModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="participantModalLabel">?????????</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div id="participant-table"></div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">??????
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane content fade" id="exec" role="tabpanel" aria-labelledby="exec-tab">

                    <button type="button" id="add-exec" class="btn btn-secondary" data-toggle="modal"
                            data-target="#execAddModal">
                        <i class="fa fa-bolt"> ????????? ??????</i>
                    </button>

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                ??? ?????????
                            </h3>
                        </div>
                        <div class="box-content">
                            <table id="active-table" data-toggle="table" data-thead-classes="thead-dark"
                                   data-buttons-align="left" data-sticky-header="true"
                                   data-sticky-header-offset-left="5" data-sticky-header-offset-right="5"
                                   data-show-columns="true" data-search="true" data-search-align="left"
                                   data-search-time-out="100" data-visible-search="true" data-show-multi-sort="true"
                                   data-sort-priority='[{"sortName": "term","sortOrder":"desc"}, {"sortName":"name","sortOrder":"asc"}]'
                                   data-pagination="true" data-page-list="[10, 20, 30, 40, 50, 100, All]"
                                   data-pagination-pre-text="??????" data-pagination-next-text="??????">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="exec_idx" data-class="hidden">ExecIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">??????</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="term">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="position">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">??????</div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="active-table-data">
                                <c:forEach var="exec" items="${activeExecList}">
                                    <tr>
                                        <td>
                                            <c:out value="${exec.exec_idx}"/>
                                        </td>
                                        <td>
                                            <c:out value="${exec.name}"/>
                                        </td>
                                        <td>
                                            <c:out value="${exec.nth}"/>???
                                        </td>
                                        <td class="execPosition">
                                            <c:set value="${exec.position}" var="index"/>
                                            <c:out value="${positionList[index - 1].position}"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-secondary modify"
                                                    data-toggle="modal" data-target="#execEditModal">
                                                <i class="fa fa-cogs" aria-hidden="true"></i> ??????
                                            </button>
                                            <input class="exec-idx-holder" value="${exec.exec_idx}" hidden>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                ??? ?????????
                            </h3>
                        </div>
                        <div class="box-content">
                            <table id="nonactive-table" data-toggle="table" data-thead-classes="thead-dark"
                                   data-buttons-align="left" data-sticky-header="true"
                                   data-sticky-header-offset-left="5" data-sticky-header-offset-right="5"
                                   data-show-columns="true" data-search="true" data-search-align="left"
                                   data-search-time-out="100" data-visible-search="true" data-show-multi-sort="true"
                                   data-sort-priority='[{"sortName": "term","sortOrder":"desc"}, {"sortName":"name","sortOrder":"asc"}]'
                                   data-pagination="true" data-page-list="[10, 20, 30, 40, 50, 100, All]"
                                   data-pagination-pre-text="??????" data-pagination-next-text="??????">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="exec_idx" data-class="hidden">ExecIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">??????</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="term">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="position">??????
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="operation">
                                        <div class="min-header">??????</div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="nonactive-table-data">
                                <c:forEach var="exec" items="${nonActiveExecList}">
                                    <tr>
                                        <td>
                                            <c:out value="${exec.exec_idx}"/>
                                        </td>
                                        <td>
                                            <c:out value="${exec.name}"/>
                                        </td>
                                        <td>
                                            <c:out value="${exec.nth}"/>???
                                        </td>
                                        <td class="execPosition">
                                            <c:set value="${exec.position}" var="index"/>
                                            <c:out value="${positionList[index - 1].position}"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-secondary modify"
                                                    data-toggle="modal" data-target="#execEditModal">
                                                <i class="fa fa-cogs" aria-hidden="true"></i> ??????
                                            </button>
                                            <input class="exec-idx-holder" value="${exec.exec_idx}" hidden>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <%-- Modal Content --%>
                    <div class="modal fade" id="execAddModal" tabindex="-1" role="dialog"
                         aria-labelledby="execAddModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="execAddModalLabel">????????? ??????</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                            </div>
                                            <div class="a-value userSelector">
                                                <button type="button" class="btn btn-primary btn-bg"
                                                        data-toggle="modal" data-target="#modalTable">
                                                    ?????? ??????
                                                </button>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>?????????</p>
                                            </div>

                                            <div class="a-value participant">
                                                <input class="userList" hidden>
                                                <div class="userNames">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <select id="position" class="position">
                                                    <option value="" selected hidden>??????</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>????????? ??????</p>
                                            </div>
                                            <div class="a-value nth-field">
                                                <input type="text" id="nth" class="near right" placeholder="??????">
                                                <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                    <input type="text" id="prevActive" hidden>
                                                    <label class="btn btn-secondary active near left pointer">
                                                        <input type="checkbox" id="active">?????? ?????????
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="addExecBtn" class="btn btn-primary">??????</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">??????</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="execEditModal" tabindex="-1" role="dialog"
                         aria-labelledby="execEditModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="execEditModalLabel">????????? ??????</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <input type="hidden" id="user_idx">
                                                <p></p>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>??????</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="prevPosit" hidden>
                                                <select id="position" class="position">
                                                    <option value="" selected hidden>??????</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>????????? ??????</p>
                                            </div>
                                            <div class="a-value nth-field">
                                                <input type="text" id="nth" class="near right" placeholder="??????">
                                                <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                    <input type="text" id="prevActive" hidden>
                                                    <label class="btn btn-secondary active near left pointer">
                                                        <input type="checkbox" id="active">?????? ?????????
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>????????? ??????</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="exec_idx" hidden>
                                                <button id="extract" class="btn btn-danger" value="??????">??????</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="modiExec" class="btn btn-primary">??????</button>
                                    <button type="button" id="closeExecEdit" class="btn btn-secondary"
                                            data-dismiss="modal">
                                        ??????
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane content fade" id="noti" role="tabpanel" aria-labelledby="noti-tab">
                    <div id="notification" class="seperator box">
                        <div class="box-header">
                            <h3>
                                ?????? ??????
                            </h3>
                        </div>
                        <div class="box-content">
                            <div class="a-info">
                                <div class="a-item">
                                    <div class="a-name">
                                        <p></p>
                                    </div>
                                    <div class="a-value userSelector btn-group-toggle" data-toggle="buttons">
                                        <label class="btn btn-secondary active near right">
                                            <input type="checkbox" id="notiIsGlobal"> ????????????
                                        </label>
                                        <button type="button" class="btn-primary agree btn-bg near left"
                                                data-toggle="modal" data-target="#modalTable">
                                            ?????? ??????
                                        </button>
                                    </div>
                                </div>

                                <div class="a-item">
                                    <div class="a-name">
                                        <p>?????????</p>
                                    </div>

                                    <div class="a-value participant">
                                        <input class="userList" hidden>
                                        <p id="allLabel" class="hidden">?????? ??????</p>
                                        <div class="userNames">
                                        </div>
                                    </div>
                                </div>

                                <div class="a-item">
                                    <div class="a-name">
                                        <p>??????</p>
                                    </div>
                                    <div class="a-value">
                                        <input type="text" id="notiTitle" class="full width" placeholder="????????????">
                                    </div>
                                </div>
                                <div class="a-item">
                                    <div class="a-name">
                                        <p>?????? URL</p>
                                    </div>
                                    <div class="a-value">
                                        <input type="text" id="notiUrl" class="full width" placeholder="?????? ??????">
                                    </div>
                                </div>
                                <hr class="noshow">
                                <div style="text-align: center;">
                                    <input type="button" id="sendNotiBtn" class="agree" value="??????">
                                    <input type="button" value="??????">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--       modal       -->
            <c:import url="/admin/user/selector"/>
        </div>
    </div>
</body>