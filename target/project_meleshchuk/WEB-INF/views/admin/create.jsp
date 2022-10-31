<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title><fmt:message key="admin.create.page"/></title>
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
    <div class="container-fluid">
        <div class="row">
            <div class="col-4 offset-1">
                <div class="card shadow-2-strong mx-auto my-5 card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4">
                        <h4 class="card-header text-center font-weight-bold mb-5">
                            <fmt:message key="registration.form"/></h4>
                        <form class=form-group" method="post">
                            <input type="hidden" name="command" value="create.user"/>

                            <div class="row">
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="first_name" class="form-control"
                                               required/>
                                        <label class="form-label"><fmt:message key="first.name"/></label>
                                    </div>
                                </div>
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="last_name" class="form-control"
                                               required/>
                                        <label class="form-label"><fmt:message key="last.name"/></label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="email" name="login" class="form-control"
                                               placeholder="example@form.com" required/>
                                        <label class="form-label"><fmt:message key="email"/></label>
                                    </div>
                                </div>
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="password" class="form-control"
                                               placeholder="min 5, max 45 characters" required/>
                                        <label class="form-label"><fmt:message key="password"/></label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="tel" name="phone_num" placeholder="+38(044)937-99-92"
                                               class="form-control " required/>
                                        <label class="form-label"><fmt:message key="phone.number"/></label>
                                    </div>
                                </div>
                                <div class="col-6 mb-2">
                                    <select class="form-control w-75" name="role" id="role"
                                            style="border: 1px solid #ced4da;" required>
                                        <option value="" hidden><fmt:message key="choose"/> <fmt:message
                                                key="role"/></option>
                                        <option value="ADMIN"><fmt:message key="admin"/></option>
                                        <option value="SPECIALIST"><fmt:message key="spec"/></option>
                                    </select>
                                </div>

                            </div>

                            <div class="row justify-content-center">
                                <button type="submit" class="btn btn-outline-warning"><fmt:message
                                        key="create"/></button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
            <div class="col-4">
                <div class="card shadow-2-strong mx-auto my-5 card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4">
                        <h4 class="card-header text-center font-weight-bold mb-5"><fmt:message key="creation"/></h4>
                        <form class=form-group" method="post">
                            <input type="hidden" name="command" value="create.service"/>

                            <div class="row">
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="serviceTitle" class="form-control" required/>
                                        <label class="form-label"><fmt:message key="service.title"/></label>
                                    </div>
                                </div>
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="serviceType" class="form-control" required/>
                                        <label class="form-label"><fmt:message key="service.type"/></label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <input type="text" name="servicePrice" class="form-control"
                                               placeholder="max 2 characters after dot" required/>
                                        <label class="form-label"><fmt:message
                                                key="price"/></label>
                                    </div>
                                </div>
                                <div class="col-6 mb-2">
                                    <div class="form-outline">
                                        <select class="selectpicker" name="specId" id="specId" multiple
                                                title="Select Specialist(s)" data-max-options="3" data-size="5"
                                                required>
                                            <c:forEach var="specialist" items="${requestScope.specialists}">
                                                <option value="${specialist.id}">${specialist.firstName} ${specialist.lastName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-4 mb-2">
                                    <input type="text" name="duration" class="form-control"
                                           placeholder="01:23:45" required/>
                                    <label class="form-label"><fmt:message key="duration"/></label>
                                </div>
                                <div class="col-8 mb-2">
                                    <input type="text" name="serviceImage" class="form-control" required/>
                                    <label class="form-label"><fmt:message key="image"/></label>
                                </div>
                            </div>

                            <div class="row justify-content-center">
                                <button type="submit" class="btn btn-outline-warning"><fmt:message
                                        key="create"/></button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>