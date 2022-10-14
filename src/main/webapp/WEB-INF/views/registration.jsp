<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Sing Up</title></head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<h1>Register</h1>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

