package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostAddOrderCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostAddOrderCommand.class);
    private final OrderService orderService = ServiceFactory.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("i am in AddOrder");
        orderService.createOrder(request.getParameter(DATA_TIME),
                Long.parseLong(request.getParameter(USER_ID)),
                Long.parseLong(request.getParameter(SPEC_ID)),
                Long.parseLong(request.getParameter(SERVICE_ID)));
        Util.redirectTo(request, response, ResourceBundle
                .getBundle(PAGES_BUNDLE).getString("user.orders"));
        logger.info("redirect to orders");
        return REDIRECTED;
    }
}
