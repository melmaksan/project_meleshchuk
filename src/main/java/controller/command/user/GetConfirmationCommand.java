package controller.command.user;

import controller.command.ICommand;
import entity.Service;
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
import java.util.ArrayList;
import java.util.List;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.CONFIRMATION_VIEW;
import static controller.util.constants.Views.USER_SPECIALISTS_VIEW;


public class GetConfirmationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(GetConfirmationCommand.class);
    private final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Service service = serviceService.findServiceById(
                Long.parseLong(request.getParameter(SERVICE_ID)));
        checkFastBooking(request, errors, service);
        if (errors.isEmpty()) {
            request.setAttribute(SERVICE, service);
            logger.info("i am GetConfirmationCommand");
            return CONFIRMATION_VIEW;
        }
        logger.info("GetConfirmationCommand HAS ERRORS");
        request.setAttribute(ERRORS, errors);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("go to user specialist page");
        return USER_SPECIALISTS_VIEW;
    }

    private void checkFastBooking(HttpServletRequest request, List<String> errors, Service service) {
        if (service == null) {
            errors.add(SERVICE_NOT_EXIST);
            return;
        }
        if (request.getParameter(SPEC_ID) == null) {
            List<User> specialists = serviceService.getSpecialists(service);
            if (specialists.isEmpty()) {
                errors.add(SPEC_NOT_EXIST);
            }
            request.setAttribute(SPECIALISTS, specialists);
        } else {
            User spec = userService.findUserById(
                    Long.parseLong(request.getParameter(SPEC_ID))).orElse(null);
            request.setAttribute(USER, spec);
        }
    }
}
