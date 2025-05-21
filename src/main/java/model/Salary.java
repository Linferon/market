package model;

import util.TableFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Salary implements FormattableEntity {
    private Long id;
    private Long userId;
    private BigDecimal salary;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private Boolean isPaid;

    private static final int ID_WIDTH = 5;
    private static final int EMPLOYEE_WIDTH = 25;
    private static final int AMOUNT_WIDTH = 15;
    private static final int STATUS_WIDTH = 10;

    @Override
    public String toString() {
        return "Зарплата" +
                "\nid: " + id +
                "\nсотрудник: " +userId +
                "\nсумма: " + salary +
                "\nстатус: " + (Boolean.TRUE.equals(isPaid) ? "Выплачена" : "Не выплачена");
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell("ID", ID_WIDTH) +
                TableFormatter.formatCell("Сотрудник", EMPLOYEE_WIDTH) +
                TableFormatter.formatCell("Сумма", AMOUNT_WIDTH) +
                TableFormatter.formatCell("Статус", STATUS_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(id, ID_WIDTH) +
                TableFormatter.formatCell(userId, EMPLOYEE_WIDTH) +
                TableFormatter.formatCell(salary, AMOUNT_WIDTH) +
                TableFormatter.formatCell(isPaid, STATUS_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(ID_WIDTH, EMPLOYEE_WIDTH, AMOUNT_WIDTH, STATUS_WIDTH);
    }

    public Salary(Long id, Long userId, BigDecimal salary, LocalDate periodStart, LocalDate periodEnd, Boolean isPaid) {
        this(userId, salary, periodStart, periodEnd, isPaid);
        this.id = id;
    }

    public Salary(Long userId, BigDecimal salary, LocalDate periodStart, LocalDate periodEnd, Boolean isPaid) {
        this.userId = userId;
        this.salary = salary;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.isPaid = isPaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
