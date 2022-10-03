package entity;

import java.math.BigDecimal;
import java.util.List;

public class Service {

    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private List<User> users;
    private List<Order> orders;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Service{" +
                "ServiceNumber=" + id +
                ", title='" + title +
                ", description='" + description +
                ", cost=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    public static class Builder {

        private final Service service;

        private Builder() {
            service = new Service();
        }

        public Builder addServiceId(long id) {
            service.setId(id);
            return this;
        }

        public Builder addServiceName(String serviceName) {
            service.setTitle(serviceName);
            return this;
        }

        public Builder addDescription(String description) {
            service.setDescription(description);
            return this;
        }

        public Builder addPrice(BigDecimal price) {
            service.setPrice(price);
            return this;
        }

        public Builder addUsers(List<User> users) {
            service.setUsers(users);
            return this;
        }

        public Builder addOrders(List<Order> orders) {
            service.setOrders(orders);
            return this;
        }

        public Service build() {
            return service;
        }
    }
}
