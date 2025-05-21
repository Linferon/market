package controller;

import model.Budget;
import model.Role;
import model.User;
import service.*;
import exception.ExceptionHandler;

import java.math.BigDecimal;
import java.util.List;

import static util.ConsoleUtil.*;
import static util.InputHandler.*;
import static util.LoggerUtil.error;

public class DirectorController extends BaseController {
    private final UserService userService;
    private final BudgetService budgetService;

    private final List<Role> roles;

    private final BudgetController budgetController;
    private final EmployeeController employeeController;

    public DirectorController() {
        userService = UserService.getInstance();
        budgetService = BudgetService.getInstance();

        roles = RoleService.getInstance().getAllRoles();

        budgetController = new BudgetController();
        employeeController = new EmployeeController();
    }

    @Override
    public void showMenu() {
        createMenu("Меню Директора")
                .addMenuItem("Управление бюджетами", budgetController::manageBudgets)
                .addMenuItem("Управление сотрудниками", employeeController::manageEmployees)
                .addExitItem("Выйти из системы")
                .show();
    }

    private class BudgetController {
        private void manageBudgets() {
            createMenu("Управление бюджетами")
                    .addMenuItem("Посмотреть все месячные бюджеты", this::viewAllBudgets)
                    .addMenuItem("Назначить бюджет на месяц", this::setBudget)
                    .addMenuItem("Изменить бюджет", this::editBudget)
                    .addExitItem("Назад")
                    .show();
        }

        private void viewAllBudgets() {
            ExceptionHandler.execute(() -> {
                List<Budget> budgets = budgetService.getAllBudgets();
                showEntitiesTable(budgets, "Список всех бюджетов на месяц: ");
            });
        }


        private void setBudget() {
            ExceptionHandler.execute(() -> {
                Integer year = getIntInput("Введите год: ");
                Integer month = getIntInput("Введите месяц: ");
                BigDecimal budget = getBigDecimalInput("Введите бюджет на месяц: ");
                BigDecimal plannedIncome = getBigDecimalInput("Введите сумму планируемого дохода: ");
                BigDecimal plannedExpenses = getBigDecimalInput("Введите сумму планируемых расходов: ");

                budgetService.createBudget(year, month, budget, plannedIncome, plannedExpenses);
                showSuccess("Бюджет успешно установлен.");
            });
        }

        private void editBudget() {
            viewAllBudgets();
            Long budgetId = getLongInput("Введите id бюджета для редактирования");
            Budget budget = budgetService.getBudgetById(budgetId);
            showEntityDetails(budget, "Редактирование бюджета");

            Integer year = getUpdatedIntegerValue("год", budget.getYear());
            Integer month = getUpdatedIntegerValue("месяц", budget.getMonth());
            BigDecimal newBudget = getUpdatedBigDecimalValue("сумму бюджета", budget.getBudget());
            BigDecimal plannedIncome = getUpdatedBigDecimalValue("сумму планируемых доходов", budget.getIncome());
            BigDecimal plannedExpense = getUpdatedBigDecimalValue("сумму планируемых расходов", budget.getExpense());

            if (budgetService.updateBudget(budgetId, year, month, newBudget, plannedIncome, plannedExpense)) {
                showSuccess("Доход успешно обновлен.");
            } else {
                showError("Не удалось обновить доход.");
            }
        }
    }

    private class EmployeeController {
        private void manageEmployees() {
            createMenu("Управление сотрудниками")
                    .addMenuItem("Показать всех сотрудников", this::viewAllEmployees)
                    .addMenuItem("Зарегистрировать нового сотрудника", this::registerNewEmployee)
                    .addMenuItem("Изменить данные сотрудника", this::updateEmployee)
                    .addMenuItem("Уволить сотрудника или восстановить сотрудника", this::toggleEmployeeStatus)
                    .addExitItem("Назад")
                    .show();
        }

        private void viewAllEmployees() {
            ExceptionHandler.execute(() -> {
                List<User> users = userService.getActiveUsers();
                showEntitiesTable(users, "Активные сотрудники");
            });
        }

        private void registerNewEmployee() {
            ExceptionHandler.execute(() -> {
                String name = getStringInput("Введите имя сотрудника: ");
                String surname = getStringInput("Введите фамилию сотрудника: ");
                String email = getStringInput("Введите email: ");
                String password = getStringInput("Введите пароль: ");
                Long selectedRole = selectRole();

                User newUser = new User(name, surname, email, password, true, selectedRole);
                userService.registerUser(newUser);
                showSuccess("Сотрудник успешно зарегистрирован.");
            });
        }

        private void updateEmployee() {
            ExceptionHandler.execute(() -> {
                viewAllEmployees();
                Long userId = getLongInput("Введите ID сотрудника для обновления: ");
                User existingUser = userService.getUserById(userId);

                showEntityDetails(existingUser, "Текущие данные сотрудника");

                inputIfPresent("Введите новое имя", existingUser::setName);
                inputIfPresent("Введите новую фамилию", existingUser::setSurname);
                inputIfPresent("Введите новый email", existingUser::setEmail);

                if (!userService.getCurrentUser().equals(existingUser)) {
                    showConfirmationMenu("Хотите изменить роль? ", () -> {
                        Long selectedRole = selectRole();
                        existingUser.setRoleId(selectedRole);
                    });
                }

                userService.updateUser(existingUser);
                showSuccess("Данные сотрудника обновлены.");
            });
        }

        private void toggleEmployeeStatus() {
            ExceptionHandler.execute(() -> {
                List<User> users = userService.getAllUsers();
                showEntitiesTable(users, "Все сотрудники");
                Long userId = getLongInput("Введите ID сотрудника: ");
                User user = userService.getUserById(userId);

                validateSelfDismiss(user);
                user.setEnabled(!user.getEnabled());
                userService.updateUser(user);

                String action = Boolean.TRUE.equals(user.getEnabled()) ? "восстановлен" : "уволен";
                showSuccess("Сотрудник успешно " + action + ".");
            });
        }

        private Long selectRole() {
            showEntitiesTable(roles, "Доступные роли");
            return getLongInput("Введите номер роли: ");
        }

        private void validateSelfDismiss(User user) {
            if (userService.getCurrentUser().equals(user)) {
                error("Вы не можете уволить самого себя!");
            }
        }
    }
}