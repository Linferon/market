package model;

import static util.TableFormatter.createDivider;
import static util.TableFormatter.formatCell;

public class User implements FormattableEntity{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Boolean enabled;
    private Long roleId;

    private static final int ID_WIDTH = 5;
    private static final int NAME_WIDTH = 20;
    private static final int SURNAME_WIDTH = 20;
    private static final int EMAIL_WIDTH = 30;
    private static final int ROLE_WIDTH = 15;
    private static final int ENABLED_WIDTH = 10;

    @Override
    public String getTableHeader() {
        return formatCell("ID", ID_WIDTH) +
                formatCell("Имя", NAME_WIDTH) +
                formatCell("Фамилия", SURNAME_WIDTH) +
                formatCell("Email", EMAIL_WIDTH) +
                formatCell("Роль", ROLE_WIDTH) +
                formatCell("Активен", ENABLED_WIDTH);
    }

    @Override
    public String toTableRow() {
        return formatCell(id, ID_WIDTH) +
                formatCell(name, NAME_WIDTH) +
                formatCell(surname, SURNAME_WIDTH) +
                formatCell(email, EMAIL_WIDTH) +
                formatCell(roleId, ROLE_WIDTH) +
                formatCell(Boolean.TRUE.equals(enabled) ? "Активен" : "Уволен", ENABLED_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return createDivider(ID_WIDTH, NAME_WIDTH, SURNAME_WIDTH, EMAIL_WIDTH, ROLE_WIDTH);
    }

    @Override
    public String toString() {
        return "Сотрудник " +
                "\nid: " + id +
                "\nимя: " + name +
                "\nфамилия: " + surname +
                "\nemail: " + email +
                "\nроль: " + roleId;
    }

    public User(Long id, String name, String surname, String email, String password, Boolean enabled, Long roleId) {
        this(name, surname, email, password, enabled, roleId);
        this.id = id;
    }

    public User(String name, String surname, String email, String password, Boolean enabled, Long roleId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
