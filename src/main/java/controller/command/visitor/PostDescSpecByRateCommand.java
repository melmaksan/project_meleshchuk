package controller.command.visitor;

import controller.command.ICommand;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.SPECIALISTS;
import static controller.util.constants.Views.SPECIALISTS_VIEW;

public class PostDescSpecByRateCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(SPECIALISTS, userService.descByRating());
        return SPECIALISTS_VIEW;
    }
}
