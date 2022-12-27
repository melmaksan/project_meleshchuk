package dao;

import dao.implementation.mysql.MySqlUserToRespond;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToRespondDtoConverter;
import entity.UserToRespond;
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
public class MySqlUserToRespondTest {

    private MySqlUserToRespond getMySqlUserToRespond() throws SQLException {
        return new MySqlUserToRespond(mockDataSource.getConnection());
    }

    private UserToRespond getUserToRespond() {
        return UserToRespond.newBuilder()
                .addRespondId(1)
                .addUserId(1)
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

    DtoConverter<UserToRespond> orderToServiceDto = new UserToRespondDtoConverter();

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.findAll();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
    }

    private void createUserToRespondResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("respond_id")).thenReturn(1L);
        when(mockResultSet.getLong("user_id")).thenReturn(2L);
    }

    @Test
    public void findAllByUser() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.findAllByUser(getUserToRespond().getUserId());

        createUserToRespondResultSet();
        List<UserToRespond> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToRespond userToRespond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToResponds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 2", 2, userToRespond.getUserId());
    }

    @Test
    public void findAllByRespond() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.findAllByRespond(getUserToRespond().getRespondId());

        createUserToRespondResultSet();
        List<UserToRespond> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToRespond userToRespond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToResponds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Respond id is not equal to 1", 1, userToRespond.getRespondId());
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.insert(getUserToRespond());

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

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.update(getUserToRespond());

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

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.delete(getUserToRespond().getUserId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void findSpecialistsByRespond() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.findSpecialistsByRespond(getUserToRespond().getRespondId());

        createUserToRespondResultSet();
        List<UserToRespond> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToRespond userToRespond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToResponds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Respond id is not equal to 1", 1, userToRespond.getRespondId());
    }

    @Test
    public void findClientsByRespond() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUserToRespond mock = getMySqlUserToRespond();
        mock.findSpecialistsByRespond(getUserToRespond().getRespondId());

        createUserToRespondResultSet();
        List<UserToRespond> convertedObjects = orderToServiceDto.convertToObjectList(mockResultSet);
        UserToRespond userToRespond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("UserToResponds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Respond id is not equal to 1", 1, userToRespond.getRespondId());
    }
}