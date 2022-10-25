package controller.command.specialist;

import controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Views.SPECIALIST_SCHEDULE_VIEW;

public class GetSpecialistScheduleCommand implements ICommand {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(GetSpecialistScheduleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("i`m in GetSpecialistScheduleCommand");
        return SPECIALIST_SCHEDULE_VIEW;
    }
}
