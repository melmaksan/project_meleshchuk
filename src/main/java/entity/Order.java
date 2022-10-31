package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order implements Serializable {

    private static final String DEFAULT_STATUS = OrderStatus.StatusIdentifier.BOOKED.name();
    private static final int DEFAULT_STATUS_ID = OrderStatus.StatusIdentifier.BOOKED.getId();

    private static final String DEFAULT_PAYMENT_STATUS = PaymentStatus.PaymentIdentifier.UNPAID.name();
    private static final int DEFAULT_PAYMENT_STATUS_ID = PaymentStatus.PaymentIdentifier.UNPAID.getId();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private long id;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private List<Service> services;
    private User user;
    private List<User> specialists;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(List<User> specialists) {
        this.specialists = specialists;
    }

    public boolean isBooked() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.BOOKED.getId();
    }

    public boolean isDone() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.DONE.getId();
    }

    public boolean isCanceled() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.CANCELED.getId();
    }

    public void setDefaultOrderStatus() {
        this.orderStatus = new OrderStatus(DEFAULT_STATUS_ID, DEFAULT_STATUS);
    }

    public boolean isPaid() {
        return paymentStatus.getId() == PaymentStatus.PaymentIdentifier.PAID.getId();
    }

    public boolean isUnpaid() {
        return paymentStatus.getId() == PaymentStatus.PaymentIdentifier.UNPAID.getId();
    }

    public void setDefaultPaymentStatus() {
        this.paymentStatus = new PaymentStatus(DEFAULT_PAYMENT_STATUS_ID, DEFAULT_PAYMENT_STATUS);
    }

    public String getOrderTime() {
        return timeStart.format(dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }


    public static class Builder {

        private final Order order;

        private Builder() {
            order = new Order();
        }

        public Builder addId(long id) {
            order.setId(id);
            return this;
        }

        public Builder addOrderStatus(OrderStatus orderStatus) {
            order.setOrderStatus(orderStatus);
            return this;
        }

        public Builder addPaymentStatus(PaymentStatus paymentStatus) {
            order.setPaymentStatus(paymentStatus);
            return this;
        }

        public Builder addOrderTimeStart(LocalDateTime orderTime) {
            order.setTimeStart(orderTime);
            return this;
        }

        public Builder addOrderTimeEnd(LocalDateTime orderTime) {
            order.setTimeEnd(orderTime);
            return this;
        }

        public Builder addDefaultStatus() {
            order.setDefaultOrderStatus();
            return this;
        }

        public Builder addDefaultPaymentStatus() {
            order.setDefaultPaymentStatus();
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
