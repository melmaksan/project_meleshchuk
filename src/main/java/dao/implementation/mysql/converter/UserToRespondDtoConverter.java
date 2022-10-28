package dao.implementation.mysql.converter;

import entity.UserToRespond;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserToRespondDtoConverter implements DtoConverter<UserToRespond>{

    private final static String RESPOND_ID = "respond_id";
    private final static String USER_ID = "user_id";

    @Override
    public UserToRespond convertToObject(ResultSet resultSet) throws SQLException {
        return UserToRespond.newBuilder()
                .addRespondId(resultSet.getLong(RESPOND_ID))
                .addUserId(resultSet.getLong(USER_ID))
                .build();
    }
}
