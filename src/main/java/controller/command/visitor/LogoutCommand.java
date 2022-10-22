package controller.command.visitor;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.HOME_PATH;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class LogoutCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        Util.redirectTo(request, response, ResourceBundle.
                getBundle(PAGES_BUNDLE).getString(HOME_PATH));
        return REDIRECTED;
    }
}
