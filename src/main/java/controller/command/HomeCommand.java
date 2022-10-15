package controller.command;

import controller.util.constants.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Views.*;

public class HomeCommand implements ICommand {

    private static final ServiceForService services = ServiceFactory.getServiceService();
    private static final Logger logger = LogManager.getLogger(HomeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Attributes.SERVICES,services.findAllService());
        logger.info("i go home");
        return HOME_VIEW;
    }

}
