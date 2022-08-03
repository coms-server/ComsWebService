<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="frag_profile" OnClick="location.href='/user/profile/${userInfo.user_idx}'">
    <div class="profilePic image-wrapper">
        <sec:authorize access="isAuthenticated()">
            <c:if test="${userInfo.user_idx eq -1}">
                <img src="<c:url value="/resources/css/images/default.jpg"/>">
            </c:if>
            <c:if test="${userInfo.user_idx ne -1}">
                <img src="/profile/${userInfo.user_idx}/40.jpg">
            </c:if>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <img src="<c:url value="/resources/css/images/default.jpg"/>">
        </sec:authorize>
    </div>
    <div class="name">
        <c:out value="${userInfo.term}" />기 <c:out value="${userInfo.name}" /><br>
    </div>
</div>