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
        <h2 class="title">관리자 페이지</h2>
        <p class="desc">회원가입 승인 및 권한 관리</p>
    </div>
    <div class="information box">
        <h3><b>주의사항</b></h3>
        <p>
            개인정보의 안전성 확보조치 기준 제 8조 1항에 따라 <b>개인정보처리시스템에 접속한 모든 기록을 1년간 보관</b>하고 있으며, 이 페이지에서 조회/수정한 행위에 따른
            <b>책임은 개인정보처리자 본인에게 있음</b>을 알려드립니다. 회원관리 이외의 목적으로 개인정보를 <b>무단으로 사용하는경우 관계법령에 따라 처벌</b>될 수 있습니다. 더불어
            개인정보를 처리함에 있어 해당 정보가 외부에 <b>유출되지 않도록 각별히 유의</b>하시기 바랍니다.<br>
        </p><br>

        <h3><b>회원관리</b></h3>
        <p>
            가입신청자 목록에서 <b>이름, 전공, 재학상태, 생년월일 및 기수</b>등의 정보를 확인하여 가입 승인처리합니다. 특별한 사유가 없다면, <b>가입신청자와 대면</b>하여 정보를
            확인한 뒤 승인합니다. 회원 제명의 경우, 임원회의를 거쳐 <b>과반의 동의를 얻은 경우에만 처리</b>하도록 합니다.<br>
        </p><br>

        <h3><b>임원단관리</b></h3>
        <p>
            임원단 인원에 변동이 있을경우 해당 페이지에서 임원단 추가/수정을 할 수 있습니다.<br>
        </p>
        <hr class="noshow">
        <p>
            <b>자신의 직책을 인수인계 하는 경우...</b><br>
            &lt;임원추가&gt; 버튼을 누릅니다. <i class="fas fa-arrow-right"></i> 인수인계 받을 회원을 찾은 뒤 직책을 선택하고 새로운 회장단 기수를 입력한
            다음
            &lt;현재 임기중이라면 체크&gt;를 체크합니다. <i class="fas fa-arrow-right"></i> &lt;추가&gt;버튼을 누릅니다. <i
                class="fas fa-arrow-right"></i> &lt;현 임원단&gt; 목록에서 자신을 선택한 다음 &lt;현재 임기중이라면 체크&gt;를 체크해제합니다.
        </p>
    </div>
    <div id="TaskArea" class="box">
        <div class="box-content">
            <ul class="nav nav-tabs" id="adminTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <a class="nav-link active" id="user-tab" data-toggle="tab" href="#user" role="tab"
                       aria-controls="userManager" aria-selected="true">회원 관리</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="event-tab" data-toggle="tab" href="#event" role="tab"
                       aria-controls="eventManager" aria-selected="false">행사 관리</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="exec-tab" data-toggle="tab" href="#exec" role="tab"
                       aria-controls="execManager" aria-selected="false">임원 관리</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="noti-tab" data-toggle="tab" href="#noti" role="tab" aria-controls="noti"
                       aria-selected="false">알림 발송</a>
                </li>
            </ul>
            <div class="tab-content" id="task">
                <div class="tab-pane content fade show active" id="user" role="tabpanel" aria-labelledby="user-tab">

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                가입신청서
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
                                       data-pagination-pre-text="이전" data-pagination-next-text="다음">
                                    <thead>
                                    <tr>
                                        <th data-align="center" data-sortable="true" data-switchable="false"
                                            data-field="term">
                                            <div class="min-header">기수</div>
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="false"
                                            data-field="name">
                                            <div class="min-header">이름</div>
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="major_string">전공
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="status_string">재학상태
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="birth">생일
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="email_addr" data-class="hidden">이메일
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="phone_num">전화번호
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="home_addr" data-class="hidden">주소
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="email_send" data-class="hidden">메일수신
                                        </th>
                                        <th data-align="center" data-sortable="true" data-switchable="true"
                                            data-field="sms_send" data-class="hidden">SMS수신
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="true"
                                            data-field="join_date">가입날짜
                                        </th>
                                        <th data-align="center" data-sortable="false" data-switchable="false"
                                            data-field="operation">
                                            <div class="min-header">작업</div>
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
                                                        <i class="fa fa-check" aria-hidden="true"></i> 승인
                                                    </button>
                                                    <input class="user-idx-holder" value="${user.user_idx}"
                                                           hidden>
                                                    <button class="btn btn-secondary deny">
                                                        <i class="fa fa-times" aria-hidden="true"></i> 거부
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
                                회원목록
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
                                   data-pagination-pre-text="이전" data-pagination-next-text="다음">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="user_idx" data-class="hidden">UserIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="term">
                                        <div class="min-header">기수</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">이름</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="major_string">전공
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="status_string">재학상태
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="birth" data-class="hidden">생일
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="email_addr" data-class="hidden">이메일
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="phone_num">전화번호
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="home_addr" data-class="hidden">주소
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="email_send" data-class="hidden">메일수신
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="sms_send" data-class="hidden">SMS수신
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="join_date" data-class="hidden">가입날짜
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">작업</div>
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
                                                <i class="fa fa-cogs" aria-hidden="true"></i> 관리
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
                                    <h5 class="modal-title" id="userEditModalLabel">유저 관리</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>이름</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="user_idx" hidden>
                                                <p></p>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>재학상태</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="prevStatus" hidden>
                                                <select id="modi-status" class="status">
                                                    <option value="" selected hidden>선택</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>비밀번호 초기화</p>
                                            </div>
                                            <div class="a-value">
                                                <button id="resetPassword" class="btn btn-danger" value="초기화"><i
                                                        class="fa fa-retweet" aria-hidden="true"></i>초기화
                                                </button>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>강제탈퇴</p>
                                            </div>
                                            <div class="a-value">
                                                <button id="extractUser" class="btn btn-danger" value="강제탈퇴"><i
                                                        class="fas fa-exclamation-triangle"></i>강제탈퇴
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" id="modiUser" class="btn btn-primary">저장</button>
                                        <button type="button" id="closeUserEdit" class="btn btn-secondary"
                                                data-dismiss="modal">
                                            취소
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
                        <i class="fa fa-bolt"> 행사 추가</i>
                    </button>

                    <%-- Modal Content --%>
                    <div class="modal fade" id="addEventModal" tabindex="-1" role="dialog"
                         aria-labelledby="addEventModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addEventModalLabel">행사 추가</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>개요</p>
                                            </div>
                                            <div class="a-value">
                                                <input type="text" id="eTitle" class="full width" placeholder="제목"
                                                       required>
                                                <hr class="noshow">
                                                <textarea id="eDesc" class="full width" placeholder="설명"
                                                          required></textarea>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>일정</p>
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
                                                    <label for="eIsImportant" class="pointer">필수행사</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="addEventBtn" class="btn btn-primary">추가</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header">
                            <h3>
                                행사목록
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
                                   data-pagination-pre-text="이전" data-pagination-next-text="다음">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="event_idx" data-class="hidden">EventIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="title">제목
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="event_desc" data-class="hidden">설명
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="begin_date">시작
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="end_date">종료
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="is_important">중요함
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="true"
                                        data-field="url">안내글
                                    </th>
                                    <th data-align="center" data-sortable="false" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">작업</div>
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
                                                    <i class="fa fa-users" aria-hidden="true"></i> 참여자
                                                </button>
                                                <input class="event-idx-holder" value="${event.event_idx}"
                                                       hidden>
                                                <c:if test="${adminMode eq true}">
                                                    <button type="button" class="btn btn-secondary modify"
                                                            data-toggle="modal" data-target="#eventEditModal">
                                                        <i class="fa fa-cogs" aria-hidden="true"></i> 수정
                                                    </button>
                                                    <button class="btn btn-secondary delete">
                                                        <i class="fa fa-times" aria-hidden="true"
                                                           style="color:red"></i> 삭제
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
                                        <h5 class="modal-title" id="eventEditModalLabel">행사 수정</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="a-info">
                                            <input id="event_idx" hidden>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>개요</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <input type="text" id="event_title" class="full width"
                                                           placeholder="제목" required>
                                                    <hr class="noshow">
                                                    <textarea id="event_desc" class="full width" placeholder="설명"
                                                              required></textarea>
                                                </div>
                                            </div>

                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>일정</p>
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
                                                    <p>안내글</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <input type="text" id="event_url" class="full width"
                                                           placeholder="URL">
                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>필수참여 여부</p>
                                                </div>
                                                <div class="a-value edit">
                                                    <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                        <input type="text" id="prevIsImportant" hidden>
                                                        <label class="btn btn-secondary active pointer">
                                                            <input type="checkbox"
                                                                   id="event_isImportant">필수행사</label>
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
                                                            회원 선택
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="a-item">
                                                <div class="a-name">
                                                    <p>참여인원</p>
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
                                        <button type="button" id="saveBtn" class="btn btn-primary">저장</button>
                                        <button type="button" id="closeEventEdit" class="btn btn-secondary"
                                                data-dismiss="modal">취소
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
                                        <h5 class="modal-title" id="participantModalLabel">참가자</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div id="participant-table"></div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">닫기
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
                        <i class="fa fa-bolt"> 임원단 추가</i>
                    </button>

                    <div class="box">
                        <div class="box-header">
                            <h3>
                                현 임원단
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
                                   data-pagination-pre-text="이전" data-pagination-next-text="다음">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="exec_idx" data-class="hidden">ExecIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">이름</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="term">기수
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="position">직책
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="operation">
                                        <div class="min-header">작업</div>
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
                                            <c:out value="${exec.nth}"/>기
                                        </td>
                                        <td class="execPosition">
                                            <c:set value="${exec.position}" var="index"/>
                                            <c:out value="${positionList[index - 1].position}"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-secondary modify"
                                                    data-toggle="modal" data-target="#execEditModal">
                                                <i class="fa fa-cogs" aria-hidden="true"></i> 관리
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
                                전 임원단
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
                                   data-pagination-pre-text="이전" data-pagination-next-text="다음">
                                <thead>
                                <tr>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="exec_idx" data-class="hidden">ExecIndex
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="name">
                                        <div class="min-header">이름</div>
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="false"
                                        data-field="term">기수
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="position">직책
                                    </th>
                                    <th data-align="center" data-sortable="true" data-switchable="true"
                                        data-field="operation">
                                        <div class="min-header">기수</div>
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
                                            <c:out value="${exec.nth}"/>기
                                        </td>
                                        <td class="execPosition">
                                            <c:set value="${exec.position}" var="index"/>
                                            <c:out value="${positionList[index - 1].position}"/>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-secondary modify"
                                                    data-toggle="modal" data-target="#execEditModal">
                                                <i class="fa fa-cogs" aria-hidden="true"></i> 관리
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
                                    <h5 class="modal-title" id="execAddModalLabel">임원단 추가</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                            </div>
                                            <div class="a-value userSelector">
                                                <button type="button" class="btn btn-primary btn-bg"
                                                        data-toggle="modal" data-target="#modalTable">
                                                    회원 선택
                                                </button>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>대상자</p>
                                            </div>

                                            <div class="a-value participant">
                                                <input class="userList" hidden>
                                                <div class="userNames">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>직책</p>
                                            </div>
                                            <div class="a-value">
                                                <select id="position" class="position">
                                                    <option value="" selected hidden>선택</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>회장단 기수</p>
                                            </div>
                                            <div class="a-value nth-field">
                                                <input type="text" id="nth" class="near right" placeholder="기수">
                                                <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                    <input type="text" id="prevActive" hidden>
                                                    <label class="btn btn-secondary active near left pointer">
                                                        <input type="checkbox" id="active">현재 임기중
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="addExecBtn" class="btn btn-primary">추가</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="execEditModal" tabindex="-1" role="dialog"
                         aria-labelledby="execEditModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="execEditModalLabel">임원단 관리</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="a-info">
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>이름</p>
                                            </div>
                                            <div class="a-value">
                                                <input type="hidden" id="user_idx">
                                                <p></p>
                                            </div>
                                        </div>

                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>직책</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="prevPosit" hidden>
                                                <select id="position" class="position">
                                                    <option value="" selected hidden>선택</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>회장단 기수</p>
                                            </div>
                                            <div class="a-value nth-field">
                                                <input type="text" id="nth" class="near right" placeholder="기수">
                                                <div class="checkbox btn-group-toggle" data-toggle="buttons">
                                                    <input type="text" id="prevActive" hidden>
                                                    <label class="btn btn-secondary active near left pointer">
                                                        <input type="checkbox" id="active">현재 임기중
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="a-item">
                                            <div class="a-name">
                                                <p>임원단 제명</p>
                                            </div>
                                            <div class="a-value">
                                                <input id="exec_idx" hidden>
                                                <button id="extract" class="btn btn-danger" value="제명">제명</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="modiExec" class="btn btn-primary">저장</button>
                                    <button type="button" id="closeExecEdit" class="btn btn-secondary"
                                            data-dismiss="modal">
                                        취소
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
                                알림 발송
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
                                            <input type="checkbox" id="notiIsGlobal"> 전체발송
                                        </label>
                                        <button type="button" class="btn-primary agree btn-bg near left"
                                                data-toggle="modal" data-target="#modalTable">
                                            회원 선택
                                        </button>
                                    </div>
                                </div>

                                <div class="a-item">
                                    <div class="a-name">
                                        <p>수신자</p>
                                    </div>

                                    <div class="a-value participant">
                                        <input class="userList" hidden>
                                        <p id="allLabel" class="hidden">회원 전체</p>
                                        <div class="userNames">
                                        </div>
                                    </div>
                                </div>

                                <div class="a-item">
                                    <div class="a-name">
                                        <p>제목</p>
                                    </div>
                                    <div class="a-value">
                                        <input type="text" id="notiTitle" class="full width" placeholder="알림제목">
                                    </div>
                                </div>
                                <div class="a-item">
                                    <div class="a-name">
                                        <p>참조 URL</p>
                                    </div>
                                    <div class="a-value">
                                        <input type="text" id="notiUrl" class="full width" placeholder="참조 링크">
                                    </div>
                                </div>
                                <hr class="noshow">
                                <div style="text-align: center;">
                                    <input type="button" id="sendNotiBtn" class="agree" value="발송">
                                    <input type="button" value="닫기">
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