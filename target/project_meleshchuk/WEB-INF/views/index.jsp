<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Beauty Salon</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
        </c:forEach>
    </div>
</c:if>


<div class="container">
    <div class="card-header my-3">All Services</div>
    <div class="row">
        <%--        <c:forEach var="service" items="${requestScope.services}">--%>
        <div class="col m-md-3">
            <div class="card" style="width: 21rem">
                <img class="card-img-top" src="${pageContext.request.contextPath}/images/service_img/man_hd_1.jpg"
                     alt="card image cap">
                <div class="card-body row">
                    <div class="col">
                        <h5 class="card-title">Title</h5>
                        <h6 class="card-title">Price: 400$</h6>
                        <h6 class="card-text">Description</h6>
                    </div>
                </div>
                <div class="card-body row justify-content-between" style="padding-top: 0">
                    <div class="col text-left">
                        <h5>Specialists: </h5>
                        <h6 class="card-text">Yulia</h6>
                        <h6 class="card-text">Regina</h6>
                    </div>
                    <div class="col text-right">
                        <h5>Rating: </h5>
                        <div class="d-inline-flex">
                            <h6 class="card-text px-xl-1">4.2</h6>
                            <img class="mx-auto" src="${pageContext.request.contextPath}/images/2b50.png"
                                 alt="rate-img2" height="19">
                        </div>
                        <div class="d-flex justify-content-end">
                            <div>
                                <h6 class="card-text px-xl-1">4.5</h6>
                            </div>
                            <div>
                                <img class="mx-auto" src="${pageContext.request.contextPath}/images/2b50.png"
                                     alt="rate-img" height="19">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--        </c:forEach>--%>
    </div>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
