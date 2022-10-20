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

<h1 class="offset-2 mt-3 mb-2">Service specialists</h1>
<table class="table offset-2 text-center table-sm table-striped table-bordered" style="width: 65%">
    <thead class="thead-dark">
    <tr>
        <th class="col-4" scope="col">Name</th>
        <th class="col-1" scope="col">Rating</th>
        <th class="col-3" scope="col">Data & Time</th>
        <th class="col-1 pr-2" scope="col">Booking</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="spec" items="${requestScope.fastBookSpecialist}">
        <tr>
            <td class="col-4 py-1"><c:out value="${spec.firstName} ${spec.lastName}"/></td>
            <td class="col-1 py-1">
                <h6 class="card-text mt-1 mb-0">${spec.rating}
                    <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor"
                         class="bi bi-star" viewBox="0 0 16 16">
                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"></path>
                    </svg>
                </h6>
            </td>
            <td class="col-3 py-1">
                <label for="appointment-time"></label>
                <input type="datetime-local" id="appointment-time" name="meeting-time" value="2022-10-19T19:30"
                       step="900" min="2022-10-19T00:00" max="2022-11-19T00:00" />
            </td>
            <td class="col-1 pt-0 pb-1 pr-2">
                <c:if test="${empty sessionScope.user}">
                    <form class="mt-1 mb-0" action="${pageContext.request.contextPath}/site/login">
                        <button type="submit" class="btn-sm btn-outline-secondary py-0" style="border-radius: 8px">
                            Book
                        </button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
