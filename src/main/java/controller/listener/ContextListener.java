package controller.listener;

import controller.util.UpdateSpecRating;
import controller.util.mailer.SendEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ScheduledExecutorService emailScheduler = Executors.newSingleThreadScheduledExecutor();
        emailScheduler.scheduleAtFixedRate(new SendEmail(), 0, 1, TimeUnit.DAYS);

        logger.info("set emailScheduler");

        ScheduledExecutorService ratingScheduler = Executors.newSingleThreadScheduledExecutor();
        ratingScheduler.scheduleAtFixedRate(new UpdateSpecRating(), 0, 7, TimeUnit.DAYS);

        logger.info("update specialists rating");
    }
}
