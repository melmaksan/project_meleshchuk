package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.validator.LoginValidator;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RespondService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Views.CREATE_RESPOND_VIEW;
import static controller.util.constants.Views.PAGES_BUNDLE;

public class PostCreateRespondCommand implements ICommand {

    private final RespondService respondService = ServiceFactory.getRespondService();
    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostCreateRespondCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>(validateEmailFromRequest(request));
        User user = userService.findByLogin(request.getParameter(LOGIN_PARAM));
        try {
            Objects.requireNonNull(user);
        } catch (NumberFormatException ex) {
            logger.error("There is no user", ex);
            errors.add(USER_NOT_EXIST);
            return returnToCreate(request, errors);
        }
        int rate = checkRate(request, errors);
        if (errors.isEmpty()) {
            checkUserName(request, user);
            respondService.createRespond(user.getFirstName(),
                    LocalDateTime.now(), rate,
                    request.getParameter(RESPOND_MESSAGE),
                    user.getId(),
                    Long.parseLong(request.getParameter(SPEC_ID)));
            request.setAttribute(RESPONDS, respondService.findAllRespond());
            Util.redirectTo(request, response, ResourceBundle
                    .getBundle(PAGES_BUNDLE).getString("responds"));
            logger.info("redirect to responds");
            return REDIRECTED;
        }
        return returnToCreate(request, errors);
    }

    private String returnToCreate(HttpServletRequest request, List<String> errors) {
        logger.info("creating respond has errors");
        request.setAttribute(ERRORS, errors);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        return CREATE_RESPOND_VIEW;
    }

    private int checkRate(HttpServletRequest request, List<String> errors) {
        int rate = 0;
        try {
            rate = Integer.parseInt(request.getParameter(RESPOND_MARK));
        } catch (NumberFormatException ex) {
            logger.error(NO_RATE, ex);
            errors.add(NO_RATE);
        }
        return rate;
    }

    private void checkUserName(HttpServletRequest request, User user) {
        String name = request.getParameter(RESPOND_NAME);
        if (name.length() != 0) {
            user.setFirstName(name);
        } else {
            user.setFirstName(user.getFirstName() + " " + user.getLastName());
        }
    }



    private List<String> validateEmailFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new LoginValidator(),
                request.getParameter(LOGIN_PARAM), errors);
        return errors;
    }
}
