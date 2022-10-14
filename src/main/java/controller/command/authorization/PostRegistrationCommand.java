package controller.command.authorization;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controller.util.constants.Attributes.*;

public class PostRegistrationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostRegistrationCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<String> errors = userService.creatingUser(
                request.getParameter(FIRST_NAME),
                request.getParameter(LAST_NAME),
                request.getParameter(LOGIN_PARAM),
                request.getParameter(PASSWORD_PARAM),
                request.getParameter(PHONE));
        if (errors.isEmpty()) {
            logger.info("LOGIN WITHOUT ERRORS!");
            return Views.LOGIN_VIEW;
        }
        logger.info("LOGIN HAS ERRORS!");
        addInvalidDataToRequest(request, errors);
        return Views.SIGNUP_VIEW;
    }

    private void addInvalidDataToRequest(HttpServletRequest request, List<String> errors) {
        request.setAttribute(ERRORS, errors);
    }

}
