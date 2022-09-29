package entity;

import java.util.Objects;

public class UserServices {

    private long id;
    private long userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "UserServices{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServices that = (UserServices) o;
        return id == that.id;
    }

    private static class Builder {

        private final UserServices userServices;

        public Builder() {
            userServices = new UserServices();
        }

        public Builder addId(long id) {
            userServices.setId(id);
            return this;
        }

        public Builder addUserId(long userId) {
            userServices.setUserId(userId);
            return this;
        }

        public Builder addServiceId(long serviceId) {
            userServices.setServiceId(serviceId);
            return this;
        }

        public UserServices build() {
            return userServices;
        }
    }
}
