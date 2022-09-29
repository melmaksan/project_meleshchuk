package entity;

import java.util.Objects;

public class OrderServices {

    private long id;
    private long orderId;
    private long serviceId;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "OrderServices{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", serviceId=" + serviceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderServices that = (OrderServices) o;
        return id == that.id;
    }

    private static class Builder {

        private final OrderServices orderServices;

        public Builder() {
            orderServices = new OrderServices();
        }

        public Builder addId(long id) {
            orderServices.setId(id);
            return this;
        }

        public Builder addOrderIdId(long orderId) {
            orderServices.setOrderId(orderId);
            return this;
        }

        public Builder addServiceId(long serviceId) {
            orderServices.setServiceId(serviceId);
            return this;
        }

        public OrderServices build() {
            return orderServices;
        }
    }
}
