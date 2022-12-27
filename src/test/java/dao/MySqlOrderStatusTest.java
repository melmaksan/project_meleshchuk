package dao;

import dao.implementation.mysql.MySqlOrderStatus;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderStatusDtoConverter;
import entity.OrderStatus;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlOrderStatusTest {

    private MySqlOrderStatus getMySqlOrderStatus() throws SQLException {
        return new MySqlOrderStatus(mockDataSource.getConnection());
    }

    private OrderStatus getOrderStatus() {
        return new OrderStatus(OrderStatus.StatusIdentifier.BOOKED.getId(),
                OrderStatus.StatusIdentifier.BOOKED.name());
    }

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPsmnt;

    @Mock
    ResultSet mockResultSet;

    @Mock
    DataSource mockDataSource;

    DtoConverter<OrderStatus> orderStatusDto = new OrderStatusDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.findById(getOrderStatus().getId());

        createOrderStatusResultSet();
        List<OrderStatus> convertedObjects = orderStatusDto.convertToObjectList(mockResultSet);
        OrderStatus orderStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Statuses were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Status id is not equal to 1", 1, orderStatus.getId());
    }

    @Test
    public void findByName() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.findByName("BOOKED");

        createOrderStatusResultSet();
        List<OrderStatus> convertedObjects = orderStatusDto.convertToObjectList(mockResultSet);
        OrderStatus orderStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Statuses were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Status id is not equal to 1", 1, orderStatus.getId());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.findAll();

        createOrderStatusResultSet();
        List<OrderStatus> convertedObjects = orderStatusDto.convertToObjectList(mockResultSet);
        OrderStatus orderStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Statuses were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Status id is not equal to 1", 1, orderStatus.getId());
    }

    private void createOrderStatusResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("status_id")).thenReturn(1);
        when(mockResultSet.getString("status_name")).thenReturn("BOOKED");
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.insert(getOrderStatus());

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

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.update(getOrderStatus());

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

        MySqlOrderStatus mock = getMySqlOrderStatus();
        mock.delete(getOrderStatus().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }
}