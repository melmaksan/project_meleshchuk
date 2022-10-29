package controller.listener;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Objects;

import static controller.util.constants.Attributes.USER;

public class UniquenessCheckSessionListener implements HttpSessionListener {

    private final Logger logger = LogManager.getLogger(UniquenessCheckSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setMaxInactiveInterval(5 * 60);
        logger.debug("Created new session: " + sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        User user = (User) sessionEvent.getSession().getAttribute(USER);
        logger.debug("Destroyed session: " + sessionEvent.getSession().getId() +
                (Objects.nonNull(user) ? "; User: " + user.getLogin() + ";" : ";"));
    }
}
