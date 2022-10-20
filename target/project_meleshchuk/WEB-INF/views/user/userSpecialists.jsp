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

<h1 class="offset-1 mt-4 mb-3">Specialists</h1>
<table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable" style="width: 83%">
    <thead class="thead-dark">
    <tr>
        <th class="col-3 " scope="col">Name</th>
        <th class="col-1 " scope="col">Rating</th>
        <th class="col-2 " scope="col">Service Type</th>
        <th class="col-2 " scope="col">Service Title</th>
        <th class="col-1 " scope="col">Price</th>
        <th class="col-2 " scope="col">Data & Time</th>
        <th class="col-1 " scope="col">Booking</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="specialist" items="${requestScope.specialists}">
        <c:forEach var="service" items="${specialist.services}">
            <tr>
                <form class="" action="${pageContext.request.contextPath}/site/user/orders">
                    <input type="hidden" name="command" value="addOrder.post"/>
                    <td class="col-3 py-1"><c:out value="${specialist.firstName} ${specialist.lastName}"/></td>
                    <td class="col-1 py-1">
                        <h6 class="card-text mt-1 mb-0">${specialist.rating}
                            <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor"
                                 class="bi bi-star" viewBox="0 0 16 16">
                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"></path>
                            </svg>
                        </h6>
                    </td>
                    <td class="col-2 py-1">${service.description}</td>
                    <td class="col-2 py-1">${service.title}</td>
                    <td class="col-1 py-1 mt-1 mb-0">${service.price}₴</td>
                    <td class="col-2 py-1">
                        <label for="appointment_time"></label>
                        <input type="datetime-local" id="appointment_time" name="appointment_time"
                               min="2022-10-21T00:00" max="2022-11-21T00:00" required/>
                    </td>
                    <td class="col-1 pt-0 pb-1 pr-2">
                        <c:if test="${sessionScope.user.user}">
                            <input type="hidden" name="userId" value="${sessionScope.user.id}"/>
                            <input type="hidden" name="specId" value="${specialist.id}"/>
                            <input type="hidden" name="serviceId" value="${service.id}"/>
                            <button type="submit" class="btn-sm btn-outline-secondary py-0 mt-1 mb-0"
                                    data-toggle="confirmation" data-singleton="true" data-btn-ok-label="Continue"
                                    data-btn-cancel-label="Cancel" data-title="Are you sure?"
                                    data-content="You won't be able to change it" style="border-radius: 8px">
                                Confirm
                            </button>
                        </c:if>
                    </td>
                </form>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
