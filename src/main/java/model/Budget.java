package model;

import util.TableFormatter;

import java.math.BigDecimal;

public class Budget implements FormattableEntity{
    private Long id;
    private Integer year;
    private Integer month;
    private BigDecimal budget;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal netResult;

    private static final int ID_WIDTH = 5;
    private static final int DATE_WIDTH = 12;
    private static final int AMOUNT_WIDTH = 15;


    @Override
    public String toString() {
        return "Бюджет" +
                "\nid: " + id +
                "\nдата: " + year + " " + month +
                "\nфактический доход: " + income +
                "\nфактические расходы: " + expense +
                "\nчистый результат: " + netResult;
    }

    @Override
    public String getTableHeader() {
        return TableFormatter.formatCell("ID", ID_WIDTH) +
                TableFormatter.formatCell("Дата", DATE_WIDTH) +
                TableFormatter.formatCell("План", AMOUNT_WIDTH) +
                TableFormatter.formatCell("Факт доход", AMOUNT_WIDTH) +
                TableFormatter.formatCell("Факт расход", AMOUNT_WIDTH) +
                TableFormatter.formatCell("Результат", AMOUNT_WIDTH);
    }

    @Override
    public String toTableRow() {
        return TableFormatter.formatCell(id, ID_WIDTH) +
                TableFormatter.formatCell(year, DATE_WIDTH) +
                TableFormatter.formatCell(budget, AMOUNT_WIDTH) +
                TableFormatter.formatCell(income, AMOUNT_WIDTH) +
                TableFormatter.formatCell(expense, AMOUNT_WIDTH) +
                TableFormatter.formatCell(netResult, AMOUNT_WIDTH);
    }

    @Override
    public String getTableDivider() {
        return TableFormatter.createDivider(ID_WIDTH, DATE_WIDTH, AMOUNT_WIDTH, AMOUNT_WIDTH, AMOUNT_WIDTH, AMOUNT_WIDTH, AMOUNT_WIDTH);
    }


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
}
