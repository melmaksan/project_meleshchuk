package controller.command.user;

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

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.USER_ORDERS_VIEW;

public class GetUserOrdersCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetUserOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = getUserFromSession(request.getSession());
        List<Order> orders = userService.getOrders(currentUser);
        for (Order order : orders) {
            logger.info("specs ==> " + order.getSpecialists());
        }
        currentUser.setOrders(orders);
        request.setAttribute(ORDERS, orders);
        logger.info("I am getUserOrders command");
        return USER_ORDERS_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER);
    }
}
