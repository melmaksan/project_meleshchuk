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
<br>

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong>${error}<br>
        </c:forEach>
    </div>
</c:if>

<h1 class="offset-1 my-3">All Specialists</h1>
<table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable4" style="width: 83%">
    <thead class="thead-dark">
    <tr>
        <th class="col-3 " scope="col">Name</th>
        <th class="col-3 " scope="col">Service Type</th>
        <th class="col-1 " scope="col">Rating</th>
        <th class="col-2 " scope="col">Email</th>
        <th class="col-2 " scope="col">Mobile Phone</th>
        <th class="col-1 " scope="col">Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="specialist" items="${requestScope.specialists}">
        <tr>
            <c:if test="${sessionScope.user.admin}">
                <td class="col-3 py-1 align-middle"><c:out value="${specialist.firstName} ${specialist.lastName}"/></td>
                <td class="col-3 py-1 ">
                    <div class="dropdown">
                        <button class="btn" id="dropdown04" data-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false">click on me
                        </button>
                        <div class="dropdown-menu text-center" aria-labelledby="dropdown04"
                             style="padding: 0; width: 80%">
                            <c:set var="data" value="serviceTypes${specialist.id}"/>
                            <c:forEach var="service" items="${requestScope[data]}">
                                <p class="dropdown-item my-0">${service}</p>
                            </c:forEach>
                        </div>
                    </div>
                </td>
                <td class="col-1 py-1 align-middle">
                    <h6 class="my-1">${specialist.rating}
                        <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor"
                             class="bi bi-star align-baseline my-0" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"></path>
                        </svg>
                    </h6>
                </td>
                <td class="col-2 py-1 align-middle">${specialist.login}</td>
                <td class="col-2 py-1 align-middle">${specialist.phoneNumber}</td>
                <td class="col-1 pt-0 pb-1 ">
                    <form class="mt-1 mb-0" action="${pageContext.request.contextPath}/site/admin/specialists" method="post">
                        <input type="hidden" name="command" value="delete.specialist"/>
                        <input type="hidden" name="specId" value="${specialist.id}"/>
                        <button type="submit" class="btn-sm btn-outline-danger py-0"
                                data-toggle="confirmation2" data-singleton="true" data-popout="true"
                                data-btn-ok-label="Continue" data-btn-cancel-label="Cancel"
                                data-title="Are you sure?" data-content="You won't be able to return it">
                            Delete
                        </button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>