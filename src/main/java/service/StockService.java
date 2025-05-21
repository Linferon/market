package service;

import dao.impl.StockDao;
import exception.ProductNotFoundException;
import exception.StockNotFoundException;
import model.Stock;
import util.ValidationUtil;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static util.LoggerUtil.info;
import static util.LoggerUtil.warn;
import static util.ValidationUtil.validateId;
import static util.ValidationUtil.validateInteger;

public class StockService {
    private static StockService instance;
    private final StockDao stockDao;
    private final ProductService productService;

    private StockService() {
        this(new StockDao(), ProductService.getInstance());
    }

    StockService(StockDao stockDao, ProductService productService) {
        this.stockDao = stockDao;
        this.productService = productService;
    }

    public static synchronized StockService getInstance() {
        if (instance == null) {
            instance = new StockService();
        }
        return instance;
    }

    public Stock getStockByProductId(Long productId) {
        validateId(productId);
        productService.getProductById(productId);

        return stockDao.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Не было найдено такого продукта на складе!"));
    }

    public List<Stock> getAllStock() {
        List<Stock> stocks = stockDao.findAll();
        if(stocks.isEmpty()) {
            throw new StockNotFoundException("Нет продуктов на складе!");
        }
        return stocks;
    }

    public List<Stock> getAvailableProducts() {
        List<Stock> stocks = stockDao.findAvailableProducts();
        if(stocks.isEmpty()) {
            throw new StockNotFoundException( "Нет доступных продуктов на складе!");
        }
        return stocks;
    }

    public List<Stock> getOutOfStockProducts() {
        List<Stock> stocks = stockDao.findOutOfStockProducts();
        if(stocks.isEmpty()) {
            throw new StockNotFoundException( "Все товары имеются в наличии.");
        }
        return stocks;
    }

    public List<Stock> getLowStockProducts(int threshold) {
        ValidationUtil.validateInteger(threshold, "Пороговое значение должно быть положительным");

        List<Stock> stocks =  stockDao.findLowStockProducts(threshold);
        if(stocks.isEmpty()) {
            throw new StockNotFoundException( "Таких продуктов нет на складе!");
        }
        return stocks;
    }

    public void addStock(Stock stock) {
        validateStock(stock);

        Long productId = stockDao.save(stock);
        info("Добавлена запись о количестве товара с ID продукта " + productId +
                ", количество: " + stock.getQuantity());
    }

    public void updateStockQuantity(Long productId, Integer quantity) {
        productService.getProductById(productId);
        validateInteger(quantity);

        Stock existingStock = getStockByProductId(productId);
        existingStock.setQuantity(quantity);

        boolean updated = stockDao.update(existingStock);

        if (updated) {
            info("Обновлено количество товара с ID " + productId +
                    ", новое количество: " + quantity);
        } else {
            warn("Не удалось обновить количество товара с ID " + productId);
        }
    }

    public boolean deleteStock(Long productId) {
        validateId(productId);
        productService.getProductById(productId);

        boolean deleted = stockDao.deleteById(productId);
        if (deleted) {
            info("Удалена запись о количестве товара с ID " + productId);
        } else {
            warn("Не удалось удалить запись о количестве товара с ID " + productId);
        }
        return deleted;
    }

    private void validateStock(Stock stock) {
        productService.getProductById(stock.getProductId());
        requireNonNull(stock.getProductId(), "Продукт должен быть указан");
        validateInteger(stock.getQuantity());
    }
}