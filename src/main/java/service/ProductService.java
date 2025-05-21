package service;

import dao.impl.ProductDao;
import exception.ProductNotFoundException;
import model.Product;

import java.math.BigDecimal;
import java.util.List;

import static util.LoggerUtil.info;
import static util.LoggerUtil.warn;
import static util.ValidationUtil.*;

public class ProductService {
    private static ProductService instance;
    private final ProductDao productDao;

    private ProductService() {
        productDao = new ProductDao();
    }

    public static synchronized ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productDao.findAll();
        if(products.isEmpty()) {
            throw new ProductNotFoundException("Список продуктов не был найден");
        }
        return products;
    }

    public Product getProductById(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукт с ID " + id + " не найден"));
    }

    public void addProduct(String name, BigDecimal buyPrice, BigDecimal sellPrice) {
        Product product = new Product(name, buyPrice, sellPrice);
        validateProduct(product);
        Long id = productDao.save(product);
        info("Добавлен новый продукт с ID " + id + ": " + product.getName());
    }

    public void updateProduct(Product product) {
        validateProduct(product);
        getProductById(product.getId());

        boolean updated = productDao.update(product);
        if (updated) {
            info("Обновлен продукт с ID " + product.getId() + ": " + product.getName());
        } else {
            warn("Не удалось обновить продукт с ID " + product.getId());
        }
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        boolean deleted = productDao.deleteById(id);

        if (deleted) {
            info("Удален продукт с ID " + id);
        } else {
            warn("Не удалось удалить продукт с ID " + id);
        }
    }

    private void validateProduct(Product product) {
        validateString(product.getName(), "Название продукта не может быть пустым");
        validatePositiveAmount(product.getBuyPrice(), "Цена закупки должна быть положительной");
        validatePositiveAmount(product.getSellPrice(), "Цена продажи должна быть неотрицательной");
    }
}