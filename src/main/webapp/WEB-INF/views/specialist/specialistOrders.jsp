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
    <div class="row mt-2 mb-1 offset-1 justify-content-between">
        <h1 class="align-self-end"><fmt:message key="your.orders"/></h1>
        <div class="col-2 align-self-end" id="datePicker2">
            <form class="my-0" action="${pageContext.request.contextPath}/site/specialist/orders">
                <input type="hidden" name="command" value="spec.filter"/>
                <label class="my-0 text-black-50" for="datetimepicker2"><fmt:message key="date"/>:</label>
                <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                    <input type="text" class="form-control datetimepicker-input"
                           data-toggle="datetimepicker" id="appointment_time"
                           name="appointment_time" data-target="#datetimepicker2"
                           value="${requestScope.date}"/>
                    <div class="input-group-append" data-target="#datetimepicker2">
                        <button class="btn-outline-light text-black-50"><fmt:message key="find"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable7" style="width: 83%">
        <thead class="thead-dark">
        <tr>
            <th class="col-1 " scope="col">#id</th>
            <th class="col-2 " scope="col"><fmt:message key="service.title"/></th>
            <th class="col-2 " scope="col"><fmt:message key="client"/></th>
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
                <tr>
                    <td class="col-1 py-1">${order.id}</td>
                    <td class="col-2 py-1">${service.title}</td>
                    <td class="col-2 py-1">${order.user.firstName} ${order.user.lastName}</td>
                    <td class="col-1 py-1">${service.price}₴</td>
                    <td class="col-2 py-1">${order.orderTime}</td>
                    <td class="col-1 py-1">${service.minutes}<fmt:message key="min"/></td>
                    <td class="col-1 py-1">
                        <div class="dropdown">
                            <button class="btn" id="dropdown04" data-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">${order.orderStatus.name}</button>
                            <div class="dropdown-menu text-center" aria-labelledby="dropdown04"
                                 style="min-width: 80px; padding: 0;">
                                <form class="my-0" action="${pageContext.request.contextPath}/site/specialist/orders"
                                      method="post">
                                    <input type="hidden" name="command" value="change.service.status"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <input type="hidden" name="orderStatus" value="1"/>
                                    <button class="dropdown-item py-1" type="submit"
                                            style="padding: 0.25rem 1.3rem;">BOOKED
                                    </button>
                                </form>
                                <form class="my-0" action="${pageContext.request.contextPath}/site/specialist/orders"
                                      method="post">
                                    <input type="hidden" name="command" value="change.service.status"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <input type="hidden" name="orderStatus" value="2"/>
                                    <button class="dropdown-item py-1" type="submit"
                                            style="padding: 0.25rem 1.3rem;">DONE
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
</section>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
