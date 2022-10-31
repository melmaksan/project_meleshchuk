<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title><fmt:message key="orders.page"/></title>
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

<section id="section">
    <h1 class="offset-1 my-3"><fmt:message key="your.orders"/></h1>
    <table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable2" style="width: 83%">
        <thead class="thead-dark">
        <tr>
            <th class="col-1 " scope="col">#id</th>
            <th class="col-2 " scope="col"><fmt:message key="service.title"/></th>
            <th class="col-1 " scope="col"><fmt:message key="spec"/></th>
            <th class="col-1 " scope="col"><fmt:message key="price"/></th>
            <th class="col-2 " scope="col"><fmt:message key="datetime"/></th>
            <th class="col-1 " scope="col"><fmt:message key="duration"/></th>
            <th class="col-2 " scope="col"><fmt:message key="status"/></th>
            <th class="col-2 " scope="col"><fmt:message key="payment"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <c:forEach var="service" items="${order.services}">
                <c:forEach var="spec" items="${order.specialists}">
                    <tr>
                        <td class="col-1 py-1">${order.id}</td>
                        <td class="col-2 py-1">${service.title}</td>
                        <td class="col-1 py-1">${spec.firstName}</td>
                        <td class="col-1 py-1">${service.price}₴</td>
                        <td class="col-2 py-1">${order.orderTime}</td>
                        <td class="col-1 py-1">${service.minutes}<fmt:message key="min"/></td>
                        <td class="col-1 py-1">${order.orderStatus.name}</td>
                        <td class="col-1 py-1">${order.paymentStatus.name}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</section>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
