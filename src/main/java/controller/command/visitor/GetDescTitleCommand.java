package controller.command.visitor;

import controller.command.HomeCommand;
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

public class GetDescTitleCommand implements ICommand {

    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetDescTitleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        request.setAttribute(SERVICES_UNIQUE_TYPE, serviceService.getUniqueServiceTypes(serviceService.findAllService()));
        request.setAttribute(SERVICES, serviceService.descByTitleService());
        logger.info("i am GetDescTitleCommand");
        return HOME_VIEW;
    }
}
