package dao;

import dao.implementation.mysql.MySqlService;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.ServiceDtoConverter;
import entity.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MySqlServiceTest {

    private MySqlService getMySqlService() throws SQLException {
        return new MySqlService(mockDataSource.getConnection());
    }

    private Service getService() {
        return Service.newBuilder()
                .addServiceTitle("Service")
                .addServiceType("Description")
                .addPrice(BigDecimal.ONE)
                .addImage("")
                .addDuration(Time.valueOf(LocalTime.now()))
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

    DtoConverter<Service> serviceDto = new ServiceDtoConverter();

    @Test
    public void findById() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.findById(getService().getId());

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    @Test
    public void findByTitle() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.findByTitle(getService().getTitle());

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee title is not equal to Service", "Service", service.getTitle());
    }

    @Test
    public void findAll() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.findAll();

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    private void createServiceResultSet() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("service_id")).thenReturn(1L);
        when(mockResultSet.getString("service_title")).thenReturn("Service");
        when(mockResultSet.getString("service_description")).thenReturn("Description");
        when(mockResultSet.getBigDecimal("service_price")).thenReturn(BigDecimal.ONE);
        when(mockResultSet.getString("image")).thenReturn("");
        when(mockResultSet.getString("duration")).thenReturn(String.valueOf(Time.valueOf(LocalTime.now())));
    }

    @Test
    public void findAllWithPagination() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.findAll(2, 3);

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
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

        MySqlService mock = getMySqlService();
        mock.insert(getService());

        verify(mockConn, times(1)).prepareStatement(anyString(),
                eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockPsmnt, times(5)).setObject(anyInt(), any());
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

        MySqlService mock = getMySqlService();
        mock.update(getService());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(5)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void delete() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(),any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlService mock = getMySqlService();
        mock.delete(getService().getId());

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void changePrice() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeUpdate()).thenReturn(1);

        MySqlService mock = getMySqlService();
        mock.changePrice(getService(), BigDecimal.TEN);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(2)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeUpdate();
    }

    @Test
    public void filterByServiceDescription() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        doNothing().when(mockPsmnt).setObject(anyInt(), any());
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.filterByServiceDescription(getService().getDescription());

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).setObject(anyInt(), any());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee description is not equal to Description",
                "Description", service.getDescription());
    }

    @Test
    public void ascByPriceService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.ascByPriceService();

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    @Test
    public void descByPriceService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.descByPriceService();

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    @Test
    public void ascByTitleService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.ascByTitleService();

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    @Test
    public void descByTitleService() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);

        MySqlService mock = getMySqlService();
        mock.descByTitleService();

        createServiceResultSet();
        List<Service> convertedObjects = serviceDto.convertToObjectList(mockResultSet);
        Service service = convertedObjects.get(0);

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();

        Assert.assertFalse("Employees were not parsed.", convertedObjects.isEmpty());
        Assert.assertEquals("Employee id is not equal to 1", 1, service.getId());
    }

    @Test
    public void getNumberOfRows() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPsmnt);
        when(mockPsmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE);
        when(mockResultSet.getInt(1)).thenReturn(1);

        MySqlService mock = getMySqlService();
        mock.getNumberOfRows();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPsmnt, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(anyInt());
    }
}