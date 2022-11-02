package controller.util;

import entity.Respond;
import entity.User;
import service.ServiceFactory;
import service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;

public class UpdateSpecRating implements Runnable {

    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public void run() {
        updateRate();
    }

    public static void updateRate() {
        List<User> specialists = userService.findAllSpecialists();
        for (User spec : specialists) {
            List<Respond> responds = userService.getResponds(spec);
            OptionalDouble rate = responds.stream().mapToDouble(Respond::getMark).average();
            if (rate.isPresent()) {
                double rating = formatRate(rate.getAsDouble());
                userService.updateRating(spec, rating);
            } else {
                userService.updateRating(spec, 0);
            }
        }
    }

    private static double formatRate(double rate) {
        BigDecimal bigDecimal = BigDecimal.valueOf(rate);
        bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN);
        return bigDecimal.doubleValue();
    }
}
