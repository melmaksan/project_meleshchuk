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
    <h1 class="offset-1 my-3"><fmt:message key="all"/> <fmt:message key="a.orders"/></h1>
    <table class="table offset-1 text-center table-sm table-striped table-bordered" id="sortTable3" style="width: 83%">
        <thead class="thead-dark">
        <tr>
            <th class="col-1 " scope="col" style="max-width: 5%">#id</th>
            <th class="col-2 " scope="col"><fmt:message key="client"/></th>
            <th class="col-2 " scope="col" style="max-width: 12%; min-width: 8%"><fmt:message key="service.title"/></th>
            <th class="col-1 " scope="col"><fmt:message key="spec"/></th>
            <th class="col-1 " scope="col"><fmt:message key="price"/></th>
            <th class="col-2 " scope="col"><fmt:message key="datetime"/></th>
            <th class="col-1 " scope="col"><fmt:message key="submit"/></th>
            <th class="col-1 " scope="col"><fmt:message key="status"/></th>
            <th class="col-1 " scope="col"><fmt:message key="payment"/></th>
            <th class="col-1 " scope="col"><fmt:message key="delete"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <c:forEach var="service" items="${order.services}">
                <c:forEach var="spec" items="${order.specialists}">
                    <tr>
                        <c:if test="${sessionScope.user.admin}">
                            <td class="col-1 py-1" style="max-width: 5%">${order.id}</td>
                            <td class="col-2 py-1">${order.user.firstName} ${order.user.lastName}</td>
                            <td class="col-1 py-1" style="max-width: 12%; min-width: 8%">${service.title}</td>
                            <td class="col-1 py-1">${spec.firstName}</td>
                            <td class="col-1 py-1">${service.price}₴</td>
                            <form action="${pageContext.request.contextPath}/site/admin/orders" method="post">
                                <input type="hidden" name="command" value="change.time"/>
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <td class="col-2 py-1">
                                    <label for="appointment_time"></label>
                                    <input type="datetime-local" id="appointment_time" name="appointment_time"
                                           min="2022-10-30T00:00" max="2022-11-30T00:00" step="1800"
                                           value="${order.timeStart}" required/>
                                </td>
                                <td class="col-1 pt-0 pb-1 ">
                                    <button type="submit" class="btn-sm btn-outline-primary py-0 mt-1 mb-0"
                                            style="border-radius: 8px"><fmt:message key="change"/>
                                    </button>
                                </td>
                            </form>
                            <td class="col-1 py-1">
                                <div class="dropdown">
                                    <button class="btn" id="dropdown04" data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">${order.orderStatus.name}</button>
                                    <div class="dropdown-menu text-center" aria-labelledby="dropdown04"
                                         style="min-width: 5rem; padding: 0;">
                                        <c:forEach var="status" items="${requestScope.orderStatuses}">
                                            <form class="my-0"
                                                  action="${pageContext.request.contextPath}/site/admin/orders"
                                                  method="post">
                                                <input type="hidden" name="command" value="change.status"/>
                                                <input type="hidden" name="orderId" value="${order.id}"/>
                                                <input type="hidden" name="orderStatus" value="${status.id}"/>
                                                <button class="dropdown-item" type="submit"
                                                        style="padding: 0.25rem 0.58rem;">
                                                        ${status.toString()}
                                                </button>
                                            </form>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td class="col-1 py-1">
                                <div class="dropdown">
                                    <button class="btn" id="dropdown04" data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">${order.paymentStatus.name}</button>
                                    <div class="dropdown-menu text-center" aria-labelledby="dropdown04"
                                         style="min-width: 5rem; padding: 0;">
                                        <c:forEach var="data" items="${requestScope.paymentStatuses}">
                                            <form class="my-0"
                                                  action="${pageContext.request.contextPath}/site/admin/orders"
                                                  method="post">
                                                <input type="hidden" name="command" value="change.paymentStatus"/>
                                                <input type="hidden" name="orderId" value="${order.id}"/>
                                                <input type="hidden" name="paymentStatus" value="${data.id}"/>
                                                <button class="dropdown-item" type="submit"
                                                        style="padding: 0.25rem 1.22rem;">
                                                        ${data.toString()}
                                                </button>
                                            </form>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td class="col-1 pt-0 pb-1 ">
                                <form class="mt-1 mb-0" action="${pageContext.request.contextPath}/site/admin/orders"
                                      method="post">
                                    <input type="hidden" name="command" value="delete.order"/>
                                    <input type="hidden" name="orderId" value="${order.id}"/>
                                    <button type="submit" class="btn-sm btn-outline-danger py-0"
                                            data-toggle="confirmation2" data-singleton="true" data-popout="true"
                                            data-btn-ok-label="Continue" data-btn-cancel-label="Cancel"
                                            data-title="Are you sure?" data-content="You won't be able to return it">
                                        <fmt:message key="delete"/>
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>

<%--    Pagination--%>
    <div class="d-flex justify-content-end" id="pagination">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${requestScope.currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/site/admin/orders?page=${requestScope.currentPage - 1}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li class="page-item active"><a class="page-link">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/site/admin/orders?page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/site/admin/orders?page=${requestScope.currentPage + 1}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</section>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

