package dao;

import dao.implementation.mysql.MySqlRole;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RoleDtoConverter;
import entity.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlRoleTest {

    private MySqlRole getMySqlRole() throws SQLException {
        return new MySqlRole(mockDataSource.getConnection());
    }

    private Role getRole() {
        return new Role(Role.RoleIdentifier.USER.getId(), Role.RoleIdentifier.USER.name());
    }

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPsmnt;

    @Mock
    ResultSet mockResultSet;

    @Mock
    DataSource mockDataSource;

    DtoConverter<Role> roleDto = new RoleDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlRole mock = getMySqlRole();
        mock.findById(getRole().getId());

        createRoleResultSet();
        List<Role> convertedObjects = roleDto.convertToObjectList(mockResultSet);
        Role role = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, role.getId());
    }

    @Test
    public void findByName() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlRole mock = getMySqlRole();
        mock.findByName("USER");

        createRoleResultSet();
        List<Role> convertedObjects = roleDto.convertToObjectList(mockResultSet);
        Role role = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, role.getId());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlRole mock = getMySqlRole();
        mock.findAll();

        createRoleResultSet();
        List<Role> convertedObjects = roleDto.convertToObjectList(mockResultSet);
        Role role = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, role.getId());
    }

    private void createRoleResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("role_id")).thenReturn(1);
        when(mockResultSet.getString("role_name")).thenReturn("USER");
    }

    @Test
    public void insert() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlRole mock = getMySqlRole();
        mock.insert(getRole());

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

        MySqlRole mock = getMySqlRole();
        mock.update(getRole());

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

        MySqlRole mock = getMySqlRole();
        mock.delete(getRole().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }
}