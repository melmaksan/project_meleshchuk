package dao.implementation.mysql.converter;

import entity.Role;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDtoConverter implements DtoConverter<User> {

    private static final String ID_FIELD = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone_num";
    private static final String RATING = "rate";
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
                .addId(resultSet.getLong(ID_FIELD))
                .addFirstName(resultSet.getString(FIRST_NAME))
                .addLastName(resultSet.getString(LAST_NAME))
                .addLogin(resultSet.getString(LOGIN))
                .addPassword(resultSet.getString(PASSWORD))
                .addPhoneNumber(resultSet.getString(PHONE))
                .addRating(resultSet.getFloat(RATING))
                .addRole(role)
                .build();
    }
}
