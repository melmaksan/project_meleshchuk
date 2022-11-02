package dao;

import dao.datasource.PooledConnection;
import dao.implementation.mysql.MySqlOrder;
import entity.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MySqlOrderTest {



    DataSource dataSource = PooledConnection.getInstance();

    MySqlOrder mySqlOrder = new MySqlOrder(dataSource.getConnection());

    MySqlOrderTest() throws SQLException {
    }

//    @Mock
//    Connection mockConn;
//    @Mock
//    PreparedStatement mockPsmnt;
//    @Mock
//    ResultSet mockResultSet;
//
//    int userId = 5;
//
//    @Mock
//    MySqlOrder orderDao;

//    @Before
//    public void setUp() throws SQLException {
//        when(dataSource.getConnection()).thenReturn(mockConn);
//        when(dataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
//        doNothing().when(mockConn).commit();
//        when(mockConn.prepareStatement(anyString(), Statement.RETURN_GENERATED_KEYS)).thenReturn(mockPsmnt);
//        doNothing().when(mockPsmnt).setString(anyInt(), anyString());
//        when(mockPsmnt.execute()).thenReturn(Boolean.TRUE);
//        when(mockPsmnt.getGeneratedKeys()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
//        when(mockResultSet.getInt(1)).thenReturn(userId);
//    }
//
//    @Test
//    public void testCreateWithNoExceptions() throws SQLException {
//        MySqlOrder instance = new MySqlOrder(mockConn);
//        instance.insert(Order.newBuilder()
//                .addOrderTimeStart(LocalDateTime.of(2022, 11, 1, 19, 30))
//                .addDefaultStatus()
//                .addDefaultPaymentStatus()
//                .build());
//
//        //verify and assert
//        verify(mockConn, times(1)).prepareStatement(anyString(), Statement.RETURN_GENERATED_KEYS);
//        verify(mockPsmnt, times(6)).setString(anyInt(), anyString());
//        verify(mockPsmnt, times(1)).execute();
//        verify(mockConn, times(1)).commit();
//        verify(mockResultSet, times(2)).next();
//        verify(mockResultSet, times(1)).getInt(1);
//    }

    @Test
    void findById() {
        Optional<Order> optionalOrder = mySqlOrder.findById(5L);
        //Assert.assertEquals();
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllWithCredentials() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void changeBookingTime() {
    }

    @Test
    void updateOrderStatus() {
    }

    @Test
    void updatePaymentStatus() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void getNumberOfRows() {
    }
}