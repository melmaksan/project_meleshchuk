<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/includes/head.jsp"/>
    <title>Create Respond Page</title>
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
    <div class="text-center mt-5 ">
        <h1>Create Respond Form</h1>
    </div>

    <div class="row ">
        <div class="card shadow-2-strong mx-auto my-2 card-registration" style="border-radius: 15px;">
            <div class="card-body p-4">
                <form class=form-group" method="post">
                    <input type="hidden" name="command" value="create.respond"/>

                    <div class="row">
                        <div class="col-6 mb-2">
                            <div class="form-outline">
                                <label class="form-label">Your Name</label>
                                <input type="text" name="respondName" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-6 mb-2">
                            <div class="form-outline">
                                <label class="form-label">Your Email *</label>
                                <input type="email" name="login" class="form-control" required/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6 mt-2">
                            <div class="form-group">
                                <label>Please rate the master's work *</label>
                                <div class="rating-input-wrapper d-flex justify-content-between mt-2">
                                    <label><input type="radio" name="respondMark" value="1"/>
                                        <span class="border rounded px-3 py-2">1</span></label>
                                    <label><input type="radio" name="respondMark" value="2"/>
                                        <span class="border rounded px-3 py-2">2</span></label>
                                    <label><input type="radio" name="respondMark" value="3"/>
                                        <span class="border rounded px-3 py-2">3</span></label>
                                    <label><input type="radio" name="respondMark" value="4"/>
                                        <span class="border rounded px-3 py-2">4</span></label>
                                    <label><input type="radio" name="respondMark" value="5"/>
                                        <span class="border rounded px-3 py-2">5</span></label>
                                </div>
                                <div class="rating-labels d-flex justify-content-between mt-1">
                                    <label>Very unlikely</label>
                                    <label>Very likely</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-6 mt-2">
                            <div class="form-outline">
                                <label>Select Specialist *</label>
                                <select class="selectpicker" name="specId" id="specId"
                                        data-size="5" required>
                                    <c:forEach var="specialist" items="${requestScope.specialists}">
                                        <option value="${specialist.id}">${specialist.firstName} ${specialist.lastName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="form_message">Message</label>
                                <textarea class="form-control" id="form_message" name="respondMessage" rows="4"
                                          placeholder="Write your message here..." required></textarea>
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


<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</body>
</html>

