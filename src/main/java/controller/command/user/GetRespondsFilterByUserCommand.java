package controller.command.user;

import controller.command.ICommand;
import entity.Respond;
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
import java.util.List;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.RESPONDS_VIEW;

public class GetRespondsFilterByUserCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetRespondsFilterByUserCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());
        logger.info("user responds ==> " + currentUser.getResponds());
        List<Respond> responds = userService.getResponds(currentUser);
        logger.info("responds ==> " + responds);
        currentUser.setResponds(responds);
        logger.info("user responds ==> " + currentUser.getResponds());
        request.setAttribute(RESPONDS, responds);
        logger.info("I am GetUserRespondsCommand");
        return RESPONDS_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER);
    }
}
