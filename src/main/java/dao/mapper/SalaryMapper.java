package dao.mapper;

import exception.DatabaseMapException;
import model.Salary;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static util.LoggerUtil.error;

public class SalaryMapper {
    private SalaryMapper() {}

    public static Salary mapRow(ResultSet rs) {
        try {
            Long id = rs.getLong("ID");
            Long userId = rs.getLong("USER_ID");
            BigDecimal salary = rs.getBigDecimal("SALARY");
            LocalDate periodStart = rs.getDate("PERIOD_START").toLocalDate();
            LocalDate periodEnd = rs.getDate("PERIOD_END").toLocalDate();
            Boolean isPaid= rs.getBoolean("IS_PAID");

            return new Salary(id, userId, salary, periodStart, periodEnd, isPaid);
        } catch (SQLException e) {
            error("Error mapping salary from ResultSet", e);
            throw new DatabaseMapException("Error mapping salary");
        }
    }
}
