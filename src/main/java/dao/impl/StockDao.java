package dao.impl;

import dao.Dao;
import dao.mapper.StockMapper;
import model.Stock;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class StockDao extends Dao<Stock> {

    @Override
    protected String getTableName() {
        return "STOCK";
    }

    @Override
    protected Function<ResultSet, Stock> getMapper() {
        return StockMapper::mapRow;
    }

    public List<Stock> findAvailableProducts() {
        String sql = """
                select * from STOCK 
                where quantity > 0
                """;
        return queryList(sql);
    }

    public List<Stock> findOutOfStockProducts(){
        String sql = """
                select * from STOCK 
                where quantity <= 0
                """;
        return queryList(sql);
    }

    public List<Stock> findLowStockProducts(int threshold) {
        String sql = "select * from STOCK where quantity between 0 and " + threshold;
        return queryList(sql, threshold);
    }

    public Optional<Stock> findByProductId(Long productId) {
        String sql = "select * from STOCK where product_id = ?";
        return querySingle(sql, productId);
    }

    public Long save(Stock stock) {
        Optional<Stock> existingStock = findByProductId(stock.getProductId());

        if (existingStock.isEmpty()) {
            String sql = """
                    INSERT INTO STOCK 
                    (PRODUCT_ID, QUANTITY) 
                    VALUES (?, ?)""";

            insert(sql, stock.getProductId(), stock.getQuantity());
            return stock.getProductId();
        } else {
            boolean updated = update(stock);
            return updated ? stock.getProductId() : null;
        }
    }

    public boolean update(Stock stock) {
        String sql = """
                UPDATE STOCK 
                SET QUANTITY = ? WHERE PRODUCT_ID = ?""";
        return update(sql,
                stock.getQuantity(),
                stock.getProductId());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM STOCK WHERE PRODUCT_ID = ?";
        return delete(sql, id);
    }
}
