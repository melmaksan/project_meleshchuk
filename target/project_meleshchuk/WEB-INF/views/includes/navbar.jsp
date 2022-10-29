<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/customTags/selectedPageTag" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<c:set var="homePage" scope="page" value="/beauty_salon/WEB-INF/views/index.jsp"/>
<c:set var="visitorSpecPage" scope="page" value="/beauty_salon/WEB-INF/views/specialists.jsp"/>
<c:set var="userSpecPage" scope="page" value="/beauty_salon/WEB-INF/views/user/userSpecialists.jsp"/>
<c:set var="userOrdersPage" scope="page" value="/beauty_salon/WEB-INF/views/user/userOrders.jsp"/>
<c:set var="userCreatePage" scope="page" value="/beauty_salon/WEB-INF/views/user/createRespond.jsp"/>
<c:set var="specialistOrdersPage" scope="page" value="/beauty_salon/WEB-INF/views/specialist/specialistOrders.jsp"/>
<c:set var="specialistSchedulePage" scope="page" value="/beauty_salon/WEB-INF/views/specialist/specialistSchedule.jsp"/>
<c:set var="adminSpecPage" scope="page" value="/beauty_salon/WEB-INF/views/admin/adminSpecialists.jsp"/>
<c:set var="adminOrdersPage" scope="page" value="/beauty_salon/WEB-INF/views/admin/adminOrders.jsp"/>
<c:set var="adminUsersPage" scope="page" value="/beauty_salon/WEB-INF/views/admin/adminUsers.jsp"/>
<c:set var="adminAdminsPage" scope="page" value="/beauty_salon/WEB-INF/views/admin/admins.jsp"/>
<c:set var="adminCreatePage" scope="page" value="/beauty_salon/WEB-INF/views/admin/create.jsp"/>
<c:set var="loginPage" scope="page" value="/beauty_salon/WEB-INF/views/login.jsp"/>
<c:set var="respondsPage" scope="page" value="/beauty_salon/WEB-INF/views/responds.jsp"/>


<c:set var="currPage" scope="page"><customTag:currPage/></c:set>

<div class="container-fluid" id="nav-bar" style="height: 4.6rem">
    <nav class="navbar navbar-expand navbar-dark bg-dark fixed-top">
        <div class="navbar-brand ml-1">
            <span><img src="${pageContext.request.contextPath}/images/logo.png" height="50" alt="BS Logo"></span>
        </div>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <%-- Home --%>
                <c:choose>
                    <c:when test="${not homePage.equals(currPage)}">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/site/home"
                               role="button"><fmt:message key="home"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/site/home"
                               role="button"><fmt:message key="home"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <%-- Specialists --%>
                <c:if test="${empty sessionScope.user}">
                    <c:choose>
                        <c:when test="${not visitorSpecPage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${sessionScope.user.user}">
                    <c:choose>
                        <c:when test="${not userSpecPage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/user/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/user/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${sessionScope.user.admin}">
                    <c:choose>
                        <c:when test="${not adminSpecPage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/specialists"
                                   role="button">Specialists</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <%-- Schedule(for specialist) --%>
                <c:if test="${sessionScope.user.specialist}">
                    <c:choose>
                        <c:when test="${not specialistSchedulePage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/specialist/schedule"
                                   role="button">Schedule</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/specialist/schedule"
                                   role="button">Schedule</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <%-- Orders --%>
                <c:if test="${not empty sessionScope.user}">
                    <c:if test="${ sessionScope.user.user}">
                        <c:choose>
                            <c:when test="${not userOrdersPage.equals(currPage)}">
                                <li class="nav-item active">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/user/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/user/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${ sessionScope.user.admin}">
                        <c:choose>
                            <c:when test="${not adminOrdersPage.equals(currPage)}">
                                <li class="nav-item active">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${ sessionScope.user.specialist}">
                        <c:choose>
                            <c:when test="${not specialistOrdersPage.equals(currPage)}">
                                <li class="nav-item active">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/specialist/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/site/specialist/orders"
                                       role="button"><fmt:message key="user.orders"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:if>
                <%-- Users(for admin) --%>
                <c:if test="${sessionScope.user.admin}">
                    <c:choose>
                        <c:when test="${not adminUsersPage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/users"
                                   role="button">Users</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admin/users"
                                   role="button">Users</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <%-- Admins(for admin) --%>
                <c:if test="${sessionScope.user.admin}">
                    <c:choose>
                        <c:when test="${not adminAdminsPage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admins"
                                   role="button">Admins</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/admins"
                                   role="button">Admins</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:choose>
                    <c:when test="${not respondsPage.equals(currPage)}">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/site/responds"
                               role="button">Responds</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/site/responds"
                               role="button">Responds</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul class="navbar-nav ml-auto">
                <%-- Create(for admin) --%>
                <c:if test="${sessionScope.user.admin}">
                    <c:choose>
                        <c:when test="${not adminCreatePage.equals(currPage)}">
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/create"
                                   role="button">Create</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/site/create"
                                   role="button">Create</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <%-- Account --%>
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item mr-3 active">
                        <a class="nav-link" href="#" data-toggle="modal" data-target="#profile-modal">
                                ${sessionScope.user.firstName} ${sessionScope.user.lastName}</a>
                    </li>
                </c:if>
                <%-- Login/Logout --%>
                <c:if test="${empty sessionScope.user}">
                    <c:choose>
                        <c:when test="${not loginPage.equals(currPage)}">
                            <li>
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/site/login"
                                   role="button"><fmt:message key="login"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/login"
                                   role="button"><fmt:message key="login"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${not empty sessionScope.user}">
                    <li>
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/site/logout?command=logout"
                           role="button"><fmt:message key="logout"/></a>
                    </li>
                </c:if>
                <%-- Language --%>
                <div class="dropdown mx-1 ml-2">
                    <button class="btn btn-dark dropdown-toggle" id="dropdown03" type="button"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ${sessionScope.locale.getLanguage().toUpperCase()}
                    </button>
                    <div class="dropdown-menu text-center" aria-labelledby="dropdown03"
                         style="min-width: 4rem; padding: 0;">
                        <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                            <a class="dropdown-item" href="?lang=${lang}" style="padding: 0.25rem 0.5rem;">
                                    ${lang.toUpperCase()}</a>
                        </c:forEach>
                    </div>
                </div>
            </ul>
        </div>
    </nav>


    <!-- Profile Modal -->

    <!-- The Modal -->
    <div class="modal" id="profile-modal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header bg-dark text-white text-center">
                    <h4 class="modal-title ml-1">User Profile</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="container text-center">
                        <img src="${pageContext.request.contextPath}/images/profile.jpg" class="img-fluid"
                             alt="profile" id="profile_img" style="max-height:  150px; border-radius: 25%"><br><br><br>

                        <!-- details   -->
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row"> Name:</th>
                                <td>${sessionScope.user.firstName} ${sessionScope.user.lastName}</td>
                            </tr>
                            <tr>
                                <th scope="row"> Email:</th>
                                <td>${sessionScope.user.login}</td>
                            </tr>
                            <tr>
                                <th scope="row"> Phone:</th>
                                <td>${sessionScope.user.phoneNumber}</td>
                            </tr>
                            <tr>
                                <th scope="row"> Role:</th>
                                <td>${sessionScope.user.role.name}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- End Modal -->


</div>
