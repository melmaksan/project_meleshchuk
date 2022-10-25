package controller.command.visitor;

import controller.command.ICommand;
import controller.util.Util;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.HOME_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;


public class PostDescPriceCommand implements ICommand {

    private static final ServiceForService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SERVICES_UNIQUE_TYPE, serviceService.getUniqueServiceTypes(serviceService.findAllService()));
        request.setAttribute(SERVICES, serviceService.descByPriceService());
        Util.redirectTo(request, response, ResourceBundle
                .getBundle(PAGES_BUNDLE)
                .getString(HOME_PATH));
        return REDIRECTED;
    }
}
