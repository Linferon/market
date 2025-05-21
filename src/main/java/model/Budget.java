package model;

import java.math.BigDecimal;

public class Budget {
    private Long id;
    private Integer year;
    private Integer month;
    private BigDecimal budget;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal netResult;

    public Budget(Integer year, Integer month, BigDecimal budget, BigDecimal income, BigDecimal expense) {
        this.year = year;
        this.month = month;
        this.budget = budget;
        this.income = income;
        this.expense = expense;
    }

    public Budget(Long id, Integer year, Integer month, BigDecimal budget, BigDecimal income, BigDecimal expense, BigDecimal netResult) {
        this(year, month, budget, income, expense);
        this.id = id;
        this.netResult = netResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getNetResult() {
        return netResult;
    }

    public void setNetResult(BigDecimal netResult) {
        this.netResult = netResult;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Budget{");
        sb.append("id=").append(id);
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", budget=").append(budget);
        sb.append(", income=").append(income);
        sb.append(", expense=").append(expense);
        sb.append(", netResult=").append(netResult);
        sb.append('}');
        return sb.toString();
    }
}
