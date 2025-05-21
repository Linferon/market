package controller;

import model.*;
import service.*;
import exception.ExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static util.ConsoleUtil.*;
import static util.ConsoleUtil.showEntitiesTable;
import static util.InputHandler.*;
import static util.TableFormatter.formatTable;

public class AccountantController extends BaseController {
    private final SalaryService salaryService;
    private final UserService userService;

    private final SalaryController payrollController;

    public AccountantController() {
        salaryService = SalaryService.getInstance();
        userService = UserService.getInstance();

        this.payrollController = new SalaryController();
    }

    @Override
    public void showMenu() {
        createMenu("Меню Бухгалтера")
                .addMenuItem("Начисление зарплат", payrollController::managePayroll)
                .addExitItem("Выйти из системы")
                .show();
    }

    private class SalaryController {
        public void managePayroll() {
            createMenu("Начисление зарплат")
                    .addMenuItem("Просмотреть зарплаты", this::viewPayrolls)
                    .addMenuItem("Начислить зарплату сотруднику", this::addPayroll)
                    .addMenuItem("Редактировать зарплату", this::editPayroll)
                    .addMenuItem("Отметить зарплату как выплаченную", this::markPayrollAsPaid)
                    .addMenuItem("Удалить зарплату", this::deletePayroll)
                    .addExitItem("Назад")
                    .show();
        }

        private void viewPayrolls() {
            createMenu("Просмотреть зарплаты")
                    .addMenuItem("Все", this::viewAllPayrolls)
                    .addMenuItem("Невыплаченные", this::viewUnpaidPayrolls)
                    .addExitItem("Назад")
                    .show();
        }


        private void viewUnpaidPayrolls() {
            ExceptionHandler.execute(() -> {
                List<Salary> unpaidPayrolls = salaryService.getUnpaidSalaries();
                showEntitiesTable(unpaidPayrolls, "Невыплаченные зарплаты");
            });
        }

        private void viewAllPayrolls() {
            ExceptionHandler.execute(() -> {
                List<Salary> payrolls = salaryService.getAllSalaries();
                showEntitiesTable(payrolls, "Список всех зарплат: ");

                long paidCount = payrolls.stream().filter(Salary::getPaid).count();
                long unpaidCount = payrolls.size() - paidCount;

                println("Выплачено: " + paidCount + " | Не выплачено: " + unpaidCount);
            });
        }

        private void addPayroll() {
            ExceptionHandler.execute(() -> {
                List<User> users = userService.getActiveUsers();
                printHeader("Сотрудники");
                println(formatTable(users));

                Long employeeId = getLongInput("Введите ID сотрудника: ");
                BigDecimal salary = getBigDecimalInput("Введите зарплату: ");
                LocalDate periodStart = getDateInput("Введите дату начала периода (ГГГГ-ММ-ДД): ");
                LocalDate periodEnd = getDateInput("Введите дату окончания периода (ГГГГ-ММ-ДД): ");

                salaryService.createSalary(employeeId, salary, periodStart, periodEnd, false);
                showSuccess("Зарплата успешно начислена.");
            });
        }

        private void editPayroll() {
            ExceptionHandler.execute(() -> {
                viewPayrolls();
                Long payrollId = getLongInput("Введите ID зарплаты для редактирования: ");

                Salary payroll = salaryService.getSalaryById(payrollId);
                showEntityDetails(payroll, "Редактирование зарплаты с ID " + payrollId);

                if (Boolean.TRUE.equals(payroll.getPaid())) {
                    showConfirmationMenu("Внимание! Зарплата уже выплачена. Продолжить редактирование? ", () -> {
                        showEditMenu(payroll);
                        showSuccess("Зарплата успешно обновлена.");
                    });
                } else {
                    showEditMenu(payroll);
                }
            });
        }

        private void showEditMenu(Salary payroll) {
            List<User> users = userService.getActiveUsers();
            printHeader("Сотрудники");
            println(formatTable(users));

            Long employeeId = getUpdatedLongValue("ID сотрудника", payroll.getUserId());
            BigDecimal salary = getUpdatedBigDecimalValue("зарплату", payroll.getSalary());
            LocalDate periodStart = getUpdatedDateValue("дату начала периода", payroll.getPeriodStart());
            LocalDate periodEnd = getUpdatedDateValue("дату окончания периода", payroll.getPeriodEnd());

            boolean updated = salaryService.updateSalary(payroll.getId(), employeeId, salary, periodStart, periodEnd, payroll.getPaid());

            if (updated) {
                showSuccess("Зарплата успешно обновлена.");
            } else {
                showError("Не удалось обновить зарплату.");
            }
        }

        private void markPayrollAsPaid() {
            ExceptionHandler.execute(() -> {
                viewUnpaidPayrolls();

                Long payrollId = getLongInput("Введите ID зарплаты для отметки о выплате: ");
                String dateInput = getStringInput(
                        "Введите дату выплаты (ГГГГ-ММ-ДД) или нажмите Enter для текущей даты: "
                );
                LocalDate paymentDate = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

                salaryService.markAsPaid(payrollId, paymentDate);
                showSuccess("Зарплата отмечена как выплаченная.");
            });
        }

        private void deletePayroll() {
            ExceptionHandler.execute(() -> {
                viewPayrolls();

                Long payrollId = getLongInput("Введите ID зарплаты для удаления: ");
                Salary payroll = salaryService.getSalaryById(payrollId);

                if (Boolean.TRUE.equals(payroll.getPaid())) {
                    showConfirmationMenu("Внимание! Зарплата уже выплачена. Продолжить редактирование? ", () -> {
                        salaryService.deleteSalary(payrollId);
                        showSuccess("Зарплата была удалена.");
                    });
                } else {
                    salaryService.deleteSalary(payrollId);
                    showSuccess("Зарплата успешно удалена.");
                }
            });
        }
    }
}