package dao.implementation.mysql;

import dao.abstraction.RoleDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.RoleDtoConverter;
import entity.Role;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlRole implements RoleDao {

    private final static String SELECT_ALL =
            "SELECT id AS role_id, name AS role_name " +
                    "FROM role ";

    private final static String INSERT =
            "INSERT INTO role (name) " +
                    "VALUES(?) ";

    private final static String UPDATE =
            "UPDATE role SET name = ? ";

    private final static String DELETE =
            "DELETE FROM role ";

    private final static String WHERE_ID =
            "WHERE id = ? ";

    private final static String WHERE_NAME =
            "WHERE name = ? ";

    private final DefaultDaoImpl<Role> defaultDao;

    public MySqlRole(Connection connection) {
        this(connection, new RoleDtoConverter());
    }

    public MySqlRole(Connection connection,
                     DtoConverter<Role> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Role> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Role insert(Role role) {
        Objects.requireNonNull(role);
        int id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT, role.getName());
        role.setId(id);
        return role;
    }

    @Override
    public void update(Role role) {
        Objects.requireNonNull(role);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, role.getName(), role.getId());
    }

    @Override
    public void delete(Integer id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return defaultDao.findOne(SELECT_ALL + WHERE_NAME, name);
    }
}
