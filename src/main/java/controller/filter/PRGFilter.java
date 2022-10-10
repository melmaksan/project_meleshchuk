package controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static controller.util.constants.Attributes.*;

public class PRGFilter implements Filter {

    private final Logger logger = LogManager.getLogger(PRGFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Hello, I`m PRG filter!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        request.setAttribute(ERRORS,session.getAttribute(ERRORS));
        session.removeAttribute(ERRORS);

        request.setAttribute(MESSAGES,session.getAttribute(MESSAGES));
        session.removeAttribute(MESSAGES);

        request.setAttribute(WARNING,session.getAttribute(WARNING));
        session.removeAttribute(WARNING);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
