package dao.implementation.mysql.converter;


import entity.Respond;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RespondDtoConverter implements DtoConverter<Respond>{

    private final static String ID_FIELD = "respond_id";
    private final static String RESPONSE = "respond";
    private final static String USER_ID = "user_id";



    @Override
    public Respond convertToObject(ResultSet resultSet) throws SQLException {
        return Respond.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addRespond(resultSet.getString(RESPONSE))
                .addUserId(resultSet.getLong(USER_ID))
                .build();
    }
}
