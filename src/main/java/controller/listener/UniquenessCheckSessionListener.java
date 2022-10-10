package controller.listener;

import controller.util.constants.Attributes;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.Objects;

public class UniquenessCheckSessionListener implements HttpSessionListener {

    private final Logger logger = LogManager.getLogger(UniquenessCheckSessionListener.class);

    private static int totalActiveSessions;

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        totalActiveSessions++;
        sessionEvent.getSession().setMaxInactiveInterval(5 * 60);
        logger.debug("Created new session: " + sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        totalActiveSessions--;
        ServletContext context = sessionEvent.getSession().getServletContext();
        User user = (User) sessionEvent.getSession().getAttribute(Attributes.USER);
        @SuppressWarnings("unchecked")
        Map<String, User> activeUserList = (Map<String, User>)
                context.getAttribute(Attributes.USER_LIST);
        logger.debug("Destroyed session: " + sessionEvent.getSession().getId() +
                (Objects.nonNull(user) ? "; User: " + user.getLogin() + ";" : ";"));
        activeUserList.remove(user.getLogin());
    }
}
