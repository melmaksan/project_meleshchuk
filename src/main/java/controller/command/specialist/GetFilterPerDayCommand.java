package controller.command.specialist;

import controller.command.ICommand;
import entity.Order;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.SPECIALIST_ORDERS_VIEW;

public class GetFilterPerDayCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetFilterPerDayCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User specialist = getUserFromSession(request.getSession());
        LocalDate date =  LocalDate.parse(request.getParameter(DATA_TIME));
        logger.info("dateTime ==> " + date);
        List<Order> orders = userService.getOrdersPerDay
                (specialist,  date, date.plusDays(1));
        specialist.setOrders(orders);
        request.setAttribute(DATE, date);
        request.setAttribute(ORDERS, orders);
        logger.info("I am GetFilterPerDayCommand");
        return SPECIALIST_ORDERS_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER);
    }
}
