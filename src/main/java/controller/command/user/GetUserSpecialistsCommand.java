package controller.command.user;

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

import static controller.util.constants.Attributes.SPECIALISTS;
import static controller.util.constants.Views.USER_SPECIALISTS_VIEW;

public class GetUserSpecialistsCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetUserSpecialistsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("i am GetUserSpecialistsCommand");
        return USER_SPECIALISTS_VIEW;
    }
}
