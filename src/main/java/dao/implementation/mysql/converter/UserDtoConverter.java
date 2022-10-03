package dao.implementation.mysql.converter;

import entity.Order;
import entity.Role;
import entity.Service;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDtoConverter implements DtoConverter<User> {

    private final static String ID_FIELD = "user_id";
    private final static String FIRST_NAME = "first_name";
    private final static String LAST_NAME = "last_name";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";
    private final static String PHONE = "phone_num";
    private final static String RATING = "rate";
    private final DtoConverter<Role> roleConverter;

    public UserDtoConverter() {
        this( new RoleDtoConverter());
    }

    public UserDtoConverter(DtoConverter<Role> roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public User convertToObject(ResultSet resultSet) throws SQLException {
        Role role = roleConverter.convertToObject(resultSet);
        return User.newBuilder()
                .addId(resultSet.getInt(ID_FIELD))
                .addFirstName(resultSet.getString(FIRST_NAME))
                .addLastName(resultSet.getString(LAST_NAME))
                .addLogin(resultSet.getString(LOGIN))
                .addPassword(resultSet.getString(PASSWORD))
                .addEmail(resultSet.getString(EMAIL))
                .addPhoneNumber(resultSet.getString(PHONE))
                .addRating(resultSet.getFloat(RATING))
                .addRole(role)
                .build();
    }

    private List<Order> getOrders(String concatOrders) {
        return Stream.of(concatOrders.split(",")).map(split ->
                Order.newBuilder().addId(Integer.parseInt(split)).build()).collect(Collectors.toList());
    }

    private List<Service> getServices(String concatServices) {
        return Stream.of(concatServices.split(",")).map(split ->
                Service.newBuilder().addServiceName(split).build()).collect(Collectors.toList());
    }
}
