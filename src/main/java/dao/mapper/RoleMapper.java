package dao.mapper;

import exception.DatabaseMapException;
import model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class RoleMapper {
    private RoleMapper() {}

    public static Role mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            String name = rs.getString("NAME");

            return new Role(id, name);
        } catch (SQLException e) {
            error("Error mapping role from ResultSet", e);
            throw new DatabaseMapException("Error mapping role");
        }
    }
}
