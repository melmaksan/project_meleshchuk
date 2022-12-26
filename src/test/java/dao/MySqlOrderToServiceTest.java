package dao;

import dao.implementation.mysql.MySqlOrderToService;
import dao.implementation.mysql.MySqlRole;
import dao.implementation.mysql.MySqlUser;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderToServiceDtoConverter;
import dao.implementation.mysql.converter.UserDtoConverter;
import entity.OrderToService;
import entity.Role;
import entity.User;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlOrderToServiceTest {

    private MySqlOrderToService getMySqlOrderToService() throws SQLException {
        return new MySqlOrderToService(mockDataSource.getConnection());
    }

    private OrderToService getOrderToService() {
        return OrderToService.newBuilder()
                .addOrderId(1)
                .addServiceId(1)
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

    DtoConverter<OrderToService> orderToServiceDto = new OrderToServiceDtoConverter();

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.findAll();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
    }

    private void createOrderToServiceResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("orders_id")).thenReturn(1L);
        when(mockResultSet.getLong("service_id")).thenReturn(2L);
    }

    @Test
    public void findAllByOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.findAllByOrder(getOrderToService().getOrderId());

        createOrderToServiceResultSet();
        List<OrderToService> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        OrderToService orderToService = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, orderToService.getOrderId());
    }

    @Test
    public void findAllByService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.findAllByService(getOrderToService().getServiceId());

        createOrderToServiceResultSet();
        List<OrderToService> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        OrderToService orderToService = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 2, orderToService.getServiceId());
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.insert(getOrderToService());

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

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.update(getOrderToService());

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

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.delete(getOrderToService().getOrderId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void deleteService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.delete(getOrderToService().getServiceId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void isServiceExistInBookedOrder() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);

        MySqlOrderToService mock = getMySqlOrderToService();
        mock.isServiceExistInBookedOrder(getOrderToService().getServiceId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

}