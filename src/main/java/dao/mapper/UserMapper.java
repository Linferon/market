package dao.mapper;

import exception.DatabaseMapException;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class UserMapper {
    private UserMapper() {}

    public static User mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            String email = rs.getString("EMAIL");
            String password = rs.getString("PASSWORD");
            Boolean enabled = rs.getBoolean("ENABLED");
            Long roleId = rs.getLong("ROLE_ID");

            return new User(id, name, surname, email, password, enabled, roleId);

        }  catch (SQLException e) {
            error("Error mapping user from ResultSet", e);
            throw new DatabaseMapException("Error mapping user");
        }
    }
}
