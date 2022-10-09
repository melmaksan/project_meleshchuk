package controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ICommand {

    /**
     * Return from execute in case when redirecting action happens
     */
    String REDIRECTED ="REDIRECTED";

    String execute(HttpServletRequest request,
                   HttpServletResponse response)
            throws ServletException, IOException;
}
