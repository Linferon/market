package service;

import dao.impl.RoleDao;
import exception.RoleNotFoundException;
import model.Role;

import java.util.List;

public class RoleService {
    private static RoleService instance;
    private final RoleDao roleDao;

    private RoleService() {
        roleDao = new RoleDao();
    }

    public static synchronized RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = roleDao.findAll();
        if(roles.isEmpty()) {
            throw new RoleNotFoundException("No roles found");
        }
        return roles;
    }
}
