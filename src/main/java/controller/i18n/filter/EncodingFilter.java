package controller.i18n.filter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String ENCODING = "utf-8";
    private final Logger logger = LogManager.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("I`m a EncodingFilter!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }
}
