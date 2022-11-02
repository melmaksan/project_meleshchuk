package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Service implements Serializable {

    DateTimeFormatter formatTimeNow=DateTimeFormatter.ofPattern("HH:mm");

    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Time duration;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getMinutes() {
        String time = getDuration().toString();
        String[] hourMin = time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int minutes = Integer.parseInt(hourMin[1]);
        int hoursInMinutes = hour * 60;
        return hoursInMinutes + minutes;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

        public Builder addServiceTitle(String serviceName) {
            service.setTitle(serviceName);
            return this;
        }

        public Builder addServiceType(String description) {
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

        public Builder addImage(String image) {
            service.setImage(image);
            return this;
        }

        public Builder addDuration(Time duration) {
            service.setDuration(duration);
            return this;
        }

        public Service build() {
            return service;
        }
    }

}
