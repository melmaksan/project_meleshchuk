package controller.command.visitor;

import controller.command.ICommand;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.HOME_VIEW;

public class PostDescTitleCommand implements ICommand {

    private static final ServiceForService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SERVICES_UNIQUE_TYPE, serviceService.getUniqueServiceTypes(serviceService.findAllService()));
        request.setAttribute(SERVICES, serviceService.descByTitleService());
        return HOME_VIEW;
    }
}
