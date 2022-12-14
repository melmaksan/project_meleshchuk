package controller.filter;

import controller.util.Util;
import controller.util.constants.Attributes;
import entity.Role;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.PAGES_BUNDLE;


public class AuthorizationFilter implements Filter {

    public static final int ADMIN_ROLE_ID = Role.RoleIdentifier.ADMIN.getId();
    public static final int USER_ROLE_ID = Role.RoleIdentifier.USER.getId();
    public static final int SPEC_ROLE_ID = Role.RoleIdentifier.SPECIALIST.getId();
    private static final ResourceBundle bundle = ResourceBundle.getBundle(PAGES_BUNDLE);

    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Hello, I`m AuthorizationFilter!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (isUserLoggedIn(request)) {
            Util.redirectTo(request, (HttpServletResponse) servletResponse, bundle.getString(LOGIN_PATH));
            logger.info(ACCESS_DENIED);
            return;
        }

        User user = (User) request.getSession().getAttribute(USER);

        if (isUserRoleInvalidForRequestedPage(request, user)) {
            Util.redirectTo(request, (HttpServletResponse) servletResponse,
                    bundle.getString(HOME_PATH));
            logger.info(ACCESS_DENIED);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(Attributes.USER) == null;
    }

    private boolean isUserRoleInvalidForRequestedPage(HttpServletRequest request, User user) {
        return (isUserPage(request) && user.getRole().getId() != USER_ROLE_ID) ||
                (isAdminPage(request) && user.getRole().getId() != ADMIN_ROLE_ID) ||
                 (isSpecialistPage(request) && user.getRole().getId() != SPEC_ROLE_ID);
    }

    private boolean isUserPage(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + bundle.getString(SITE_PREFIX) +
                        bundle.getString(USER_PREFIX));
    }

    private boolean isAdminPage(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + bundle.getString(SITE_PREFIX) +
                        bundle.getString(ADMIN_PREFIX));
    }

    private boolean isSpecialistPage(HttpServletRequest request) {
        return request.getRequestURI().startsWith(request.getContextPath() + bundle.getString(SITE_PREFIX) +
                bundle.getString(SPEC_PREFIX));
    }
}
