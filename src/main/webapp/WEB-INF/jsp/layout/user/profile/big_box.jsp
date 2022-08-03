<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/user/big_profile.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/seperateContent.css"/>">

    <script type="module" src="<c:url value="/resources/js/user/big_profile.js"/>"></script>
</head>

<c:if test="${standAlone ne true}">
    <body id="task">
</c:if>
<c:if test="${standAlone eq true}">
    <div class="content">
    <div class="pagedesc">
        <h2 class="title" style="font-size: 1.5em">프로필</h2>
    </div>
    <div id="TaskArea" class="box">
</c:if>

        <input id="userIndex" value="${userInfo.user_idx}" hidden>
        <div class="sepContainer box-content">
            <div class="seperator box border">
                <form id="mainInfo" class="userInfo" action="/user/modifyProfilePic" method="POST">
                    <div class="profilePic image-wrapper">
                        <c:if test="${userInfo.user_idx eq -1}">
                            <img src="/resources/css/images/default.jpg">
                        </c:if>
                        <c:if test="${userInfo.user_idx ne -1}">
                            <img src="/profile/${userInfo.user_idx}/120.jpg">
                        </c:if>
                        <sec:authorize access="principal.user_idx eq ${userInfo.user_idx}">
                            <div class="redFilter">
                                <span id="deleteProfilePic">
                                    삭제
                                </span>
                            </div>
                            <div class="blackFilter">
                                <span>
                                    편집
                                </span>
                                <label id="tool" for="profilePic">
                                    <input type="file" id="profilePic" name="profilePic" hidden>
                                </label>
                            </div>
                        </sec:authorize>
                    </div>
                    <div id="briefProfile">
                        <div style="font-size: larger; font-weight: bolder;">
                            <c:out value="${userInfo.term}" />기 <c:out value="${userInfo.name}" />
                        </div>
                        <div style="font-size: large;">
                            <c:out value="${userInfo.major_string}" />
                        </div>
                        <div style="font-size: large;">
                            <c:out value="${userInfo.status_string}" />
                        </div>
                    </div>
                </form>
                <div style="display: flex; flex-direction: row-reverse; justify-content: space-between;">
                    <sec:authorize access="principal.user_idx eq ${userInfo.user_idx}">
                        <div id="editBtn" class="userInfo noselect">
                            편집
                        </div>
                    </sec:authorize>
                    <div id="subInfo" class="userInfo display">
                        <div id="company">
                            <i class="fas fa-user-friends"></i>
                            <span><c:out value="${userInfo.company}" /></span>
                        </div>
                        <div id="email">
                            <i class="fas fa-envelope"></i>
                            <span><a href="mailto:" + <c:out value="${userInfo.email_addr}" />>
                                    <c:out value="${userInfo.email_addr}" />
                            </a></span>
                        </div>
                        <div id="website">
                            <i class="fas fa-link"></i>
                            <span><a href=<c:out value="${userInfo.website}" />>
                                <c:out value="${userInfo.website}" />
                            </a></span>
                        </div>
                    </div>
                    <sec:authorize access="principal.user_idx eq ${userInfo.user_idx}">
                    <div id="editSubInfo" class="userInfo editor hidden">
                        <div id="company">
                            <i class="fas fa-user-friends"></i>
                            <input type="text" id="newCompany" placeholder="소속" value="<c:out value="${userInfo.company}" />">
                        </div>
                        <div id="email">
                            <i class="fas fa-envelope"></i>
                            <span><c:out value="${userInfo.email_addr}" /></span>
                        </div>
                        <div id="website">
                            <i class="fas fa-link"></i>
                            <input type="text" id="newWebsite" placeholder="웹사이트" value="<c:out value="${userInfo.website}" />">
                        </div>
                    </div>
                    </sec:authorize>
                </div>
                <div id="intro" class="userInfo display">
                    <c:if test="${noInformation eq true}">
                        탈퇴 혹은 휴면 중인 회원입니다.
                    </c:if>
                    <pre><c:out value="${userInfo.intro}" /></pre>    
                </div>
                <sec:authorize access="principal.user_idx eq ${userInfo.user_idx}">
                <div id="editIntro" class="userInfo editor hidden">
                    <textarea id="newIntro" placeholder="자기소개"><c:out value="${userInfo.intro}" /></textarea>
                </div>
                <div class="userInfo">
                    <input type="button" id="submitEdit" class="hidden agree" value="저장">
                    <input type="button" id="abortEdit" class="hidden" value="취소">
                </div>
                </sec:authorize>
            </div>
            <div id="userEvent" class="seperator box border">
                <c:forEach var="event" items="${eventList}">
                    <div class="eventContainer box-content">
                        <div class="event main">
                            <div class="event title">
                                <c:out value="${event.title}" />
                            </div>
                            <div class="event date">
                                <span class="event beginDate"><c:out value="${event.begin_date}"/></span>
                                <span class="event endDate"><c:out value="~ ${event.end_date}"/></span>
                            </div>
                        </div>
                        <div class="event desc">
                            <c:out value="${event.event_desc}" />
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    
<c:if test="${standAlone ne true}">
</body>
</c:if>
<c:if test="${standAlone eq true}">
    </div>
</div>
</c:if>