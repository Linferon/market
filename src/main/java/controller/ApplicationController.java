package controller;

import model.User;
import service.UserService;
import util.LiquibaseMigrator;
import util.DatabaseConnection;

import static util.ConsoleUtil.printHeader;
import static util.ConsoleUtil.println;
import static util.InputHandler.closeScanner;
import static util.InputHandler.getStringInput;
import static util.LoggerUtil.*;

public class ApplicationController {
    private final UserService userService;
    private final AuthController authUI;

    public ApplicationController() {
        this.userService = UserService.getInstance();
        this.authUI = new AuthController();
    }

    public void run() {
        try {
            initializeApplication();
            mainApplicationLoop();
        } catch (Exception e) {
            handleCriticalError(e);
        } finally {
            cleanupResources();
        }
    }

    private void initializeApplication() {
        LiquibaseMigrator.migrate();
    }

    private void mainApplicationLoop() {
        while (true) {
            if (!performAuthentication()) {
                continue;
            }

            User currentUser = userService.getCurrentUser();
            displayWelcomeMessage(currentUser);
            processUserInterface(currentUser);
            authUI.logout();

            if (shouldExitApplication()) {
                displayExitMessage();
                break;
            }
        }
    }

    private boolean performAuthentication() {
        if (!authUI.authenticate()) {
            error("Ошибка авторизации. Попробуйте снова.");
            return false;
        }
        return true;
    }

    private void displayWelcomeMessage(User user) {
        printHeader("Добро пожаловать, " +
                user.getName() + " " + user.getSurname());
    }

    private void processUserInterface(User user) {
        BaseController ui = getUIForRole(user.getRoleId());

        assert ui != null;
        ui.showMenu();
    }

    private BaseController getUIForRole(long roleId) {
        return switch ((int) roleId) {
            case 1 -> new DirectorController();
            case 2 -> new AccountantController();
            case 3 -> new StockKeeperController();
            case 4 -> new CashierController();
            default -> {
                handleUnknownRole(roleId);
                yield null;
            }
        };
    }


    private boolean shouldExitApplication() {
        while (true) {
            String input = getStringInput("\nЖелаете продолжить работу? (да/нет): ").trim().toLowerCase();
            if (input.equals("да")) return false;
            if (input.equals("нет")) return true;
        }
    }

    private void displayExitMessage() {
        printHeader("Завершение работы системы");
        println("Спасибо за использование системы управления предприятием!");
    }

    private void handleCriticalError(Exception e) {
        error("Критическая ошибка приложения: " + e.getMessage());
        e.printStackTrace();
    }

    private void cleanupResources() {
        DatabaseConnection.closeConnection();
        closeScanner();
        close();
    }

    private void handleUnknownRole(Long roleId) {
        println("Неизвестная роль: " + roleId + ". Доступ запрещен.");
        warn("Попытка доступа с неизвестной ролью: " + roleId);
    }
}