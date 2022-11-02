package dao.implementation.mysql.converter;

import entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDtoConverter implements DtoConverter<Role> {

    private static final String ID_FIELD = "role_id";
    private static final String NAME_FIELD = "role_name";

    @Override
    public Role convertToObject(ResultSet resultSet) throws SQLException {
        int roleId = resultSet.getInt(ID_FIELD);
        String roleName = resultSet.getString(NAME_FIELD);
        return new Role(roleId, roleName);
    }
}
