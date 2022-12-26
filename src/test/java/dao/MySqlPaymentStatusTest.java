package dao;

import dao.implementation.mysql.MySqlPaymentStatus;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.PaymentStatusDtoConverter;
import entity.PaymentStatus;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlPaymentStatusTest {

    private MySqlPaymentStatus getMySqlPaymentStatus() throws SQLException {
        return new MySqlPaymentStatus(mockDataSource.getConnection());
    }

    private PaymentStatus getPaymentStatus() {
        return new PaymentStatus(PaymentStatus.PaymentIdentifier.UNPAID.getId(),
                PaymentStatus.PaymentIdentifier.UNPAID.name());
    }

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPsmnt;

    @Mock
    ResultSet mockResultSet;

    @Mock
    DataSource mockDataSource;

    DtoConverter<PaymentStatus> paymentStatusDto = new PaymentStatusDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.findById(getPaymentStatus().getId());

        createPaymentStatusResultSet();
        List<PaymentStatus> convertedObjects = paymentStatusDto.convertToObjectList(mockResultSet);
        PaymentStatus paymentStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, paymentStatus.getId());
    }

    @Test
    public void findByName() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.findByName("PAID");

        createPaymentStatusResultSet();
        List<PaymentStatus> convertedObjects = paymentStatusDto.convertToObjectList(mockResultSet);
        PaymentStatus paymentStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, paymentStatus.getId());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.findAll();

        createPaymentStatusResultSet();
        List<PaymentStatus> convertedObjects = paymentStatusDto.convertToObjectList(mockResultSet);
        PaymentStatus paymentStatus = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, paymentStatus.getId());
    }

    private void createPaymentStatusResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("payment_status_id")).thenReturn(1);
        when(mockResultSet.getString("payment_status_name")).thenReturn("UNPAID");
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.insert(getPaymentStatus());

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

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.update(getPaymentStatus());

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

        MySqlPaymentStatus mock = getMySqlPaymentStatus();
        mock.delete(getPaymentStatus().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }
}