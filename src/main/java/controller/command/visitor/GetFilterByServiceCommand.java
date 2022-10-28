package controller.command.visitor;

import controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.HOME_VIEW;

public class GetFilterByServiceCommand implements ICommand {

    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetFilterByServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SERVICES_UNIQUE_TYPE, serviceService.getUniqueServiceTypes(serviceService.findAllService()));
        logger.info("unique types ==> " + serviceService.getUniqueServiceTypes(serviceService.findAllService()));
        request.setAttribute(SERVICES, serviceService.filterByServiceType(request.getParameter(SERVICE_TYPE)));
        return HOME_VIEW;
    }
}
