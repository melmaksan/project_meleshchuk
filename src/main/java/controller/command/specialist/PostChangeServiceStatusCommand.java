package controller.command.specialist;

import controller.command.ICommand;
import controller.util.Util;
import entity.Order;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Attributes.ORDERS;
import static controller.util.constants.Views.*;

public class PostChangeServiceStatusCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostChangeServiceStatusCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = orderService.findOrderById(Long.parseLong(request.getParameter(ORDER_ID)));
        List<String> errors = orderService.updateOrderStatus(order, Integer.parseInt
                (request.getParameter(ORDER_STATUS)));
        if (errors.isEmpty()) {
            logger.info("Order status has updated successfully!");
            Util.redirectTo(request, response, ResourceBundle.getBundle(PAGES_BUNDLE)
                    .getString("specialist.orders"));
            logger.info("redirect to orders");
            return REDIRECTED;
        }
        logger.info("Order can't be canceled because order has paid");
        request.setAttribute(ERRORS, errors);
        setSpecialistOrders(request);
        return SPECIALIST_ORDERS_VIEW;
    }

    private void setSpecialistOrders(HttpServletRequest request) {
        User specialist = getUserFromSession(request.getSession());
        List<Order> orders = userService.getOrders(specialist);
        specialist.setOrders(orders);
        request.setAttribute(ORDERS, orders);
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER);
    }
}
