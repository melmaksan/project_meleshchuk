package controller.command;

import controller.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Views.PAGES_BUNDLE;

public class DefaultCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(DefaultCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Util.redirectTo(request, response, ResourceBundle
                .getBundle(PAGES_BUNDLE)
                .getString("home.path"));
        logger.info("i am DefaultCommand");
        return REDIRECTED;
    }
}