package dao;

import dao.implementation.mysql.MySqlOrder;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderDtoConverter;
import entity.Order;
import entity.OrderStatus;
import entity.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MySqlOrderTest {

    private MySqlOrder getMySqlOrder() throws SQLException {
        return new MySqlOrder(mockDataSource.getConnection());
    }

    private Order getOrder() {
        return Order.newBuilder()
                .addOrderTimeStart(LocalDateTime.now())
                .addDefaultStatus()
                .addDefaultPaymentStatus()
                .build();
    }

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPsmnt;

    @Mock
    ResultSet mockResultSet;

    @Mock
    DataSource mockDataSource;

    DtoConverter<Order> orderDto = new OrderDtoConverter();


    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrder mock = getMySqlOrder();
        mock.findById(getOrder().getId());

        createOrderResultSet();
        List<Order> convertedObjects = orderDto.convertToObjectList(mockResultSet);
        Order order = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, order.getId());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrder mock = getMySqlOrder();
        mock.findAll();

        createOrderResultSet();
        List<Order> convertedObjects = orderDto.convertToObjectList(mockResultSet);
        Order order = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, order.getId());

    }

    private void createOrderResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getTimestamp("time")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("time_end")).thenReturn(Timestamp.valueOf(LocalDateTime.now().minusMinutes(60)));
    }

    @Test
    public void findAllWithCredentials() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrder mock = getMySqlOrder();
        mock.findAllWithCredentials(LocalDate.now(), LocalDate.now().plusDays(1),
                OrderStatus.StatusIdentifier.BOOKED.getId());

        createOrderResultSet();
        List<Order> convertedObjects = orderDto.convertToObjectList(mockResultSet);
        Order order = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(3)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, order.getId());
    }

    @Test
    public void findAllWithPagination() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrder mock = getMySqlOrder();
        mock.findAll(2, 3);

        createOrderResultSet();
        List<Order> convertedObjects = orderDto.convertToObjectList(mockResultSet);
        Order order = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, order.getId());
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);
        when(mockPsmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getLong(1)).thenReturn(1L);

        MySqlOrder mock = getMySqlOrder();
        mock.insert(getOrder());

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(),
                eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockPsmnt, times(3)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);
    }

    @Test
    public void update() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.update(getOrder());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(4)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void delete() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(),any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.delete(getOrder().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void changeBookingTime() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.changeBookingTime(getOrder(), LocalDateTime.now());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void updateOrderStatus() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.updateOrderStatus(getOrder(), OrderStatus.StatusIdentifier.DONE.getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void updatePaymentStatus() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.updatePaymentStatus(getOrder(), PaymentStatus.PaymentIdentifier.PAID.getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void getNumberOfRows() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(1)).thenReturn(1);

        MySqlOrder mock = getMySqlOrder();
        mock.getNumberOfRows();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(anyInt());
    }
}