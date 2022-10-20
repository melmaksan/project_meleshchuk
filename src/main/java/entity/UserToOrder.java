package entity;

import java.io.Serializable;
import java.util.Objects;

public class UserToOrder implements Serializable {

    private long id;
    private long userId;
    private long orderId;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "UserToOrder{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToOrder that = (UserToOrder) o;
        return id == that.id;
    }

    public static class Builder {

        private final UserToOrder userToOrder;

        private Builder() {
            userToOrder = new UserToOrder();
        }

        public Builder addId(long id) {
            userToOrder.setId(id);
            return this;
        }

        public Builder addUserId(long userId) {
            userToOrder.setUserId(userId);
            return this;
        }

        public Builder addOrderId(long orderId) {
            userToOrder.setOrderId(orderId);
            return this;
        }

        public UserToOrder build() {
            return userToOrder;
        }

    }
}

