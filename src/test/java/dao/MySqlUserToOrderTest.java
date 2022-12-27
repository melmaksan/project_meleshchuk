package dao;

import dao.implementation.mysql.MySqlUserToOrder;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToOrderDtoConverter;
import entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MySqlUserToOrderTest {

    private MySqlUserToOrder getMySqlUserToOrder() throws SQLException {
        return new MySqlUserToOrder(mockDataSource.getConnection());
    }

    private UserToOrder getUserToOrder() {
        return UserToOrder.newBuilder()
                .addUserId(1)
                .addOrderId(1)
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

    DtoConverter<UserToOrder> orderToServiceDto = new UserToOrderDtoConverter();

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findAll();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
    }

    private void createUserToOrderResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("user_id")).thenReturn(1L);
        when(mockResultSet.getLong("orders_id")).thenReturn(2L);
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.insert(getUserToOrder());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void update() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.update(getUserToOrder());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void delete() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.delete(getUserToOrder().getOrderId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void deleteUser() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.delete(getUserToOrder().getUserId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void findAllByUser() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findAllByUser(getUserToOrder().getUserId());

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, userToOrder.getUserId());
    }

    @Test
    public void findAllByOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findAllByOrder(getUserToOrder().getOrderId());

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Order id is not equal to 2", 2, userToOrder.getOrderId());
    }

    @Test
    public void findSpecialistsByOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findSpecialistsByOrder(getUserToOrder().getOrderId());

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Order id is not equal to 2", 2, userToOrder.getOrderId());
    }

    @Test
    public void findClientsByOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findClientsByOrder(getUserToOrder().getOrderId());

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Order id is not equal to 2", 2, userToOrder.getOrderId());
    }

    @Test
    public void findAllBookedByDay() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findAllBookedByDay(getUserToOrder().getUserId(), LocalDate.now(), LocalDate.now().plusDays(1),
                OrderStatus.StatusIdentifier.BOOKED.getId());

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(4)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, userToOrder.getUserId());
    }

    @Test
    public void findOrdersByDay() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.findOrdersByDay(getUserToOrder().getUserId(), LocalDate.now(), LocalDate.now().plusDays(1));

        createUserToOrderResultSet();
        List<UserToOrder> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToOrder userToOrder = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(3)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToOrders were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, userToOrder.getUserId());
    }

    @Test
    public void isSpecExistsInOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);

        MySqlUserToOrder mock = getMySqlUserToOrder();
        mock.isSpecExistsInOrder(getUserToOrder().getUserId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }
}