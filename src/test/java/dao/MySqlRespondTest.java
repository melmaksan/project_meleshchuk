package dao;

import dao.implementation.mysql.MySqlRespond;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RespondDtoConverter;

import entity.Respond;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlRespondTest {

    private MySqlRespond getMySqlRespond() throws SQLException {
        return new MySqlRespond(mockDataSource.getConnection());
    }

    private Respond getRespond() {
        return Respond.newBuilder()
                .addName("")
                .addRespondTime(LocalDateTime.now())
                .addMark(1)
                .addRespond("")
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

    DtoConverter<Respond> respondDto = new RespondDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlRespond mock = getMySqlRespond();
        mock.findById(getRespond().getId());

        createRespondResultSet();
        List<Respond> convertedObjects = respondDto.convertToObjectList(mockResultSet);
        Respond respond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Responds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Respond id is not equal to 1", 1, respond.getId());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlRespond mock = getMySqlRespond();
        mock.findAll();

        createRespondResultSet();
        List<Respond> convertedObjects = respondDto.convertToObjectList(mockResultSet);
        Respond respond = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Responds were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Respond id is not equal to 1", 1, respond.getId());
    }

    private void createRespondResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("username")).thenReturn("");
        when(mockResultSet.getTimestamp("datetime")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getInt("mark")).thenReturn(1);
        when(mockResultSet.getString("respond")).thenReturn("");
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

        MySqlRespond mock = getMySqlRespond();
        mock.insert(getRespond());

        //verify and assert
        verify(mockConn, times(1)).prepareStatement(anyString(),
                eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockPsmnt, times(4)).setObject(anyInt(), any());
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

        MySqlRespond mock = getMySqlRespond();
        mock.update(getRespond());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void delete() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(),any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlRespond mock = getMySqlRespond();
        mock.delete(getRespond().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }
}