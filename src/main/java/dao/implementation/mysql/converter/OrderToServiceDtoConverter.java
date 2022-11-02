package dao.implementation.mysql.converter;

import entity.OrderToService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderToServiceDtoConverter implements DtoConverter<OrderToService> {

    private static final String ORDER_ID = "orders_id";
    private static final String SERVICE_ID = "service_id";

    @Override
    public OrderToService convertToObject(ResultSet resultSet) throws SQLException {
        return OrderToService.newBuilder()
                .addOrderId(resultSet.getLong(ORDER_ID))
                .addServiceId(resultSet.getLong(SERVICE_ID))
                .build();
    }
}
