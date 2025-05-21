package service;

import dao.impl.SaleDao;
import exception.SaleNotFoundException;
import exception.StockUpdateException;
import model.Product;
import model.Sale;
import model.Stock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static util.LoggerUtil.*;
import static util.ValidationUtil.*;

public class SaleService {
    private static SaleService instance;
    private final SaleDao saleDao;
    private final ProductService productService;
    private final StockService stockService;

    private SaleService() {
        this(new SaleDao(),
                ProductService.getInstance(),
                StockService.getInstance());
    }

    SaleService(SaleDao saleDao,
                ProductService productService,
                StockService stockService) {
        this.saleDao = saleDao;
        this.productService = productService;
        this.stockService = stockService;
    }

    public static synchronized SaleService getInstance() {
        if (instance == null) {
            instance = new SaleService();
        }
        return instance;
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = saleDao.findAll();
        if (sales.isEmpty()) {
            throw new SaleNotFoundException("Продажи не найдены");
        }
        return sales;
    }

    public List<Sale> getSalesByProduct(Long productId) {
        productService.getProductById(productId);

        List<Sale> sales = saleDao.findByProduct(productId);
        if (sales.isEmpty()) {
            throw new SaleNotFoundException("Продажи товара с ID " + productId + " не найдены");
        }
        return sales;
    }


    public Sale addSale(Sale sale) {
        validateSale(sale);
        verifyStockAvailability(sale.getProductId(), sale.getQuantity());
        prepareSaleData(sale);

        Long id = saleDao.save(sale);

        updateStockAfterSale(sale.getProductId(), sale.getQuantity());
        info("Добавлена новая продажа с ID " + id);
        return sale;
    }

    private void validateSale(Sale sale) {
        validateInteger(sale.getQuantity());
        productService.getProductById(sale.getProductId());
    }

    public Sale addSale(Long productId, Integer quantity) {
        validateId(productId);
        validateInteger(quantity);
        Product product = productService.getProductById(productId);
        BigDecimal totalAmount = calculateTotalAmount(product, quantity);

        Sale sale = new Sale(
                productId,
                quantity,
                totalAmount
        );

        return addSale(sale);
    }


    private void prepareSaleData(Sale sale) {
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(Timestamp.valueOf(LocalDateTime.now()));
        }

        if (sale.getTotalPrice() == null) {
            calculateTotalAmount(sale);
        }
    }

    private void verifyStockAvailability(Long productId, int requiredQuantity) {
        Stock stock = stockService.getStockByProductId(productId);
        if (stock.getQuantity() < requiredQuantity) {
            throw new IllegalStateException(
                    "Недостаточно товара на складе. Доступно: " + stock.getQuantity() +
                            ", требуется: " + requiredQuantity
            );
        }
    }

    private void calculateTotalAmount(Sale sale) {
        Product product = productService.getProductById(sale.getProductId());
        sale.setTotalPrice(calculateTotalAmount(product, sale.getQuantity()));
    }

    private BigDecimal calculateTotalAmount(Product product, int quantity) {
        return product.getSellPrice().multiply(BigDecimal.valueOf(quantity));
    }

    private void updateStock(Long productId, int quantityChange) {
        try {
            Stock stock = stockService.getStockByProductId(productId);
            int newQuantity = stock.getQuantity() + quantityChange;

            if (newQuantity < 0) {
                throw new IllegalStateException("Недостаточно товара на складе для продажи");
            }

            stockService.updateStockQuantity(productId, newQuantity);
            info("Обновлено количество товара с ID " + productId +
                    " на складе после " + "продажи" + ": " + newQuantity);
        } catch (Exception e) {
            error("Ошибка при обновлении склада после " + "продажи" + ": " + e.getMessage(), e);
            throw new StockUpdateException("Ошибка при обновлении склада после продажи");
        }
    }

    private void updateStockAfterSale(Long productId, Integer quantity) {
        updateStock(productId, -quantity);
    }
}