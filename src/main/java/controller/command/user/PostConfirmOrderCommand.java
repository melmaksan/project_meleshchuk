package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import entity.Order;
import entity.Service;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;
import service.ServiceForService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.*;

public class PostConfirmOrderCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostConfirmOrderCommand.class);
    private final OrderService orderService = ServiceFactory.getOrderService();
    private static final UserService userService = ServiceFactory.getUserService();
    private final ServiceForService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Optional<User> spec = userService.findUserById(Long.parseLong(request.getParameter(SPEC_ID)));
        if (spec.isPresent()) {
            String dateTime = request.getParameter(DATA_TIME);
            Service service = serviceService.findServiceById(Long.parseLong(request.getParameter(SERVICE_ID)));
            checkIfSpecIsFree(dateTime, service, spec.get(), errors);
            if (errors.isEmpty()) {
                logger.info("PostConfirm doesn't have errors");
                orderService.createOrder(dateTime, spec.get().getId(), service.getId(),
                        Long.parseLong(request.getParameter(USER_ID)));
                Util.redirectTo(request, response, ResourceBundle
                        .getBundle(PAGES_BUNDLE).getString("user.orders"));
                logger.info("redirect to orders");
                return REDIRECTED;
            }
            return confirmView(request, errors, spec.get(), service);
        }
        return specialistView(request, errors);
    }

    private String specialistView(HttpServletRequest request, List<String> errors) {
        errors.add(USER_NOT_EXIST);
        request.setAttribute(ERRORS, errors);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("go to user specialist page");
        return USER_SPECIALISTS_VIEW;
    }

    private String confirmView(HttpServletRequest request, List<String> errors, User spec, Service service) {
        logger.info("PostConfirm HAS ERRORS");
        request.setAttribute(ERRORS, errors);
        request.setAttribute(USER, spec);
        request.setAttribute(SERVICE, service);
        return CONFIRMATION_VIEW;
    }

    private void checkIfSpecIsFree(String dateTime,
                                   Service service, User spec, List<String> errors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newOrderStart = LocalDateTime.parse(dateTime, formatter);
        logger.info("newOrderStart ==> " + newOrderStart);
        LocalDateTime newOrderEnd = newOrderStart.plusMinutes(service.getMinutes());
        logger.info("newOrderEnd ==> " + newOrderEnd);
        checkOnOrdersPerDay(errors, newOrderStart, newOrderEnd, spec);
    }

    private void checkOnOrdersPerDay(List<String> errors, LocalDateTime newOrderStart,
                                     LocalDateTime newOrderEnd, User spec) {
        List<Order> orders = userService.getBookedOrdersPerDay(spec,
                LocalDate.from(newOrderEnd), LocalDate.from(newOrderEnd).plusDays(1));
        logger.info("orders ==> " + orders);
        for (Order order : orders) {
            if (newOrderStart.isBefore(order.getTimeEnd()) && newOrderEnd.isAfter(order.getTimeStart())) {
                logger.info("order check ==> " + (newOrderStart.isBefore(order.getTimeEnd()) &&
                        newOrderEnd.isAfter(order.getTimeStart())));
                errors.add(SPEC_IS_BUSY);
                return;
            }
        }
    }

}
