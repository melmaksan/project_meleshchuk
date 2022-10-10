package controller;

import controller.command.ICommand;
import controller.i18n.SupportedLocale;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class Controller extends HttpServlet {

    private final static String SUPPORTED_LOCALES = "supportedLocales";
    private ControllerHelper controllerHelper;

    @Override
    public void init() {
//        super.init();
        controllerHelper = ControllerHelper.getInstance();
        getServletContext().setAttribute(SUPPORTED_LOCALES,
                SupportedLocale.getSupportedLanguages());
        getServletContext().setAttribute(Attributes.USER_LIST,
                new ConcurrentHashMap<String, User>());
//        SchedulerInit.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        ICommand command = controllerHelper.getCommand(
                getPath(request), request.getParameter("command"));

        String path = command.execute(request, response);
        if (!path.equals(ICommand.REDIRECTED)) {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    private String getPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.replaceAll(request.getContextPath() + ResourceBundle.
                getBundle(Views.PAGES_BUNDLE).
                getString("site.prefix"), "");
    }
}
