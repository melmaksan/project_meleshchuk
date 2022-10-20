package entity;

import java.io.Serializable;

public class OrderToService implements Serializable {

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
        return "OrderToService{" +
                "orderId=" + orderId +
                ", serviceId=" + serviceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderToService that = (OrderToService) o;
        return id == that.id;
    }

     public static class Builder {

        private final OrderToService orderToService;

        public Builder() {
            orderToService = new OrderToService();
        }

        public Builder addId(long id) {
            orderToService.setId(id);
            return this;
        }

        public Builder addOrderId(long orderId) {
            orderToService.setOrderId(orderId);
            return this;
        }

        public Builder addServiceId(long serviceId) {
            orderToService.setServiceId(serviceId);
            return this;
        }

        public OrderToService build() {
            return orderToService;
        }
    }
}
