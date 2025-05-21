package dao.mapper;

import exception.DatabaseMapException;
import model.Budget;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.LoggerUtil.error;

public class BudgetMapper {
    private BudgetMapper() {}

    public static Budget mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            Integer year = rs.getInt("YEAR");
            Integer month = rs.getInt("MONTH");
            BigDecimal budget = rs.getBigDecimal("BUDGET");
            BigDecimal income = rs.getBigDecimal("INCOME");
            BigDecimal expense = rs.getBigDecimal("EXPENSE");
            BigDecimal netResult = rs.getBigDecimal("NET_RESULT");

            return new Budget(id, year, month, budget, income, expense, netResult);
        } catch (SQLException e) {
            error("Error mapping budget rom ResultSet", e);
            throw new DatabaseMapException("Error mapping budget");
        }
    }
}
