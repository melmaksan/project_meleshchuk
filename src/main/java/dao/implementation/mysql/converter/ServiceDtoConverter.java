package dao.implementation.mysql.converter;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ServiceDtoConverter implements DtoConverter<Service> {

    private static final String ID_FIELD = "service_id";
    private static final String SERVICE_NAME = "service_title";
    private static final String SERVICE_DESCRIPTION = "service_description";
    private static final String PRICE = "service_price";
    private static final String IMAGE = "image";
    private static final String DURATION = "duration";


    @Override
    public Service convertToObject(ResultSet resultSet) throws SQLException {
        return Service.newBuilder()
                .addServiceId(resultSet.getLong(ID_FIELD))
                .addServiceTitle(resultSet.getString(SERVICE_NAME))
                .addServiceType(resultSet.getString(SERVICE_DESCRIPTION))
                .addPrice(resultSet.getBigDecimal(PRICE))
                .addImage(resultSet.getString(IMAGE))
                .addDuration(Time.valueOf(resultSet.getString(DURATION)))
                .build();
    }
}
