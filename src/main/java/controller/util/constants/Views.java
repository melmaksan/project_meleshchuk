package controller.util.constants;

public class Views {

    public static final String PAGES_BUNDLE = "pagespath";
    public static final String FOLDER = "/WEB-INF/views/";
    public static final String HOME_VIEW = FOLDER + "index.jsp";
    public static final String LOGIN_VIEW = FOLDER + "login.jsp";
    public static final String SIGNUP_VIEW = FOLDER + "registration.jsp";
    public static final String SPECIALISTS_VIEW = FOLDER + "specialists.jsp";
    public static final String RESPONDS_VIEW = FOLDER + "responds.jsp";

    public static final String ADMIN_ROLE_FOLDER = "admin/";
    public static final String SPECIALIST_ROLE_FOLDER = "specialist/";
    public static final String USER_ROLE_FOLDER = "user/";

    public static final String USER_SPECIALISTS_VIEW = FOLDER + USER_ROLE_FOLDER + "userSpecialists.jsp";
    public static final String USER_ORDERS_VIEW = FOLDER + USER_ROLE_FOLDER + "userOrders.jsp";
    public static final String CREATE_RESPOND_VIEW = FOLDER + USER_ROLE_FOLDER + "createRespond.jsp";
    public static final String CONFIRMATION_VIEW = FOLDER + USER_ROLE_FOLDER + "confirmation.jsp";

    public static final String ADMIN_ORDERS_VIEW = FOLDER + ADMIN_ROLE_FOLDER + "adminOrders.jsp";
    public static final String ADMIN_SPECIALISTS_VIEW = FOLDER + ADMIN_ROLE_FOLDER + "adminSpecialists.jsp";
    public static final String ADMIN_USERS_VIEW = FOLDER + ADMIN_ROLE_FOLDER + "adminUsers.jsp";
    public static final String CREATE_VIEW = FOLDER + ADMIN_ROLE_FOLDER + "create.jsp";
    public static final String ADMINS_VIEW = FOLDER + ADMIN_ROLE_FOLDER + "admins.jsp";

    public static final String SPECIALIST_ORDERS_VIEW = FOLDER + SPECIALIST_ROLE_FOLDER + "specialistOrders.jsp";
    public static final String SPECIALIST_SCHEDULE_VIEW = FOLDER + SPECIALIST_ROLE_FOLDER + "specialistSchedule.jsp";

}
