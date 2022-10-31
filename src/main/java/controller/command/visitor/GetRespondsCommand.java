package controller.command.visitor;

import controller.command.HomeCommand;
import controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RespondService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.util.constants.Attributes.RESPONDS;
import static controller.util.constants.Views.RESPONDS_VIEW;

public class GetRespondsCommand implements ICommand {

    private static final RespondService respondService = ServiceFactory.getRespondService();
    private static final Logger logger = LogManager.getLogger(GetRespondsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HomeCommand.removeLogStatus(request);
        HomeCommand.removeRegStatus(request);
        request.setAttribute(RESPONDS, respondService.findAllRespond());
        logger.info("i am GetRespondsCommand");
        return RESPONDS_VIEW;
    }
}
