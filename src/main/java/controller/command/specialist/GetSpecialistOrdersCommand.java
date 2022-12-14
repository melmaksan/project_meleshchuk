package controller.command.specialist;

import controller.command.HomeCommand;
import controller.command.ICommand;
import entity.Order;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static controller.util.constants.Attributes.ORDERS;
import static controller.util.constants.Attributes.USER;
import static controller.util.constants.Views.SPECIALIST_ORDERS_VIEW;

public class GetSpecialistOrdersCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetSpecialistOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HomeCommand.removeLogStatus(request);
        User specialist = getUserFromSession(request.getSession());
        List<Order> orders = userService.getOrders(specialist);
        specialist.setOrders(orders);
        request.setAttribute(ORDERS, orders);
        logger.info("I am GetSpecialistOrders command");
        return SPECIALIST_ORDERS_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER);
    }
}
