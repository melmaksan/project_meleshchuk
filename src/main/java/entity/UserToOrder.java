package entity;

import java.io.Serializable;
import java.util.Objects;

public class UserToOrder implements Serializable {

    private long userId;
    private long orderId;

    public static Builder newBuilder() {
        return new Builder();
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
        return userId == that.userId && orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderId);
    }

    public static class Builder {

        private final UserToOrder userToOrder;

        private Builder() {
            userToOrder = new UserToOrder();
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

