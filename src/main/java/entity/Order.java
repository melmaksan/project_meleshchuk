package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order {

    private final static String DEFAULT_STATUS = OrderStatus.StatusIdentifier.BOOKED_STATUS.name();
    private final static int DEFAULT_STATUS_ID = OrderStatus.StatusIdentifier.BOOKED_STATUS.getId();

    private final static String DEFAULT_PAYMENT_STATUS = PaymentStatus.PaymentIdentifier.UNPAID_STATUS.name();
    private final static int DEFAULT_PAYMENT_STATUS_ID = PaymentStatus.PaymentIdentifier.UNPAID_STATUS.getId();

    private long id;
    private User user;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDate orderTime;
    private List<Service> services;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public boolean isBooked() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.BOOKED_STATUS.getId();
    }

    public boolean isDone() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.DONE_STATUS.getId();
    }

    public boolean isCanceled() {
        return orderStatus.getId() == OrderStatus.StatusIdentifier.CANCELED_STATUS.getId();
    }

    public void setDefaultOrderStatus() {
        this.orderStatus = new OrderStatus(DEFAULT_STATUS_ID, DEFAULT_STATUS);
    }

    public boolean isPaid() {
        return paymentStatus.getId() == PaymentStatus.PaymentIdentifier.PAID_STATUS.getId();
    }

    public boolean isUnpaid() {
        return paymentStatus.getId() == PaymentStatus.PaymentIdentifier.UNPAID_STATUS.getId();
    }

    public void setDefaultPaymentStatus() {
        this.paymentStatus = new PaymentStatus(DEFAULT_PAYMENT_STATUS_ID, DEFAULT_PAYMENT_STATUS);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", orderTime=" + orderTime +
                ", services=" + services +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }


    private static class Builder {

        private final Order order;

        private Builder() {
            order = new Order();
        }

        public Builder addId(long id) {
            order.setId(id);
            return this;
        }

        public Builder addUser(User user) {
            order.setUser(user);
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

        public Builder addOrderTime(LocalDate orderTime) {
            order.setOrderTime(orderTime);
            return this;
        }

        public Builder addServices(List<Service> services) {
            order.setServices(services);
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
