package dao.impl;

import dao.Dao;
import dao.mapper.ProductMapper;
import model.Product;
import model.Purchase;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.function.Function;

public class ProductDao extends Dao<Product> {
    @Override
    protected String getTableName() {
        return "PRODUCTS";
    }

    @Override
    protected Function<ResultSet, Product> getMapper() {
        return ProductMapper::mapRow;
    }

    public Long save(Product product) {
        if (product.getId() == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            String sql = """
                    INSERT INTO PRODUCTS 
                    (NAME, BUY_PRICE, SELL_PRICE) 
                    VALUES (?, ?, ?)""";
            Long id = insert(sql,
                    product.getName(),
                    product.getBuyPrice(),
                    product.getSellPrice());
            if (id != null) {
                product.setId(id);
            }
            return id;
        } else {
            boolean updated = update(product);
            return updated ? product.getId() : null;
        }
    }

    public boolean update(Product product) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        String sql = """
                UPDATE PRODUCTS 
                SET NAME = ?, BUY_PRICE = ?, 
                SELL_PRICE = ? WHERE ID = ?""";
        return update(sql,
                product.getName(),
                product.getBuyPrice(),
                product.getSellPrice(),
                product.getId());
    }
}
