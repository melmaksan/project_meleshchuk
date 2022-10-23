package controller.command.admin;

import controller.command.ICommand;
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

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.ADMIN_ORDERS_VIEW;

public class GetAllOrdersCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final Logger logger = LogManager.getLogger(GetAllOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrderStatus.StatusIdentifier> orderStatuses = Arrays.asList(OrderStatus.StatusIdentifier.values());
        List<PaymentStatus.PaymentIdentifier> paymentStatuses = Arrays.asList(PaymentStatus.PaymentIdentifier.values());
        logger.info("orderStatuses ==> " + orderStatuses);
        request.setAttribute(ORDER_STATUSES, orderStatuses);
        logger.info("paymentStatuses ==> " + paymentStatuses);
        request.setAttribute(PAYMENT_STATUSES, paymentStatuses);
        logger.info("orders ==> " + orderService.findAllOrders());
        request.setAttribute(ORDERS, orderService.findAllOrders());
        return ADMIN_ORDERS_VIEW;
    }
}
