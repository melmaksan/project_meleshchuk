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

public class LogoutCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        Util.redirectTo(request, response, ResourceBundle.
                getBundle(Views.PAGES_BUNDLE).getString(HOME_PATH));
        return REDIRECTED;
    }
}
