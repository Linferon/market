package dao.impl;

import dao.Dao;
import dao.mapper.BudgetMapper;
import model.Budget;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.function.Function;

public class BudgetDao extends Dao<Budget > {
    @Override
    protected String getTableName() {
        return "BUDGETS";
    }

    @Override
    protected Function<ResultSet, Budget> getMapper() {
        return BudgetMapper::mapRow;
    }

    public Long save(Budget budget) {
        if (budget.getId() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            String sql = """
                    INSERT INTO BUDGETS 
                    (YEAR, MONTH, BUDGET, INCOME, EXPENSE) 
                    VALUES (?, ?, ?, ?, ?)""";
            Long id = insert(sql,
                    budget.getYear(),
                    budget.getMonth(),
                    budget.getBudget(),
                    budget.getIncome(),
                    budget.getExpense());
            if (id != null) {
                budget.setId(id);
            }
            return id;
        } else {
            boolean updated = update(budget);
            return updated ? budget.getId() : null;
        }
    }

    public boolean update(Budget budget) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        String sql = """
                UPDATE BUDGETS 
                SET YEAR = ?, MONTH = ?, BUDGET = ?, 
                INCOME = ?, EXPENSE = ? 
                WHERE ID = ?""";
        return update(sql,
                budget.getYear(),
                budget.getMonth(),
                budget.getBudget(),
                budget.getIncome(),
                budget.getExpense(),
                budget.getId());
    }
}
