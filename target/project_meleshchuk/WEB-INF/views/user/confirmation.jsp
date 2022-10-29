<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Confirmation Page</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
<br><br>

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong>${error}<br>
        </c:forEach>
    </div>
</c:if>


<div class="container">

    <div class="row ">
        <div class="card shadow-2-strong mx-auto my-2 card-registration" style="border-radius: 15px;">
            <div class="card-body p-4">
                <div class="modal-content">
                    <form action="${pageContext.request.contextPath}/site/user/orders" method="post">
                        <input type="hidden" name="command" value="add.order"/>

                        <!-- Modal Header -->
                        <div class="modal-header bg-light text-black-50 justify-content-center">
                            <h3 class="modal-title pl-1">Confirmation</h3>
                        </div>

                        <!-- Modal body -->
                        <div class="modal-body">
                            <div class="container">
                                <br>

                                <!-- order details   -->
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <th scope="row" style="vertical-align: inherit">Specialist:</th>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${requestScope.user == null}">
                                                    <select class="selectpicker" name="specId" id="specId" required>
                                                        <c:forEach var="specialist" items="${requestScope.specialists}">
                                                            <option value="${specialist.id}">${specialist.firstName} ${specialist.lastName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:when>
                                                <c:otherwise>
                                                    ${requestScope.user.firstName} ${requestScope.user.lastName}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Service type:</th>
                                        <td class="text-center">${requestScope.service.description}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Service title:</th>
                                        <td class="text-center">${requestScope.service.title}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Price:</th>
                                        <td class="text-center">${requestScope.service.price}₴</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" style="vertical-align: inherit"> Order time:</th>
                                        <td class="pl-3 pr-1">
                                            <!-- time picker   -->
                                            <div class="input-group date" id="datetimepicker1"
                                                 data-target-input="nearest">
                                                <input type="text" class="form-control datetimepicker-input"
                                                       id="appointment_time" name="appointment_time"
                                                       data-target="#datetimepicker1"/>
                                                <div class="input-group-append" data-target="#datetimepicker1"
                                                     data-toggle="datetimepicker">
                                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer justify-content-between px-4">
                            <c:if test="${sessionScope.user.user}">
                                <a class="btn btn-outline-secondary ml-3"
                                   href="${pageContext.request.contextPath}/site/user/specialists"
                                   role="button" id="confButton">Back</a>

                                <input type="hidden" name="userId" value="${sessionScope.user.id}"/>
                                <input type="hidden" name="specId" value="${requestScope.user.id}"/>
                                <input type="hidden" name="serviceId" value="${requestScope.service.id}"/>
                                <button type="submit" class="btn btn-outline-success mr-2"
                                        data-toggle="confirmation" data-singleton="true" data-popout="true"
                                        data-btn-ok-label="Continue" data-btn-cancel-label="Cancel"
                                        data-title="Are you sure?" data-content="You won't be able to change it"
                                        style="border-radius: 8px">
                                    Confirm
                                </button>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
