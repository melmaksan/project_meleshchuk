package controller.command.admin;

import controller.command.HomeCommand;
import controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.USER_LIST;
import static controller.util.constants.Views.ADMIN_USERS_VIEW;

public class GetAllUsersCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetAllOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        logger.info("users ==> " + userService.findAllClients());
        request.setAttribute(USER_LIST, userService.findAllClients());
        return ADMIN_USERS_VIEW;
    }
}
