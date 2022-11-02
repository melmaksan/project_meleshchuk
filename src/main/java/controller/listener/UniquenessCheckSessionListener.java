package controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UniquenessCheckSessionListener implements HttpSessionListener {

    private final Logger logger = LogManager.getLogger(UniquenessCheckSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setMaxInactiveInterval(5 * 60);
        logger.debug("Created new session");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        logger.debug("Destroyed session");
    }
}
