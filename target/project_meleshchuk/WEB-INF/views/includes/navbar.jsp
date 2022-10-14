<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
    <div class="navbar-brand">
        <span><img src="${pageContext.request.contextPath}/images/BS_image.png" height="50" alt="BS Logo"></span>

    </div>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/site/home"
                   role="button"><fmt:message key="home"/></a>
            </li>
            <c:if test="${not empty sessionScope.user}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/site/orders"
                       role="button"><fmt:message key="user.orders"/></a>
                </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
            <c:if test="${empty sessionScope.user}">
                <li>
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/site/login"
                       role="button"><fmt:message key="login"/></a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <li>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/site/logout?command=logout"
                       role="button"><fmt:message key="logout"/></a>
                </li>
            </c:if>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown03" role="button" data-bs-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">${sessionScope.locale.getLanguage().toUpperCase()}</a>
                <div class="dropdown-menu" aria-labelledby="dropdown03">
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                        <a class="dropdown-item" href="?lang=${lang}">${lang.toUpperCase()}</a>
                    </c:forEach>
                </div>
            </li>
        </ul>
    </div>

</nav>