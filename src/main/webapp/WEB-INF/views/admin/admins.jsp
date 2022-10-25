<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Beauty Salon</title>
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

<h1 class="offset-2 mt-3 mb-4">Admins</h1>
<table class="table offset-2 text-center table-sm table-striped table-bordered" id="sortTable6" style="width: 66%">
    <thead class="thead-dark">
    <tr>
        <th class="col-1 " scope="col">#id</th>
        <th class="col-2 " scope="col">Name</th>
        <th class="col-2 " scope="col">Email</th>
        <th class="col-2 " scope="col">Mobile Phone</th>
        <th class="col-1 " scope="col">Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="admin" items="${requestScope.admins}">
        <tr>
            <c:if test="${sessionScope.user.admin}">
                <td class="col-1 py-1">${admin.id}</td>
                <td class="col-2 py-1"><c:out value="${admin.firstName} ${admin.lastName}"/></td>
                <td class="col-2 py-1">${admin.login}</td>
                <td class="col-2 py-1">${admin.phoneNumber}</td>
                <td class="col-1 pt-0 pb-1 ">
                    <form class="mt-1 mb-0" action="${pageContext.request.contextPath}/site/admins" method="post">
                        <input type="hidden" name="command" value="delete.admin"/>
                        <input type="hidden" name="adminId" value="${admin.id}"/>
                        <button type="submit" class="btn-sm btn-outline-danger py-0"
                                data-toggle="confirmation2" data-singleton="true" data-popout="true"
                                data-btn-ok-label="Continue" data-btn-cancel-label="Cancel"
                                data-title="Are you sure?" data-content="You won't be able to return it">
                            Delete
                        </button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>
