package service;

import dao.impl.BudgetDao;
import exception.BudgetNotFoundException;
import model.Budget;

import java.math.BigDecimal;
import java.util.List;
import static util.LoggerUtil.*;
import static util.ValidationUtil.*;
import static util.ValidationUtil.validatePositiveAmount;

public class BudgetService {
    private static BudgetService instance;
    private final BudgetDao budgetDao;

    private BudgetService() {
        this.budgetDao = new BudgetDao();
    }

    public static synchronized BudgetService getInstance() {
        if (instance == null) {
            instance = new BudgetService();
        }
        return instance;
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgets = budgetDao.findAll();
        if(budgets.isEmpty()) {
            throw new BudgetNotFoundException("Бюджеты не найдены");
        }
        return budgets;
    }

    public Budget getBudgetById(Long id) {
        return budgetDao.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException("Бюджет с ID " + id + " не найден"));
    }


    public void createBudget(Budget budget) {
        validateBudget(budget);

        Long id = budgetDao.save(budget);
        info("Создан новый бюджет с ID " + id);
    }

    public void createBudget(Integer year, Integer month, BigDecimal budget, BigDecimal income, BigDecimal expense) {
        Budget newBudget = new Budget(
                year,
                month,
                budget,
                income,
                expense
        );
        createBudget(newBudget);
    }

    public boolean updateBudget(Long budgetId, Integer year, Integer month, BigDecimal budget, BigDecimal income, BigDecimal expense) {
        Budget monthlyBudget = getBudgetById(budgetId);

        monthlyBudget.setIncome(income);
        monthlyBudget.setExpense(expense);
        monthlyBudget.setBudget(budget);
        monthlyBudget.setYear(year);
        monthlyBudget.setMonth(month);

        validateBudget(monthlyBudget);

        return budgetDao.update(monthlyBudget);
    }

    public void updateActualValues(Long budgetId, BigDecimal actualIncome, BigDecimal actualExpenses) {
        validatePositiveAmount(actualIncome, "Фактический доход должен быть положительным числом");
        validatePositiveAmount(actualExpenses, "Фактические расходы должны быть положительным числом");

        Budget budget = getBudgetById(budgetId);
        budget.setIncome(actualIncome);
        budget.setExpense(actualExpenses);

        boolean updated = budgetDao.update(budget);
        if (updated) {
            info("Обновлены фактические значения для бюджета с ID " + budgetId +
                    ": доход = " + actualIncome + ", расходы = " + actualExpenses +
                    ", чистый результат = " + budget.getNetResult());
        } else {
            warn("Не удалось обновить фактические значения для бюджета с ID " + budgetId);
        }
    }

    private void validateBudget(Budget budget) {
        validateInteger(budget.getYear(), "Год должен быть положительным числом");
        validateInteger(budget.getMonth(), "Месяц должен быть положительным числом");
        validatePositiveAmount(budget.getBudget(), "Бюджет должен быть положительным числом.");
        validatePositiveAmount(budget.getIncome(), "Фактический доход должен быть положительным числом.");
        validatePositiveAmount(budget.getExpense(), "Фактические расходы должны быть положительным числом.");
    }
}