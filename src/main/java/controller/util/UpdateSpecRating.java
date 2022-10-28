package controller.util;

import controller.util.mailer.SendEmail;
import entity.Respond;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;

public class UpdateSpecRating implements Runnable {

    private static final UserService userService = ServiceFactory.getUserService();
    private static final Logger logger = LogManager.getLogger(SendEmail.class);

    @Override
    public void run() {
        updateRate();
    }

    public static void updateRate() {
        int i = 0;
        List<User> specialists = userService.findAllSpecialists();
        for (User spec : specialists) {
            List<Respond> responds = userService.getResponds(spec);
            logger.info("responds ==> " + responds);
            OptionalDouble rate = responds.stream().mapToDouble(Respond::getMark).average();
            if (rate.isPresent()) {
                i++;
                double rating = formatRate(rate.getAsDouble());
                logger.info("for user" + i + " rate is " + rating);
                userService.updateRating(spec, rating);
            } else {
                i++;
                logger.info("for user" + i + " rate is " + 0);
                userService.updateRating(spec, 0);
            }
        }
    }

    private static double formatRate(double rate) {
        MathContext mathContext = new MathContext(15, RoundingMode.HALF_UP); // для double
        BigDecimal bigDecimal = new BigDecimal(rate, mathContext);
        bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN);
        return bigDecimal.doubleValue();
    }
}
