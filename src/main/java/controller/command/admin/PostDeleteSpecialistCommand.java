package controller.command.admin;

import controller.command.ICommand;
import controller.util.constants.Attributes;
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
import static controller.util.constants.Views.ADMIN_SPECIALISTS_VIEW;

public class PostDeleteSpecialistCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostDeleteSpecialistCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = userService.deleteUser(Long.parseLong(request.getParameter(SPEC_ID)));
        if (!errors.isEmpty()) {
            logger.info("Specialist is exist in booked order! Try later please");
            request.setAttribute(ERRORS, errors);
        }
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("SPECIALISTS ==> " + userService.findAllSpecialists());
        return ADMIN_SPECIALISTS_VIEW;
    }
}
