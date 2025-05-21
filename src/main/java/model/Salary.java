package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Salary {
    private Long id;
    private Long userId;
    private BigDecimal salary;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private Boolean isPaid;

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
