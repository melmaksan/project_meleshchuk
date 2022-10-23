package controller.command.admin;

import controller.command.ICommand;
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
import static controller.util.constants.Views.ADMIN_USERS_VIEW;

public class PostDeleteUserCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostDeleteSpecialistCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = userService.deleteUser(Long.parseLong(request.getParameter(USER_ID)));
        if (!errors.isEmpty()) {
            logger.info("User is exist in booked order!");
            request.setAttribute(ERRORS, errors);
        }
        request.setAttribute(USER_LIST, userService.findAllClients());
        logger.info("users ==> " + userService.findAllClients());
        return ADMIN_USERS_VIEW;
    }
}
