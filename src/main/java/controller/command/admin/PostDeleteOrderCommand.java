package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
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

public class PostDeleteOrderCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final Logger logger = LogManager.getLogger(PostDeleteOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = orderService.deleteOrder(Long.parseLong(request.getParameter(ORDER_ID)));
        logger.info("errors ==> " + errors);
        if (errors.isEmpty()) {
            logger.info("DELETE WITHOUT ERRORS!");
            setAttribute(request);
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString("admin.orders"));
            return REDIRECTED;
        }
        logger.info("Order has already booked or paid");
        request.setAttribute(ERRORS, errors);
        setAttribute(request);
        return ADMIN_ORDERS_VIEW;
    }

    private void setAttribute(HttpServletRequest request) {
        List<OrderStatus.StatusIdentifier> orderStatuses = Arrays.asList
                (OrderStatus.StatusIdentifier.values());
        List<PaymentStatus.PaymentIdentifier> paymentStatuses = Arrays.asList
                (PaymentStatus.PaymentIdentifier.values());
        request.setAttribute(ORDER_STATUSES, orderStatuses);
        request.setAttribute(PAYMENT_STATUSES, paymentStatuses);
        request.setAttribute(ORDERS, orderService.findAllOrders());
    }
}
