package dao.impl;

import dao.Dao;
import dao.mapper.SalaryMapper;
import model.Salary;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class SalaryDao extends Dao<Salary> {
    @Override
    protected String getTableName() {
        return "SALARIES";
    }

    @Override
    protected Function<ResultSet, Salary> getMapper() {
        return SalaryMapper::mapRow;
    }

    public List<Salary> findUnpaidPayrolls() {
        String sql= "SELECT * FROM SALARIES WHERE IS_PAID = FALSE";
        return queryList(sql);
    }

    public Long save(Salary salary) {
        if (salary.getId() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            String sql = """
                    INSERT INTO SALARIES 
                    (USER_ID, SALARY , 
                    PERIOD_START, PERIOD_END, 
                    IS_PAID) 
                    VALUES (?, ?, ?, ?, ?)""";
            Long id = insert(sql,
                    salary.getUserId(),
                    salary.getSalary(),
                    salary.getPeriodStart(),
                    salary.getPeriodEnd(),
                    salary.getPaid());
            if (id != null) {
                salary.setId(id);
            }
            return id;
        } else {
            boolean updated = update(salary);
            return updated ? salary.getId() : null;
        }
    }

    public boolean markAsPaid(Long id) {
        String sql = "UPDATE SALARIES SET IS_PAID = TRUE WHERE ID = ?";
        return update(sql,  id);
    }

    public boolean update(Salary salary ) {
        String sql = """
                UPDATE SALARIES 
                SET USER_ID = ?,SALARY = ?, PERIOD_START = ?, 
                PERIOD_END = ?, IS_PAID = ? WHERE ID = ?""";
        return update(sql,
                salary.getUserId(),
                salary.getSalary(),
                salary.getPeriodStart(),
                salary.getPeriodEnd(),
                salary.getPaid(),
                salary.getId());
    }

}
