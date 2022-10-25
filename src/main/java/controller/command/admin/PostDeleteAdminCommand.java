package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.ADMINS_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostDeleteAdminCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostDeleteSpecialistCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = userService.deleteUser(Long.parseLong(request.getParameter(ADMIN_ID)));
        if (errors.isEmpty()) {
            logger.info("DELETE WITHOUT ERRORS!");
            request.setAttribute(ADMINS, userService.findAdmins());
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString("admins"));
            return REDIRECTED;
        }
        logger.info("Admin is exist in booked order! Please fix the problem and try again");
        request.setAttribute(ERRORS, errors);
        request.setAttribute(ADMINS, userService.findAdmins());
        logger.info("ADMINS ==> " + userService.findAdmins());
        return ADMINS_VIEW;
    }
}
