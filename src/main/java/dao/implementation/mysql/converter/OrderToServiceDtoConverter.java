package dao.implementation.mysql.converter;

import entity.OrderToService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderToServiceDtoConverter implements DtoConverter<OrderToService> {

    private final static String ORDER_ID = "orders_id";
    private final static String SERVICE_ID = "service_id";

    @Override
    public OrderToService convertToObject(ResultSet resultSet) throws SQLException {
        return OrderToService.newBuilder()
                .addOrderIdId(resultSet.getLong(ORDER_ID))
                .addServiceId(resultSet.getLong(SERVICE_ID))
                .build();
    }
}
