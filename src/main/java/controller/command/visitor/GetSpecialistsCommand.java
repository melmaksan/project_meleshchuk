package controller.command.visitor;

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


import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.SPECIALISTS_VIEW;

public class GetSpecialistsCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetSpecialistsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        HomeCommand.removeRegStatus(request);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("i am GetSpecialistsCommand");
        return SPECIALISTS_VIEW;
    }
}
