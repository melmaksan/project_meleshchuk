package dao.implementation.mysql.converter;

import entity.UserToService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserToServiceDtoConverter implements DtoConverter<UserToService> {

    private static final String USER_ID = "user_id";
    private static final String SERVICE_ID = "service_id";

    @Override
    public UserToService convertToObject(ResultSet resultSet) throws SQLException {
        return UserToService.newBuilder()
                .addUserId(resultSet.getLong(USER_ID))
                .addServiceId(resultSet.getLong(SERVICE_ID))
                .build();
    }
}
