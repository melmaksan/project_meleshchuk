package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Respond implements Serializable {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private long id;
    private String userName;
    private LocalDateTime dateTime;
    private int mark;
    private String response;
    private List<User> users;
    private List<User> specialists;

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDateTime() {
        return dateTime.format(dateTimeFormatter);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(List<User> specialists) {
        this.specialists = specialists;
    }

    @Override
    public String toString() {
        return "Respond{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", dateTime=" + dateTime +
                ", mark=" + mark +
//                ", respond='" + respond + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Respond respond = (Respond) o;
        return id == respond.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

        public Builder addName(String name) {
            respond.setUserName(name);
            return this;
        }

        public Builder addRespond(String response) {
            respond.setResponse(response);
            return this;
        }

        public Builder addMark(int mark) {
            respond.setMark(mark);
            return this;
        }

        public Builder addRespondTime(LocalDateTime dateTime) {
            respond.setDateTime(dateTime);
            return this;
        }

        public Respond build() {
            return respond;
        }
    }
}
