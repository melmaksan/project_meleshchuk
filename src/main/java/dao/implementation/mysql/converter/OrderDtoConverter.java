package dao.implementation.mysql.converter;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OrderDtoConverter implements DtoConverter<Order> {

    private static final String ID_FIELD = "id";
    private static final String ORDER_TIME_START = "time";
    private static final String ORDER_TIME_END = "time_end";
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
                .addOrderTimeStart(convertTime(resultSet.getTimestamp(ORDER_TIME_START)))
                .addOrderTimeEnd(convertTime(resultSet.getTimestamp(ORDER_TIME_END)))
                .addOrderStatus(orderStatus)
                .addPaymentStatus(paymentStatus)
                .build();
    }

    private LocalDateTime convertTime(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedUTC = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("GMT-2"));
        return zonedIST.toLocalDateTime();
    }
}
