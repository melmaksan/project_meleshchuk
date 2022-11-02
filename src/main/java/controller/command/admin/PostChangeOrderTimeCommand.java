package controller.command.admin;

import controller.command.ICommand;
import controller.command.user.PostConfirmOrderCommand;
import controller.util.Util;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Attributes.PAYMENT_STATUSES;
import static controller.util.constants.Views.ADMIN_ORDERS_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostChangeOrderTimeCommand implements ICommand {

    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostChangeOrderTimeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Order order = orderService.findOrderById(Long.parseLong(request.getParameter(ORDER_ID)));
        String dateTime = request.getParameter(DATA_TIME);
        checkIfSpecIsFree(dateTime, order, errors);
        if (errors.isEmpty()) {
            errors.addAll(orderService.changeBookingTime(order, dateTime));
            if (errors.isEmpty()) {
                logger.info("Order time has changed successfully!");
                Util.redirectTo(request, response, ResourceBundle.getBundle(PAGES_BUNDLE)
                        .getString("admin.orders"));
                logger.info("redirect to orders");
                return REDIRECTED;
            }
            return getOrdersView(request, errors, "You can't change order time " +
                    "because order has already done or canceled!");
        }
        return getOrdersView(request, errors, "You can't change order time because " +
                "spec is busy on this time!");
    }

    private String getOrdersView(HttpServletRequest request, List<String> errors, String s) {
        logger.info(s);
        addStatuses(request);
        request.setAttribute(ERRORS, errors);
        servicePagination(request);
        return ADMIN_ORDERS_VIEW;
    }

    private void checkIfSpecIsFree(String dateTime, Order order, List<String> errors) {
        List<Service> services = order.getServices();
        List<User> specialists = order.getSpecialists();
        LocalDateTime newOrderStart = LocalDateTime.parse(dateTime);
        logger.info("i set newOrderStart");
        for (Service service : services) {
            LocalDateTime newOrderEnd = newOrderStart.plusMinutes(service.getMinutes());
            for (User spec : specialists) {
                checkOnOrdersPerDay(errors, newOrderStart, newOrderEnd, spec);
            }
        }

    }

    private void checkOnOrdersPerDay(List<String> errors, LocalDateTime newOrderStart,
                                     LocalDateTime newOrderEnd, User spec) {
        PostConfirmOrderCommand.checkOrders(errors, newOrderStart, newOrderEnd, spec, userService);
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
