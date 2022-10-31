package controller.command.visitor;

import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.HOME_PATH;
import static controller.util.constants.Views.*;

public class GetRegistrationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(GetRegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HomeCommand.removeRegStatus(request);
        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, ResourceBundle
                    .getBundle(PAGES_BUNDLE)
                    .getString(HOME_PATH));
            return REDIRECTED;
        }
        logger.info("i go to register");
        return SIGNUP_VIEW;
    }
}
