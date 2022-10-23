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

<br><br>
<div class="container-fluid">
    <div class="row">
        <div class="col-4 offset-1">
            <div class="card shadow-2-strong mx-auto my-5 card-registration" style="border-radius: 15px;">
                <div class="card-body p-4">
                    <h4 class="card-header text-center font-weight-bold mb-5">Employee Registration Form</h4>
                    <form>

                        <div class="row">
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="firstName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="firstName">First Name</label>
                                </div>
                            </div>
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="lastName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="lastName">Last Name</label>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="email" id="emailAddress" class="form-control form-control-lg"
                                           required/>
                                    <label class="form-label" for="emailAddress">Email</label>
                                </div>
                            </div>
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="password" class="form-control form-control-lg" name="password"
                                           required/>
                                    <label class="form-label" for="emailAddress">Password</label>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="tel" id="phoneNumber" class="form-control form-control-lg"/>
                                    <label class="form-label" for="phoneNumber">Phone Number</label>
                                </div>
                            </div>
                            <div class="col-6 mb-2">
                                <select class="select form-control-lg" style="border: 1px solid #ced4da;">
                                    <option selected disabled>Choose role</option>
                                    <option value="2">Subject 1</option>
                                    <option value="3">Subject 2</option>
                                    <option value="4">Subject 3</option>
                                </select>
                            </div>

                        </div>

                        <div class="row justify-content-center mt-2">
                            <button type="submit" class="btn btn-outline-warning">Create</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
        <div class="col-4">
            <div class="card shadow-2-strong mx-auto my-5 card-registration" style="border-radius: 15px;">
                <div class="card-body p-4">
                    <h4 class="card-header text-center font-weight-bold mb-5">Service creation Form</h4>
                    <form>

                        <div class="row">
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="firstName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="firstName">Service Title</label>
                                </div>
                            </div>
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="lastName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="lastName">Service Type</label>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="lastName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="lastName">Service Price</label>
                                </div>
                            </div>
                            <div class="col-6 mb-2">
                                <div class="form-outline">
                                    <input type="text" id="lastName" class="form-control form-control-lg" required/>
                                    <label class="form-label" for="lastName">Image (src)</label>
                                </div>
                            </div>
                        </div>

                        <div class="row justify-content-center mt-2">
                            <button type="submit" class="btn btn-outline-warning">Create</button>
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