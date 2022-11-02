package dao.implementation.mysql.converter;


import entity.Respond;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class RespondDtoConverter implements DtoConverter<Respond>{

    private static final String ID_FIELD = "id";
    private static final String USER_NAME = "username";
    private static final String RESPONSE = "respond";
    private static final String RESPOND_TIME = "datetime";
    private static final String RESPOND_MARK = "mark";

    @Override
    public Respond convertToObject(ResultSet resultSet) throws SQLException {
        return Respond.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addName(resultSet.getString(USER_NAME))
                .addRespondTime(convertTime(resultSet.getTimestamp(RESPOND_TIME)))
                .addMark(resultSet.getInt(RESPOND_MARK))
                .addRespond(resultSet.getString(RESPONSE))
                .build();
    }

    private LocalDateTime convertTime(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedUTC = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("GMT-2"));
        return zonedIST.toLocalDateTime();
    }
}
