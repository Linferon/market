package service;

import dao.impl.SalaryDao;
import exception.SalaryNotFoundException;
import model.Salary;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static util.LoggerUtil.*;
import static util.ValidationUtil.*;

public class SalaryService {
    private static SalaryService instance;
    private final SalaryDao salaryDao;
    private final UserService userService;

    private SalaryService() {
        salaryDao = new SalaryDao();
        userService = UserService.getInstance();
    }

    public static synchronized SalaryService getInstance() {
        if (instance == null) {
            instance = new SalaryService();
        }
        return instance;
    }

    public List<Salary> getAllSalaries() {
        List<Salary> salaries = salaryDao.findAll();
        if(salaries == null || salaries.isEmpty()) {
            throw new SalaryNotFoundException("Записи о зарплатах не найдены");
        }
        return salaries;
    }

    public Salary getSalaryById(Long id) {
        return salaryDao.findById(id)
                .orElseThrow(() -> new SalaryNotFoundException("Запись о зарплате с ID " + id + " не найдена"));
    }

    public List<Salary> getUnpaidSalaries() {
        List<Salary> salaries = salaryDao.findUnpaidPayrolls();
        if(salaries == null || salaries.isEmpty()) {
            throw new SalaryNotFoundException("Нет невыплаченных зарплат!");
        }
        return salaries;
    }

    public void createSalary(Salary salary) {
        validateSalary(salary);
        Long id = salaryDao.save(salary);

        info("Создана новая запись о зарплате с ID " + id);
    }

    public void createSalary(Long employeeId, BigDecimal salary,
                              LocalDate periodStart, LocalDate periodEnd, Boolean paid) {
        validatePositiveAmount(salary, "Зарплата должна быть положительным числом");
        validateDateRange(periodStart, periodEnd);

        User employee = userService.getUserById(employeeId);

        Salary newSalary = new Salary(
                employeeId,
                salary,
                periodStart,
                periodEnd,
                paid
        );

        createSalary(newSalary);
    }

    public boolean updateSalary(Long salaryId, Long employeeId, BigDecimal salary,
                                 LocalDate periodStart, LocalDate periodEnd,Boolean paid) {

        Salary existingSalary = getSalaryById(salaryId);
        userService.getUserById(employeeId);

        existingSalary.setUserId(employeeId);
        existingSalary.setSalary(salary);
        existingSalary.setPeriodStart(periodStart);
        existingSalary.setPeriodEnd(periodEnd);
        existingSalary.setPaid(paid);

        return updateSalary(existingSalary);
    }

    public boolean updateSalary(Salary Salary) {
        validateSalary(Salary);

        boolean updated = salaryDao.update(Salary);
        if (updated) {
            info("Обновлена запись о зарплате с ID " + Salary.getId());
        } else {
            warn("Не удалось обновить запись о зарплате с ID " + Salary.getId());
        }

        return updated;
    }

    public void markAsPaid(Long SalaryId, LocalDate paymentDate) {
        Salary salary = getSalaryById(SalaryId);

        if (Boolean.TRUE.equals(salary.getPaid())) {
            warn("Запись о зарплате с ID " + SalaryId + " уже помечена как выплаченная");
            return;
        }

        boolean updated = salaryDao.markAsPaid(SalaryId);
        if (updated) {
            info("Запись о зарплате с ID " + SalaryId +
                    " помечена как выплаченная с датой выплаты " + paymentDate);
        } else {
            warn("Не удалось пометить запись о зарплате с ID " + SalaryId + " как выплаченную");
        }
    }

    public void deleteSalary(Long id) {
        Salary Salary = getSalaryById(id);

        boolean deleted = salaryDao.deleteById(id);

        if (deleted) {
            info("Удалена запись о зарплате с ID " + id);
        } else {
            warn("Не удалось удалить запись о зарплате с ID " + id);
        }
    }

    private void validateSalary(Salary salary) {
        Objects.requireNonNull(salary);
        validateDateRange(salary.getPeriodStart(), salary.getPeriodEnd());
        userService.getUserById(salary.getUserId());
    }
}