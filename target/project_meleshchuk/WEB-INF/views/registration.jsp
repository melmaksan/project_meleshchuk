<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Registration Page</title>
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

<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center font-weight-bold">Sing Up</div>
        <div class="card-body">
            <form class=form-group" method="post">
                <input type="hidden" name="command" value="registration"/>

                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="first_name" required>
                </div>

                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="last_name" required>
                </div>

                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" class="form-control" name="login" placeholder="example@form.com"
                           required>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="text" class="form-control" name="password" placeholder="min 5, max 45 characters"
                           required>
                </div>

                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="tel" class="form-control" name="phone_num" placeholder="+38(0xx)xx-xx-xx"
                           required>
                </div>

                <div class="text-center ">
                    <button type="submit" class="btn btn-outline-primary"><fmt:message key="signup"/></button>
                </div>

            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

