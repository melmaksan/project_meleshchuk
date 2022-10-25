package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
import entity.Order;
import entity.OrderStatus;
import entity.PaymentStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.ADMIN_ORDERS_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostChangeOrderStatusCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final Logger logger = LogManager.getLogger(PostChangeOrderStatusCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = orderService.findOrderById(Long.parseLong(request.getParameter(ORDER_ID)));
        logger.info("order ==> " + order);
        List<String> errors = orderService.updateOrderStatus(order, Integer.parseInt
                (request.getParameter(ORDER_STATUS)));
        logger.info("orderStatus ==> " + Integer.parseInt(request.getParameter(ORDER_STATUS)));
        if (errors.isEmpty()) {
            logger.info("Order status has updated successfully!");
            Util.redirectTo(request, response, ResourceBundle.getBundle(PAGES_BUNDLE)
                    .getString("admin.orders"));
            logger.info("redirect to orders");
            return REDIRECTED;
        }
        logger.info("Order can't be canceled because order has paid");
        addStatuses(request);
        request.setAttribute(ERRORS, errors);
        request.setAttribute(ORDERS, orderService.findAllOrders());
        return ADMIN_ORDERS_VIEW;
    }

    private void addStatuses(HttpServletRequest request) {
        List<OrderStatus.StatusIdentifier> orderStatuses = Arrays.asList
                (OrderStatus.StatusIdentifier.values());
        logger.info("orderStatuses ==> " + orderStatuses);
        List<PaymentStatus.PaymentIdentifier> paymentStatuses = Arrays.asList
                (PaymentStatus.PaymentIdentifier.values());
        logger.info("paymentStatuses ==> " + paymentStatuses);
        request.setAttribute(ORDER_STATUSES, orderStatuses);
        request.setAttribute(PAYMENT_STATUSES, paymentStatuses);
    }
}
