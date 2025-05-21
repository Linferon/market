package service;

import dao.impl.PurchaseDao;
import exception.PurchaseNotFoundException;
import exception.StockUpdateException;
import model.Product;
import model.Purchase;
import model.Stock;

import java.math.BigDecimal;

import static util.LoggerUtil.*;
import static util.ValidationUtil.*;

public class PurchaseService {
    private static PurchaseService instance;
    private final PurchaseDao purchaseDao;
    private final ProductService productService;
    private final StockService stockService;

    private PurchaseService() {
        purchaseDao = new PurchaseDao();
        productService = ProductService.getInstance();
        stockService = StockService.getInstance();
    }

    public static synchronized PurchaseService getInstance() {
        if (instance == null) {
            instance = new PurchaseService();
        }
        return instance;
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseDao.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Закупка с ID " + id + " не найдена"));
    }

    public void addPurchase(Purchase purchase) {
        validatePurchase(purchase);

        Long purchaseId = purchaseDao.save(purchase);
        info("Добавлена новая закупка с ID " + purchaseId);

        updateStockAfterPurchase(purchase.getProductId(), purchase.getQuantity());
    }


    public void addPurchase(Long productId, Integer quantity, BigDecimal totalCost) {
        validateId(productId, "ID продукта должен быть указан");
        validateInteger(quantity);
        validatePositiveAmount(totalCost, "Общая стоимость должна быть положительным числом");

        productService.getProductById(productId);

        Purchase purchase = new Purchase(
                productId,
                quantity,
                totalCost
        );

        addPurchase(purchase);
    }

    public void updatePurchase(Long purchaseId, Integer quantity) {
        Purchase existingPurchase = getPurchaseById(purchaseId);
        Product product = productService.getProductById(existingPurchase.getProductId());
        BigDecimal newTotalCost = product.getBuyPrice().multiply(new BigDecimal(quantity));

        Purchase updatePurchase = new Purchase(
                purchaseId,
                existingPurchase.getProductId(),
                quantity,
                newTotalCost,
                existingPurchase.getPurchaseDate()
        );


        validatePurchase(updatePurchase);
        boolean updated = purchaseDao.update(updatePurchase);

        if (updated) {
            if (!existingPurchase.getQuantity().equals(updatePurchase.getQuantity())) {
                int quantityDifference = updatePurchase.getQuantity() - existingPurchase.getQuantity();
                updateStockAfterPurchaseUpdate(updatePurchase.getProductId(), quantityDifference);
            }
        } else {
            warn("Не удалось обновить закупку с ID " + existingPurchase.getId());
        }
    }

    public void deletePurchase(Long id) {
        Purchase purchase = getPurchaseById(id);
        boolean deleted = purchaseDao.deleteById(id);

        if (deleted) {
            updateStockAfterPurchaseDeletion(purchase.getProductId(), purchase.getQuantity());
            info("Удалена закупка с ID " + id);
        } else {
            warn("Не удалось удалить закупку с ID " + id);
        }
    }

    private void validatePurchase(Purchase purchase) {
        validateInteger(purchase.getQuantity());
        validatePositiveAmount(purchase.getTotalCost(), "Общая стоимость должна быть положительным числом");
        productService.getProductById(purchase.getProductId());
    }

    private void updateStockAfterPurchase(Long productId, Integer quantity) {
        try {
            Stock stock = stockService.getStockByProductId(productId);

            int newQuantity = stock.getQuantity() + quantity;
            stockService.updateStockQuantity(productId, newQuantity);

            info("Обновлено количество товара с ID " + productId + " на складе: " + newQuantity);

        } catch (Exception e) {
            Stock newStock = new Stock(
                    productId,
                    quantity
            );

            stockService.addStock(newStock);
            info("Добавлен новый товар с ID " + productId + " на склад, количество: " + quantity);
        }
    }

    private void updateStockAfterPurchaseUpdate(Long productId, Integer quantityDifference) {
        try {
            Stock stock = stockService.getStockByProductId(productId);

            int newQuantity = stock.getQuantity() + quantityDifference;
            if (newQuantity < 0) {
                throw new IllegalStateException("Невозможно обновить закупку: недостаточно товара на складе");
            }

            stockService.updateStockQuantity(productId, newQuantity);
            info("Обновлено количество товара с ID " + productId +
                    " на складе после изменения закупки: " + newQuantity);
        } catch (Exception e) {
            error("Ошибка при обновлении количества товара на складе: " + e.getMessage(), e);
            throw new StockUpdateException("Ошибка при обновлении количества товара на складе");
        }
    }

    private void updateStockAfterPurchaseDeletion(Long productId, Integer quantity) {
        try {
            Stock stock = stockService.getStockByProductId(productId);
            int newQuantity = Math.max(stock.getQuantity() - quantity, 0);
            stockService.updateStockQuantity(productId, newQuantity);

            info("Обновлено количество товара с ID " + productId +
                    " на складе после удаления закупки: " + newQuantity);
        } catch (Exception e) {
            error("Ошибка при обновлении количества товара на складе: " + e.getMessage(), e);
        }
    }
}