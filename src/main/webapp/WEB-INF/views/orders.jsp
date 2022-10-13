<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Orders Page</title></head>
</head>
<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/><br>


<div class="col-md-4">
    <h2>YOUTODIEJDIJ</h2>
    <p>A telephone is a telecommunications device that permits two or more users to conduct a conversation when they are too far apart to be heard directly. A telephone converts sound, typically and most efficiently the human voice, into electronic signals that are transmitted via cables  </p>
    <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
</div>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
