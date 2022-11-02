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
import static controller.util.constants.Attributes.ERRORS;
import static controller.util.constants.Views.*;

public class PostCreateEmployeeCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostCreateEmployeeCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<String> errors = userService.createUser(
                request.getParameter(FIRST_NAME),
                request.getParameter(LAST_NAME),
                request.getParameter(LOGIN_PARAM),
                request.getParameter(PASSWORD_PARAM),
                request.getParameter(PHONE),
                request.getParameter(ROLE));
        if (errors.isEmpty()) {
            logger.info("CREATE WITHOUT ERRORS!");
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString(HOME_PATH));
            return REDIRECTED;
        }
        logger.info("CREATING EMPLOYEE HAS ERRORS!");
        request.setAttribute(ERRORS, errors);
        return CREATE_VIEW;
    }
}
