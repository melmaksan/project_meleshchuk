<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Orders Page</title>
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

<h1 class="offset-1 my-3">Your Orders</h1>
<table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable7" style="width: 83%">
    <thead class="thead-dark">
    <tr>
        <th class="col-1 " scope="col">#id</th>
        <th class="col-2 " scope="col">Service Title</th>
        <th class="col-2 " scope="col">Client</th>
        <th class="col-1 " scope="col">Price</th>
        <th class="col-2 " scope="col">Data & Time</th>
        <th class="col-2 " scope="col">Status</th>
        <th class="col-2 " scope="col">Payment</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.orders}">
        <c:forEach var="service" items="${order.services}">
                <tr>
                    <td class="col-1 py-1">${order.id}</td>
                    <td class="col-2 py-1">${service.title}</td>
                    <td class="col-2 py-1">${order.user.firstName} ${order.user.lastName}</td>
                    <td class="col-1 py-1">${service.price}₴</td>
                    <td class="col-2 py-1">${order.orderTime}</td>
                    <td class="col-1 py-1">
                        <div class="dropdown">
                            <button class="btn" id="dropdown04" data-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">${order.orderStatus.name}</button>
                            <div class="dropdown-menu text-center" aria-labelledby="dropdown04"
                                 style="min-width: 119px; padding: 0;">
                                <form class="my-0" action="${pageContext.request.contextPath}/site/specialist/orders"
                                      method="post">
                                    <input type="hidden" name="command" value="change.service.status"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <input type="hidden" name="orderStatus" value="1"/>
                                    <button class="dropdown-item py-1" type="submit">BOOKED
                                    </button>
                                </form>
                                <form class="my-0" action="${pageContext.request.contextPath}/site/specialist/orders"
                                      method="post">
                                    <input type="hidden" name="command" value="change.service.status"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <input type="hidden" name="orderStatus" value="2"/>
                                    <button class="dropdown-item py-1" type="submit">DONE
                                    </button>
                                </form>
                            </div>
                        </div>
                    </td>
                    <td class="col-1 py-1">${order.paymentStatus.name}</td>
                </tr>
            </c:forEach>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
