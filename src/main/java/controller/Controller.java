package controller;

import controller.command.ICommand;
import controller.i18n.SupportedLocale;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class Controller extends HttpServlet {

    private static final String SUPPORTED_LOCALES = "supportedLocales";
    private ControllerHelper controllerHelper;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("Controller.init");
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
        logger.info("Controller.doGet");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Controller.doPost");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        ICommand command = controllerHelper.getCommand(
                getPath(request), request.getParameter("command"));

        logger.info("command ==>   \n" + command);

        String path = command.execute(request, response);

        logger.info("path ==>   " + path);
        if (!path.equals(ICommand.REDIRECTED)) {
            logger.info("i am before forward");
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    private String getPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        logger.info("uri ==>   " + uri);
        return uri.replaceAll(request.getContextPath() + ResourceBundle.
                getBundle(Views.PAGES_BUNDLE).
                getString("site.prefix"), "");
    }
}
