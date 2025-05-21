package dao.impl;

import dao.Dao;
import dao.mapper.UserMapper;
import model.User;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserDao extends Dao<User> {
    @Override
    protected String getTableName() {
        return "USERS";
    }

    @Override
    protected Function<ResultSet, User> getMapper() {
        return UserMapper::mapRow;
    }

    public List<User> findActiveEmployees(){
        String sql = "SELECT * from users where enabled = true ";
        return queryList(sql);
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * from users where email = ? ";
        return querySingle(sql,email);
    }

    public Long save(User user) {
        if (user.getId() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            String sql = """
                    INSERT INTO users
                    (NAME, SURNAME, EMAIL, PASSWORD, ENABLED, ROLE_ID)
                    VALUES (?, ?, ?, ?, ?, ?)""";

            Long id = insert(sql,
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getEnabled(),
                    user.getRoleId());

            if (id != null) {
                user.setId(id);
            }
            return id;
        } else {
            boolean updated = update(user);
            return updated ? user.getId() : null;
        }
    }

    public boolean update(User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        String sql = """
                UPDATE USERS 
                SET NAME = ?, SURNAME = ?, EMAIL = ?, 
                PASSWORD = ?, ENABLED = ?,  ROLE_ID = ?  
                WHERE ID = ?""";

        return update(sql,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                user.getRoleId(),
                user.getId());
    }
}
