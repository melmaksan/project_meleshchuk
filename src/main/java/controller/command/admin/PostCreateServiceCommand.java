package controller.command.admin;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.validator.PriceValidator;
import controller.util.validator.TimeValidator;
import entity.Service;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

import static controller.util.constants.Attributes.*;
import static controller.util.constants.Attributes.SERVICES;
import static controller.util.constants.Views.*;

public class PostCreateServiceCommand implements ICommand {

    private static final ServiceForService services = ServiceFactory.getServiceService();
    private final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(PostCreateServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>(validateDataFromRequest(request));
        if (errors.isEmpty()) {
            services.createService(getDataFromRequestCreating(request));
            request.setAttribute(SERVICES_UNIQUE_TYPE, services.getUniqueServiceTypes
                    (services.findAllService()));
            request.setAttribute(SERVICES,services.findAllService());
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(PAGES_BUNDLE).getString(HOME_PATH));
            return REDIRECTED;
        }
        logger.info("creating service has errors");
        request.setAttribute(ERRORS, errors);
        request.setAttribute(SPECIALISTS, userService.findAllSpecialists());
        logger.info("SPECIALISTS ==> " + userService.findAllSpecialists());
        return CREATE_VIEW;
    }

    private Service getDataFromRequestCreating(HttpServletRequest request) {
        String serviceTitle = request.getParameter(SERVICE_TITLE);
        logger.info("title ==> " + request.getParameter(SERVICE_TITLE));
        String serviceType = request.getParameter(SERVICE_TYPE);
        logger.info("serviceType ==> " + request.getParameter(SERVICE_TYPE));
        BigDecimal price = new BigDecimal(request.getParameter(SERVICE_PRICE));
        logger.info("price ==> " + request.getParameter(SERVICE_PRICE));
        String serviceImage = request.getParameter(SERVICE_IMAGE);
        logger.info("serviceImage ==> " + request.getParameter(SERVICE_IMAGE));
        Time time = Time.valueOf(request.getParameter(DURATION));
        logger.info("time ==> " + time);
        String[] usersId = request.getParameterValues(SPEC_ID);
        logger.info("users ==> " + Arrays.toString(usersId));
        List<User> specialists = new ArrayList<>();
        for (String specId : usersId) {
            Optional<User> specialist = userService.findUserById(Long.parseLong(specId));
            specialist.ifPresent(specialists::add);
        }
        logger.info("specialists ==> " + specialists);
        return Service.newBuilder()
                .addServiceTitle(serviceTitle)
                .addServiceType(serviceType)
                .addPrice(price)
                .addImage(serviceImage)
                .addDuration(time)
                .addUsers(specialists)
                .build();
    }

    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new PriceValidator(),
                request.getParameter(SERVICE_PRICE), errors);
        Util.validateField(new TimeValidator(),
                request.getParameter(DURATION), errors);
        return errors;
    }
}

