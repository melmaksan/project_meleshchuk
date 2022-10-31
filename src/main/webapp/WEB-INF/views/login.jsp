<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title><fmt:message key="login.page"/></title></head>
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
    <div class="container">
        <div class="card w-50 mx-auto my-5">
            <div class="card-header text-center font-weight-bold"><fmt:message key="sign.in"/></div>
            <div class="card-body">
                <form class=form-group" method="post">
                    <input type="hidden" name="command" value="login"/>

                    <div class="form-group">
                        <label><fmt:message key="email.address"/></label>
                        <input type="email" class="form-control" name="login" placeholder="Enter your email" required>
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="password"/></label>
                        <input type="password" class="form-control" name="password" placeholder="***********"
                               required>
                    </div>

                    <div class="form-check py-1">
                        <label class="form-check-label">
                            <input class="form-check-input" type="checkbox"><fmt:message key="remember"/>
                        </label>
                    </div>

                    <div class="row py-1">
                        <div class="col-3">
                            <button type="submit" class="btn btn-primary"><fmt:message key="login"/></button>
                        </div>
                        <div class="col-9 text-right">
                            <a href="${pageContext.request.contextPath}/site/registration">
                                <fmt:message key="create.account"/>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

