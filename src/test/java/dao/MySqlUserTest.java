package dao;

import dao.implementation.mysql.MySqlUser;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserDtoConverter;
import entity.Role;
import entity.User;
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
public class MySqlUserTest {

    private MySqlUser getMySqlUser() throws SQLException {
        return new MySqlUser(mockDataSource.getConnection());
    }

    private User getUser() {
        return User.newBuilder()
                .addFirstName("Name")
                .addLastName("Surname")
                .addLogin("Login")
                .addPassword("")
                .addPhoneNumber("")
                .addDefaultRate()
                .addRole(new Role(Role.RoleIdentifier.USER.getId(), Role.RoleIdentifier.USER.name()))
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

    DtoConverter<User> userDto = new UserDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findById(getUser().getId());

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void findByLogin() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findByLogin("Login");

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User login is not equal to Login", "Login", user.getLogin());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findAll();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    private void createUserResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("user_id")).thenReturn(1L);
        when(mockResultSet.getString("first_name")).thenReturn("Name");
        when(mockResultSet.getString("last_name")).thenReturn("Surname");
        when(mockResultSet.getString("login")).thenReturn("Login");
        when(mockResultSet.getString("password")).thenReturn("");
        when(mockResultSet.getString("phone_num")).thenReturn("");
        when(mockResultSet.getFloat("rate")).thenReturn(1f);
    }

    @Test
    public void findAllWithPagination() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findAll(2, 3);

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
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

        MySqlUser mock = getMySqlUser();
        mock.insert(getUser());

        verify(mockConn, times(1)).prepareStatement(anyString(),
                eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockPsmnt, times(7)).setObject(anyInt(), any());
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

        MySqlUser mock = getMySqlUser();
        mock.update(getUser());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(6)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void delete() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUser mock = getMySqlUser();
        mock.delete(getUser().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void findAllSpecialists() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findAllSpecialists();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void findAllUsers() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findAllUsers();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void findAllAdmins() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.findAllAdmins();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void ascByRating() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.ascByRating();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void descByRating() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.descByRating();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void ascByName() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.ascByName();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void descByName() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlUser mock = getMySqlUser();
        mock.descByName();

        createUserResultSet();
        List<User> convertedObjects = userDto.convertToObjectList(mockResultSet);
        User user = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Users were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("User id is not equal to 1", 1, user.getId());
    }

    @Test
    public void getNumberOfRows() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(1)).thenReturn(1);

        MySqlUser mock = getMySqlUser();
        mock.getNumberOfRows();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(anyInt());
    }

    @Test
    public void changePassword() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUser mock = getMySqlUser();
        mock.changePassword(getUser(), "");

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void updateRating() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlUser mock = getMySqlUser();
        mock.updateRating(getUser(), 1f);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }
}