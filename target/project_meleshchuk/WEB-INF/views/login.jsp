<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Login Page</title></head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">Sing In</div>
        <div class="card-body">
            <form class=form-group" method="post">

                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" class="form-control" name="login" placeholder="Enter your email" required>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="***********"
                           required>
                </div>

                <div class="row justify-content-between">
                    <div class="col">
                        <button type="submit" class="btn btn-primary m">Login</button>
                    </div>
                    <div class="col text-center align-text-bottom">
                        <a  href="${pageContext.request.contextPath}/site/registration">
                            Don`t have account? Create new
                        </a>
                    </div>
                </div>


            </form>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>