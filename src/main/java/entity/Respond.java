package entity;

import java.io.Serializable;
import java.util.Objects;

public class Respond implements Serializable {

    private long id;
    private String respond;
    private long userId;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Respond{" +
                "id=" + id +
                ", respond='" + respond + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Respond respond = (Respond) o;
        return id == respond.id;
    }

    public static class Builder {

        private final Respond respond;

        public Builder() {
            respond = new Respond();
        }

        public Builder addId(long id) {
            respond.setId(id);
            return this;
        }

        public Builder addRespond(String response) {
            respond.setRespond(response);
            return this;
        }

        public Builder addUserId(long userId) {
            respond.setUserId(userId);
            return this;
        }

        public Respond build() {
            return respond;
        }
    }
}
