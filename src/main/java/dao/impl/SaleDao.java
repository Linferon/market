package dao.impl;

import dao.Dao;
import dao.mapper.SaleMapper;
import model.Sale;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

public class SaleDao extends Dao<Sale> {
    @Override
    protected String getTableName() {
        return "SALES";
    }

    @Override
    protected Function<ResultSet, Sale> getMapper() {
        return SaleMapper::mapRow;
    }

    public List<Sale> findByProduct(Long productId) {
        String sql = "select * from sales where product_id = ?";
        return queryList(sql, productId);
    }

    public Long save(Sale sale) {
        if (sale.getId() == null) {
            String sql = """
                    INSERT INTO SALES 
                    (PRODUCT_ID, QUANTITY, TOTAL_PRICE, SALE_DATE)
                    VALUES (?, ?, ?, ?)""";
            Long id = insert(sql,
                    sale.getProductId(),
                    sale.getQuantity(),
                    sale.getTotalPrice(),
                    sale.getSaleDate());
            if (id != null) {
                sale.setId(id);
            }
            return id;
        } else {
            boolean updated = update(sale);
            return updated ? sale.getId() : null;
        }
    }

    public boolean update(Sale sale) {
        String sql = """
                UPDATE SALES 
                SET PRODUCT_ID = ?, QUANTITY = ?, 
                TOTAL_PRICE = ?, SALE_DATE = ? WHERE ID = ?""";
        return update(sql,
                sale.getProductId(),
                sale.getQuantity(),
                sale.getTotalPrice(),
                sale.getSaleDate(),
                sale.getId());
    }
}
