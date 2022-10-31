package controller.command.visitor;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.LOGIN_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostLoginCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(PostLoginCommand.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle(PAGES_BUNDLE);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, bundle.
                    getString(HOME_PATH));
            return REDIRECTED;
        }
        User userDto = getDataFromRequest(request);
        List<String> errors = validateData(userDto);
        User user = loadUserFromDatabase(userDto, errors);
        if (errors.isEmpty()) {
            logger.info("LOGIN WITHOUT ERRORS!");
            addUserToSession(request.getSession(), user);
            Util.redirectTo(request, response, bundle.
                    getString(HOME_PATH));
            return REDIRECTED;
        }
        logger.info("LOGIN HAS ERRORS!");
        addInvalidDataToRequest(request, userDto, errors);
        return LOGIN_VIEW;
    }

    private User loadUserFromDatabase(User userDto, List<String> errors) {
        User user = userService.findByLogin(userDto.getLogin());
        if (user == null) {
            errors.add(USER_NOT_EXIST);
        }
        return user;
    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .addLogin(request.getParameter(LOGIN_PARAM))
                .addPassword(request.getParameter(PASSWORD_PARAM))
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);
        /* Check if entered password matches with user password in case,
            when email and password is valid
        */
        if (errors.isEmpty() && !userService.
                isCredentialsValid(user.getLogin(), user.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }
        return errors;
    }

    private void addUserToSession(HttpSession session, User user) {
        session.setAttribute(USER, user);
        session.setAttribute(STATUS, SUCCESS_STATUS);
    }

    private void addInvalidDataToRequest(HttpServletRequest request, User user, List<String> errors) {
        request.setAttribute(USER, user);
        request.setAttribute(ERRORS, errors);
    }
}
