package controller.command.admin;

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

import static controller.util.constants.Attributes.SERVICES_UNIQUE_TYPE;
import static controller.util.constants.Attributes.SPECIALISTS;
import static controller.util.constants.Views.ADMIN_SPECIALISTS_VIEW;

public class GetAllSpecialistsCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private final ServiceForService service = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetAllSpecialistsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        List<User> users = userService.findAllSpecialists();
        logger.info("SPECIALISTS ==> " + userService.findAllSpecialists());
        for (User user : users) {
            request.setAttribute(SERVICES_UNIQUE_TYPE + user.getId(), service.getUniqueServiceTypes(user.getServices()));
            logger.info("serviceTypes ==> " + user.getId() + service.getUniqueServiceTypes(user.getServices()));
        }
        return ADMIN_SPECIALISTS_VIEW;
    }
}
