package dao.implementation.mysql.converter;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderDtoConverter implements DtoConverter<Order> {

    private final static String ID_FIELD = "id";
    private final static String ORDER_TIME = "time";
    private final DtoConverter<OrderStatus> statusConverter;
    private final DtoConverter<PaymentStatus> paymentStatusDtoConverter;

    public OrderDtoConverter() {
        this(new OrderStatusDtoConverter(), new PaymentStatusDtoConverter());
    }

    public OrderDtoConverter(DtoConverter<OrderStatus> statusConverter, DtoConverter<PaymentStatus> paymentStatusDtoConverter) {
        this.statusConverter = statusConverter;
        this.paymentStatusDtoConverter = paymentStatusDtoConverter;
    }

    @Override
    public Order convertToObject(ResultSet resultSet) throws SQLException {
        OrderStatus orderStatus = statusConverter.convertToObject(resultSet);
        PaymentStatus paymentStatus = paymentStatusDtoConverter.convertToObject(resultSet);
        return Order.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addOrderTime(resultSet.getTimestamp(ORDER_TIME).toLocalDateTime())
                .addOrderStatus(orderStatus)
                .addPaymentStatus(paymentStatus)
                .build();
    }

    private List<User> getUsers(String concatUsers) {
        return Stream.of(concatUsers.split(",")).map(split ->
                User.newBuilder().addFirstName(split).addLastName(split).build()).collect(Collectors.toList());
    }

    private List<Service> getServices(String concatServices) {
        return Stream.of(concatServices.split(",")).map(split ->
                Service.newBuilder().addServiceName(split).build()).collect(Collectors.toList());
    }
}
