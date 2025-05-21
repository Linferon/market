package dao.impl;

import dao.Dao;
import dao.mapper.RoleMapper;
import model.Role;

import java.sql.ResultSet;
import java.util.function.Function;

public class RoleDao extends Dao<Role> {
    @Override
    protected String getTableName() {
        return "ROLES";
    }

    @Override
    protected Function<ResultSet, Role> getMapper() {
        return RoleMapper::mapRow;
    }
}
