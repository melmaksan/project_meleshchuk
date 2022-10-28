package entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String phoneNumber;
    private double rating;
    private Role role;
    private List<Service> services;
    private List<Order> orders;
    private List<Respond> responds;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Respond> getResponds() {
        return responds;
    }

    public void setResponds(List<Respond> responds) {
        this.responds = responds;
    }

    public boolean isUser() {
        return role.getId() == getUserId(Role.RoleIdentifier.USER);
    }

    public boolean isAdmin() {
        return role.getId() == getUserId(Role.RoleIdentifier.ADMIN);
    }

    public boolean isSpecialist() {
        return role.getId() == getUserId(Role.RoleIdentifier.SPECIALIST);
    }

    public void setUserRole() {
        this.role = new Role(getUserId(Role.RoleIdentifier.USER), getName(Role.RoleIdentifier.USER));
    }

    public void setAdminRole() {
        this.role = new Role(getUserId(Role.RoleIdentifier.ADMIN), getName(Role.RoleIdentifier.ADMIN));
    }

    public void setSpecialistRole() {
        this.role = new Role(getUserId(Role.RoleIdentifier.SPECIALIST), getName(Role.RoleIdentifier.SPECIALIST));
    }

    private int getUserId(Role.RoleIdentifier user) {
        return user.getId();
    }

    private String getName(Role.RoleIdentifier user) {
        return user.name();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
//                ", login='" + login + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", rating=" + rating +
//                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }


    public static class Builder {

        private final User user;

        private Builder() {
            user = new User();
        }

        public Builder addId(long id) {
            user.setId(id);
            return this;
        }

        public Builder addFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder addLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder addLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder addPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder addPhoneNumber(String phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder addRating(double rating) {
            user.setRating(rating);
            return this;
        }

        public Builder addRole(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder addDefaultRate() {
            user.setRating(0);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
