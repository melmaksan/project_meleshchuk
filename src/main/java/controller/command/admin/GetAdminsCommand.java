package controller.command.admin;

import controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.ADMINS;
import static controller.util.constants.Views.ADMINS_VIEW;

public class GetAdminsCommand implements ICommand {

        private final UserService userService = ServiceFactory.getUserService();
        private static final Logger logger = LogManager.getLogger(GetAdminsCommand.class);

        @Override
        public String execute(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            logger.info("admins ==> " + userService.findAdmins());
            request.setAttribute(ADMINS, userService.findAdmins());
            return ADMINS_VIEW;
        }
}
