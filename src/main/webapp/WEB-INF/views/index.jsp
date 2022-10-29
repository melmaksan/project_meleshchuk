<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Home Page</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong>${error}<br>
        </c:forEach>
    </div>
</c:if>

<div class="container-fluid">
    <div class="row">
        <div class="col-2 px-1 bg-light position-fixed" id="sticky-sidebar" style="height: 100%">
            <div class="nav flex-column pl-4 m-1">
                <h5 class="pt-3">Sort:</h5>
                <h6 class="pb-0 pl-2 mb-0">by price</h6>
                <form class="py-0 pl-3 my-1" action="${pageContext.request.contextPath}/site/home">
                    <input type="hidden" name="command" value="asc.price">
                    <button type="submit" class="btn-sm btn-outline-dark py-0" style="border-radius: 6px">ascPrice
                    </button>
                </form>
                <form class="py-0 pl-3 mb-1" action="${pageContext.request.contextPath}/site/home">
                    <input type="hidden" name="command" value="desc.price">
                    <button type="submit" class="btn-sm btn-outline-dark py-0" style="border-radius: 6px">descPrice
                    </button>
                </form>
                <h6 class="pb-0 pl-2 mb-0 ">by title</h6>
                <form class="py-0 pl-3 my-1" action="${pageContext.request.contextPath}/site/home">
                    <input type="hidden" name="command" value="asc.title">
                    <button type="submit" class="btn-sm btn-outline-dark py-0" style="border-radius: 6px">ascTitle
                    </button>
                </form>
                <form class="py-0 pl-3 mb-1" action="${pageContext.request.contextPath}/site/home">
                    <input type="hidden" name="command" value="desc.title">
                    <button type="submit" class="btn-sm btn-outline-dark py-0" style="border-radius: 6px">descTitle
                    </button>
                </form>
                <h5 class="pt-2">Filter:</h5>
                <div class="dropdown py-0 pl-3 mb-3">
                    <button class="btn-sm btn-outline-dark dropdown-toggle" id="dropdown02" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false" style="border-radius: 6px">
                        by service
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdown02">
                        <c:forEach var="type" items="${requestScope.serviceTypes}">
                            <form class="mb-1" action="${pageContext.request.contextPath}/site/home">
                                <input type="hidden" name="command" value="filter.service"/>
                                <input type="hidden" name="serviceType" value="${type}"/>
                                <button class="dropdown-item" type="submit">${type}</button>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-10 offset-2" id="main">
            <div class="container-fluid">
                <div class="card-header mx-auto mt-3 mb-0"><h4>All Services</h4></div>
                <div class="row mx-auto">
                    <c:forEach var="service" items="${requestScope.services}">
                        <div class="col my-4">
                            <div class="card mx-auto" id="serviceCard">
                                <img class="card-img-top" src="${pageContext.request.contextPath}${service.image}"
                                     alt="card image2">
                                <div class="card-body row ">
                                    <div class="col-8 text-left ">
                                        <h5 class="card-title ">${service.title}</h5>
                                        <h6 class="card-title">${service.price}₴</h6>
                                        <h6 class="card-text">${service.description}</h6>
                                    </div>
                                    <div class="col-4 text-right ">
                                        <c:if test="${empty sessionScope.user}">
                                            <form class="mb-0" action="${pageContext.request.contextPath}/site/login">
                                                <button type="submit" class="btn btn-outline-info">Book</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sessionScope.user.user}">
                                            <form action="${pageContext.request.contextPath}/site/user/confirmation">
                                                <input type="hidden" name="serviceId" value="${service.id}"/>
                                                <button type="submit" class="btn btn-outline-info">Book</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sessionScope.user.admin}">
                                            <form action="${pageContext.request.contextPath}/site/home" method="post">
                                                <input type="hidden" name="command" value="delete.service"/>
                                                <input type="hidden" name="serviceId" value="${service.id}"/>
                                                <button type="submit" class="btn btn-outline-danger"
                                                        data-toggle="confirmation2" data-singleton="true"
                                                        data-popout="true" data-btn-ok-label="Continue"
                                                        data-btn-cancel-label="Cancel" data-title="Are you sure?"
                                                        data-content="You won't be able to return it">
                                                    Delete
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="card-body row justify-content-between py-0">
                                    <div class="col text-left">
                                        <h5>Specialists: </h5>
                                    </div>
                                    <div class="col text-right">
                                        <h5>Rating: </h5>
                                    </div>
                                </div>
                                <c:forEach var="user" items="${service.users}">
                                    <div class="card-body row justify-content-between py-0">
                                        <div class="col text-left">
                                            <h6 class="card-text">${user.firstName}</h6>
                                        </div>
                                        <div class="col text-right">
                                            <div class="d-inline-flex">
                                                <h6 class="card-text px-1">${user.rating}</h6>
                                                <img class="mx-auto"
                                                     src="${pageContext.request.contextPath}/images/rate.png"
                                                     alt="rate-img2" height="18">
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
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
