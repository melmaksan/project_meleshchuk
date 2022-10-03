package dao.abstraction;

import entity.Order;

import java.time.LocalDate;

public interface OrderDao extends GenericDao<Order, Long> {

    /**
     * Change time of certain order.
     *
     * @param order order that changes
     * @param localDate another order time
     */
    void changeBookingTime(Order order, LocalDate localDate);

    /**
     * Update order status in database.
     *
     * @param order object to update
     * @param orderStatus identifier of status
     */
    void updateOrderStatus(Order order, long orderStatus);

    /**
     * Update order's payment status in database.
     *
     * @param order object to update
     * @param paymentStatus identifier of status
     */
    void updatePaymentStatus(Order order, long paymentStatus);

    int getNumberOfRows();
}
