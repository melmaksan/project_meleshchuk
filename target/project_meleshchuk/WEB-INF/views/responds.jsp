<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="../../resources/index.css"/>
    <title>Responds Page</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
<br>

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong>${error}<br>
        </c:forEach>
    </div>
</c:if>

<div class="container-fluid">
    <div class="row">
        <c:if test="${sessionScope.user.user}">
        <div class="col-1 px-1 mx-1 position-fixed">
            <div class="nav flex-column pl-5 mx-1">
                <a class="btn btn-outline-light mb-1" href="${pageContext.request.contextPath}/site/user/create_respond"
                   role="button" id="respButton">Create respond</a>
                <form class="my-1" action="${pageContext.request.contextPath}/site/responds">
                    <input type="hidden" name="command" value="filter.respond"/>
                    <button class="btn btn-outline-light" type="submit" id="respButton">Show my responds</button>
                </form>
            </div>
        </div>
        </c:if>
        <div class="col-9 offset-2" id="respond">
            <div class="container-fluid">
                <div class="card-header text-center mx-auto mt-3 mb-0 bg-white"><h2>Responds</h2></div>
                <div class="row mx-auto">
                    <c:forEach var="respond" items="${requestScope.responds}">
                        <div class="col my-4">
                            <div class="card mx-auto" id="respondCard" style="border-radius: 10px">
                                <h4 class="card-title px-3 mt-3 ml-1">${respond.userName}</h4>
                                <div class="card-body row py-1">
                                    <div class="col-8 align-self-center">
                                        <h6 class="text-muted my-0 ">${respond.dateTime}</h6>
                                    </div>
                                    <div class="col-4 align-self-center text-right">
                                        <c:choose>
                                            <c:when test="${respond.mark == 3}">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star-o"></i>
                                                <i class="fa fa-star-o"></i>
                                            </c:when>
                                            <c:when test="${respond.mark == 4}">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star-o"></i>
                                            </c:when>
                                            <c:when test="${respond.mark == 5}">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="card-body row py-1">
                                    <div class="col-12 py-2">
                                        <p class="card-text">${respond.respond}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

