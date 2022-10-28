package controller.util.mailer;

import entity.Order;
import entity.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

public class SendEmail implements Runnable {

    private static final OrderService orderService = ServiceFactory.getOrderService();
    private static final int STATUS = OrderStatus.StatusIdentifier.DONE.getId();
    private static final Logger logger = LogManager.getLogger(SendEmail.class);

    @Override
    public void run() {
        //sendEmailForReviews();
    }

    private void sendEmailForReviews() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        try {
            List<Order> orders = orderService.findOrderByForFeedback(dateFrom, dateTo, STATUS);
            for (Order order : orders) {
                String header = "Dear " + order.getUser().getFirstName() + " " + order.getUser().getLastName() +
                        ", we are waiting for your review!";
                String body = "Hello! It`s beauty Salon. Please, provide your feedback for your last appointment. " +
                        "You can do it by logging in to our website or by click on the link: " +
                        "http://localhost:8080/beauty_salon/site/user/create_respond";

                MailUtil.sendMessage(order.getUser().getLogin(), header, body);
            }
        } catch (MessagingException e) {
            logger.error("Email doesn't sent", e);
        }
    }
}
