<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<body class="text-center">
    <main class="form-signing">
        <c:if test="${not empty requestScope.errors}">
            <div class="alert alert-danger">
                <c:forEach items="${requestScope.errors}" var="error">
                    <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
                </c:forEach>
            </div>
        </c:if>
        <form class="form-signing" method="post">
            <input type="hidden" name="command" value="login.post"/>
            <div class="form-floating">
                <input  class="form-control" name="login" id="login" placeholder="<fmt:message key="enter.login"/>"
                        value="<c:out value="${requestScope.user.login}" />" required autofocus>
                <label for="login"><fmt:message key="enter.login"/></label>
            </div>

            <div class="form-floating">
                <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="enter.password"/>" required>
                <label for="password"><fmt:message key="enter.password"/></label>
            </div>

            <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="login" /></button>
        </form>
    </main>
</body>
</html>