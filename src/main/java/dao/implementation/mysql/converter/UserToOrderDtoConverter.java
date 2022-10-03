package dao.implementation.mysql.converter;

import entity.UserToOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserToOrderDtoConverter implements DtoConverter<UserToOrder> {

    private final static String USER_ID = "user_id";
    private final static String ORDER_ID = "orders_id";

    @Override
    public UserToOrder convertToObject(ResultSet resultSet) throws SQLException {
        return UserToOrder.newBuilder()
                .addUserId(resultSet.getLong(USER_ID))
                .addOrderId(resultSet.getLong(ORDER_ID))
                .build();
    }
}
