package dao.abstraction;

import entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {

    /**
     * Change time of certain order.
     *
     * @param order order that changes
     * @param localDateTime another order time
     */
    void changeBookingTime(Order order, LocalDateTime localDateTime);

    /**
     * Update order status in database.
     *
     * @param order object to update
     * @param orderStatus identifier of status
     */
    void updateOrderStatus(Order order, int orderStatus);

    /**
     * Update order's payment status in database.
     *
     * @param order object to update
     * @param paymentStatus identifier of status
     */
    void updatePaymentStatus(Order order, int paymentStatus);

    int getNumberOfRows();

    List<Order> findAllWithCredentials(LocalDate dateFrom, LocalDate dateTo, int status);
}
