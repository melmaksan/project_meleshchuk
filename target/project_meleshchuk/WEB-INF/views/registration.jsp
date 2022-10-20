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

<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center font-weight-bold">Sing Up</div>
        <div class="card-body">
            <form class=form-group" method="post">
                <input type="hidden" name="command" value="register.post"/>

                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="first_name" required>
                </div>

                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="last_name"  required>
                </div>

                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" class="form-control" name="login" placeholder="Enter your email" required>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control valida" name="password" placeholder="***********"
                           required>
                </div>

                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="tel" class="form-control" name="phone_num" placeholder="+38(044)937-99-92" required>
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

