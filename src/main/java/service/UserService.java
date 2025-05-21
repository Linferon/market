package service;

import dao.impl.UserDao;
import exception.AuthenticationException;
import exception.UserNotFoundException;
import model.User;

import java.util.List;

import static util.LoggerUtil.*;

public class UserService {
    private static UserService instance;
    private final UserDao userDao;
    private User currentUser;

    private UserService() {
        userDao = new UserDao();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.findAll();
        if(users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не были найдены");
        }
        return users;
    }

    public User getUserById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Сотрудник с таким id не был найден!"));
    }

    private User findUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Не был найден сотрудник с таким email!"));
    }

    public Long registerUser(User user) {
        Long id = userDao.save(user);
        info("Был зарегистрирован новый сотрудник с id " + id);
        return id;
    }

    public void updateUser(User user) {
        Long id = userDao.save(user);
        info("Был обновлен сотрудник с id " + id);
    }

    public boolean authenticate(String email, String password) {
        try {
            User user = findUserByEmail(email);

            if (isValidCredentials(user, password)) {
                this.currentUser = user;
                info("Пользователь авторизовался: " + user.getName() + " " + user.getSurname());
                return true;
            }

            warn("Неудачная попытка авторизации с email: " + email);
            return false;
        } catch (Exception e) {
            error("Ошибка при авторизации", e);
            return false;
        }
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new AuthenticationException("Пользователь не авторизован");
        }
        return currentUser;
    }

    public void logout() {
        info("Пользователь вышел из системы: ");
        this.currentUser = null;
    }

    private boolean isValidCredentials(User user, String password) {
        return user.getPassword().equals(password) && Boolean.TRUE.equals(user.getEnabled());
    }
}
