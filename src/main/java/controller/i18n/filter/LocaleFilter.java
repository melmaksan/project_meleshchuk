package controller.i18n.filter;

import controller.i18n.SupportedLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

    private static final String LANG = "lang";
    private static final String LOCALE = "locale";
    private final Logger logger = LogManager.getLogger(LocaleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("I`m a LocaleFilter!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if(req.getParameter(LANG) != null) {
            replaceUserLocale(req);
        }
        if (req.getSession().getAttribute(LOCALE) == null) {
            setUserLocale(req);
        }
        chain.doFilter(request, response);
    }

    private void replaceUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String langParameter = request.getParameter(LANG);
        Locale locale = SupportedLocale.getLocaleOrDefault(langParameter);
        session.setAttribute(LOCALE, locale);
    }

    private void setUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }
}
