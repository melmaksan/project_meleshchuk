package controller.command.user;

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
import static controller.util.constants.Views.CREATE_RESPOND_VIEW;

public class GetCreateRespondCommand implements ICommand {

    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetCreateRespondCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("SPECIALISTS ==> " + userService.findAllSpecialists());
        return CREATE_RESPOND_VIEW;
    }
}
