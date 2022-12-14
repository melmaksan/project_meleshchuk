package controller.command.visitor;

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
import static controller.util.constants.Views.PAGES_BUNDLE;
import static controller.util.constants.Views.SIGNUP_VIEW;

public class PostRegistrationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostRegistrationCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        logger.info("i am in post register");
        List<String> errors = userService.createUser(
                request.getParameter(FIRST_NAME),
                request.getParameter(LAST_NAME),
                request.getParameter(LOGIN_PARAM),
                request.getParameter(PASSWORD_PARAM),
                request.getParameter(PHONE),
                request.getParameter(ROLE));
        if (errors.isEmpty()) {
            request.getSession().setAttribute(REGISTER, SUCCESS_REGISTER);
            logger.info("SING UP WITHOUT ERRORS!");
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString("login.path"));
            return REDIRECTED;
        }
        logger.info("SING UP HAS ERRORS!");
        request.setAttribute(ERRORS, errors);
        logger.info(errors);
        return SIGNUP_VIEW;
    }
}
