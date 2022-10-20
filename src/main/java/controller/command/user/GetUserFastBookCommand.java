package controller.command.user;

import controller.command.ICommand;
import controller.command.visitor.GetFastBookCommand;
import entity.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.FAST_BOOK;
import static controller.util.constants.Attributes.SERVICE_ID;
import static controller.util.constants.Views.USER_FAST_BOOK_VIEW;

public class GetUserFastBookCommand implements ICommand {

    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(GetFastBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalArgumentException {
        Service service = serviceService.findServiceById(Long.parseLong
                (request.getParameter(SERVICE_ID)));
        logger.info("service ==> " + service);
        request.setAttribute(SERVICE_ID, service.getId());
        request.setAttribute(FAST_BOOK, service.getUsers());
        logger.info("specialist ==> " + service.getUsers());
        return USER_FAST_BOOK_VIEW;
    }
}
