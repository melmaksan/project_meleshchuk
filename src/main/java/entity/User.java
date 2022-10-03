package entity;

import java.util.List;

public class User {

    private final static String DEFAULT_ROLE = Role.RoleIdentifier.USER_ROLE.name();
    private final static int DEFAULT_ROLE_ID = Role.RoleIdentifier.USER_ROLE.getId();

    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private float rating;
    private Role role;
    private List<Service> services;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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

    public boolean isUser() {
        return role.getId() == Role.RoleIdentifier.USER_ROLE.getId();
    }

    public boolean isAdmin() {
        return role.getId() == Role.RoleIdentifier.ADMIN_ROLE.getId();
    }

    public boolean isSpecialist() {
        return role.getId() == Role.RoleIdentifier.SPECIALIST_ROLE.getId();
    }

    public void setDefaultRole() {
        this.role = new Role(DEFAULT_ROLE_ID, DEFAULT_ROLE);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", rating=" + rating +
                ", role=" + role +
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

        public Builder addEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder addPhoneNumber(String phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder addRating(float rating) {
            user.setRating(rating);
            return this;
        }

        public Builder addRole(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder addDefaultRole() {
            user.setDefaultRole();
            return this;
        }

        public Builder addDefaultRate() {
            user.setRating(0);
            return this;
        }

        public Builder addServices(List<Service> services) {
            user.setServices(services);
            return this;
        }

        public Builder addOrders(List<Order> orders) {
            user.setOrders(orders);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
