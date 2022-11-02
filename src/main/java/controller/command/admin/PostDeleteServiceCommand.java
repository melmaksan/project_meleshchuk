package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.HOME_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostDeleteServiceCommand implements ICommand {

    private final ServiceForService services = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(PostDeleteServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = services.deleteService(Long.parseLong(request.getParameter(SERVICE_ID)));
        if (errors.isEmpty()) {
            logger.info("DELETE WITHOUT ERRORS!");
            setAttribute(request);
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString(HOME_PATH));
            return REDIRECTED;
        }
        logger.info("Service is exist in booker order! Try later please");
        request.setAttribute(ERRORS, errors);
        setAttribute(request);
        return HOME_VIEW;
    }

    private void setAttribute(HttpServletRequest request) {
        request.setAttribute(SERVICES_UNIQUE_TYPE, services.getUniqueServiceTypes(services.findAllService()));
        request.setAttribute(SERVICES, services.findAllService());
    }
}
