package controller.command.visitor;

import controller.command.ICommand;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.SPECIALISTS_VIEW;

public class GetSpecialistsCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetSpecialistsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        return SPECIALISTS_VIEW;
    }
}
