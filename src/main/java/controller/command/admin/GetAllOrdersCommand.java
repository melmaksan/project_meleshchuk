package controller.command.admin;

import controller.command.HomeCommand;
import controller.command.ICommand;
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

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.ADMIN_ORDERS_VIEW;

public class GetAllOrdersCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final Logger logger = LogManager.getLogger(GetAllOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        List<OrderStatus.StatusIdentifier> orderStatuses = Arrays.asList
                (OrderStatus.StatusIdentifier.values());
        List<PaymentStatus.PaymentIdentifier> paymentStatuses = Arrays.asList
                (PaymentStatus.PaymentIdentifier.values());
        request.setAttribute(ORDER_STATUSES, orderStatuses);
        request.setAttribute(PAYMENT_STATUSES, paymentStatuses);
        pagination(request, orderService);
        logger.info("i am GetAllOrdersCommand");
        return ADMIN_ORDERS_VIEW;
    }

    static void pagination(HttpServletRequest request, OrderService orderService) {
        int page = 1;
        int recordsPerPage = 2;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<Order> list = orderService.findAll(recordsPerPage, (page - 1) *
                recordsPerPage);
        int numberOfRows = orderService.getNumberOfRows();
        int numberOfPages = (int) Math.ceil(numberOfRows * 1.0 / recordsPerPage);
        request.setAttribute(ORDERS, list);
        request.setAttribute(NUM_OF_PAGE, numberOfPages);
        request.setAttribute(CURR_PAGE, page);
    }
}
