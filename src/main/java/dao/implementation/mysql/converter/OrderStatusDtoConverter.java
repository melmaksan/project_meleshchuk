package dao.implementation.mysql.converter;

import entity.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDtoConverter implements DtoConverter<OrderStatus> {

    private final static String ID_FIELD = "status_id";
    private final static String NAME_FIELD = "status_name";

    @Override
    public OrderStatus convertToObject(ResultSet resultSet) throws SQLException {
        int statusId = resultSet.getInt(ID_FIELD);
        String statusName = resultSet.getString(NAME_FIELD);
        return new OrderStatus(statusId, statusName);
    }
}
