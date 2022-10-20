package entity;

import java.io.Serializable;

public class UserToService implements Serializable {

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
        UserToService that = (UserToService) o;
        return id == that.id;
    }

    public static class Builder {

        private final UserToService userToService;

        public Builder() {
            userToService = new UserToService();
        }

        public Builder addId(long id) {
            userToService.setId(id);
            return this;
        }

        public Builder addUserId(long userId) {
            userToService.setUserId(userId);
            return this;
        }

        public Builder addServiceId(long serviceId) {
            userToService.setServiceId(serviceId);
            return this;
        }

        public UserToService build() {
            return userToService;
        }
    }
}
