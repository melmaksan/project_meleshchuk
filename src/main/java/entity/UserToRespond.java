package entity;

import java.io.Serializable;
import java.util.Objects;

public class UserToRespond implements Serializable {

    private long userId;
    private long respondId;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRespondId() {
        return respondId;
    }

    public void setRespondId(long respondId) {
        this.respondId = respondId;
    }

    @Override
    public String toString() {
        return "UserToRespond{" +
                "userId=" + userId +
                ", respondId=" + respondId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToRespond that = (UserToRespond) o;
        return userId == that.userId && respondId == that.respondId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, respondId);
    }

    public static class Builder {

        private final UserToRespond userToRespond;

        private Builder() {
            userToRespond = new UserToRespond();
        }

        public Builder addUserId(long userId) {
            userToRespond.setUserId(userId);
            return this;
        }

        public Builder addRespondId(long respondId) {
            userToRespond.setRespondId(respondId);
            return this;
        }


        public UserToRespond build() {
            return userToRespond;
        }
    }
}
