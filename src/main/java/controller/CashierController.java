package controller;

import model.Sale;
import model.Stock;
import service.SaleService;
import service.StockService;
import exception.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static util.ConsoleUtil.*;
import static util.InputHandler.getIntInput;
import static util.InputHandler.getLongInput;

public class CashierController extends BaseController {
    private final StockService stockService;
    private final SaleService saleService;

    public CashierController() {
        stockService = StockService.getInstance();
        saleService = SaleService.getInstance();
    }

    @Override
    public void showMenu() {
        createMenu("Меню Кассира")
                .addMenuItem("Продать товар", this::sellProduct)
                .addMenuItem("Просмотреть продажи", this::viewSales)
                .addExitItem("Выйти из системы")
                .show();
    }

    private void sellProduct() {
        ExceptionHandler.execute(() -> {
            List<Stock> availableStock = stockService.getAvailableProducts();
            showEntitiesTable(availableStock, "Доступные товары");

            Long productId = getLongInput("Введите ID продукта: ");
            Stock stock = stockService.getStockByProductId(productId);

            int quantity = getIntInput("Введите количество: ");

            if (stock.getQuantity() < quantity) {
                showError("Недостаточно товара на складе. Доступно: " + stock.getQuantity());
                return;
            }

            Sale sale = saleService.addSale(productId, quantity);
            showSuccess("Продажа успешно совершена. Сумма к оплате: " + sale.getTotalPrice() + " руб.");

            printCheck(sale);
        });
    }

    private void viewSales() {
        ExceptionHandler.execute(() -> {
            List<Sale> sales = saleService.getAllSales();
            showEntitiesTable(sales, "Список продаж ");
        });
    }

    private void printCheck(Sale sale) {
        printHeader("ЧЕК");
        println("Товар: " + sale.getProductId());
        println("Количество: " + sale.getQuantity());
        println("Итого: " + sale.getTotalPrice() + " руб.");
        println("Дата: " + LocalDateTime.now());
        printDivider();
    }
}