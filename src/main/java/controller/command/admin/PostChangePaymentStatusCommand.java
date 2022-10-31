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

public class PostChangePaymentStatusCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final Logger logger = LogManager.getLogger(PostChangePaymentStatusCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = orderService.findOrderById(Long.parseLong(request.getParameter(ORDER_ID)));
        List<String> errors = orderService.updatePaymentStatus(order,
                Integer.parseInt(request.getParameter(PAYMENT_STATUS)));
        if (errors.isEmpty()) {
            logger.info("Payment status has updated successfully!");
            Util.redirectTo(request, response, ResourceBundle.getBundle(PAGES_BUNDLE)
                    .getString("admin.orders"));
            logger.info("redirect to orders");
            return REDIRECTED;
        }
        logger.info("You can't change payment status because order has already paid!");
        addStatuses(request);
        request.setAttribute(ERRORS, errors);
        servicePagination(request);
        return ADMIN_ORDERS_VIEW;
    }

    private void addStatuses(HttpServletRequest request) {
        List<OrderStatus.StatusIdentifier> orderStatuses = Arrays.asList
                (OrderStatus.StatusIdentifier.values());
        List<PaymentStatus.PaymentIdentifier> paymentStatuses = Arrays.asList
                (PaymentStatus.PaymentIdentifier.values());
        request.setAttribute(ORDER_STATUSES, orderStatuses);
        request.setAttribute(PAYMENT_STATUSES, paymentStatuses);
    }

    private void servicePagination(HttpServletRequest request) {
        GetAllOrdersCommand.pagination(request, orderService);
    }
}
