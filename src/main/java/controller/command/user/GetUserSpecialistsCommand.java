package controller.command.user;

import controller.command.ICommand;
import controller.command.visitor.GetSpecialistsCommand;
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
import static controller.util.constants.Views.USER_SPECIALISTS_VIEW;

public class GetUserSpecialistsCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetUserSpecialistsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.findAllSpecialists();
        for (User user : users) {
            request.setAttribute(SERVICES_UNIQUE_TYPE + user.getId(), serviceService.getUniqueServiceTypes(user.getServices()));
        }
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("i am user specialist view!!" + userService.findAllSpecialists());
        return USER_SPECIALISTS_VIEW;
    }
}
